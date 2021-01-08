package com.binge.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.binge.entity.UploadFile;
import com.binge.listener.DemoDataListener;
import com.binge.service.IUploadFileService;
import com.binge.utils.GetBean;
import com.binge.utils.JdbcUtil;
import com.binge.utils.MyUtils;
import com.binge.webentity.AxiosResult;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@RestController
@Api("文件上传和下载")
@RequestMapping("file")
@Slf4j
public class FileController {
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    IUploadFileService iUploadFileService;
    @Autowired
    JdbcUtil jdbcUtil;

    /**
     * 根据表名导出对应的Excel
     *
     * @param tableName
     * @return
     */
    @SneakyThrows
    @GetMapping("exportexcel")
    public void exportexcel(String tableName,
                            @RequestParam(defaultValue = "100") Long limit,
                            HttpServletResponse response) {
       /* List<Map<String, Object>> maps = jdbcUtil.jdbcTableValue(tableName);
        System.out.println(maps);*/

        List<List> lists = jdbcUtil.jdbcTableValue(tableName, String.valueOf(limit));
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(tableName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        lists.forEach(System.out::println);
        EasyExcel.write(response.getOutputStream()).sheet("模板").doWrite(lists);
    }

    @SneakyThrows
    @PostMapping("importexcel")
    public AxiosResult importexcel(@RequestPart MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");

        String suffix = fileName.substring(index + 1);
        String name = fileName.substring(0, index);
//        name = MyUtils.captureName(name);
//        log.info(name);
//        log.info(suffix);
        DemoDataListener listener = new DemoDataListener();
        ExcelReader excelReader = EasyExcelFactory.read(file.getInputStream(), null, listener).headRowNumber(0).build();
        excelReader.read();
        List<List<String>> lists = listener.getDatas();
        //默认第一行是字段名，第二行开始是一条条的记录
        List<String> fieldNames = lists.get(0);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 1; i < lists.size(); i++) {
            HashMap<String, Object> fields = new HashMap<>();
            for (int j = 0; j < fieldNames.size(); j++) {
                fields.put(fieldNames.get(j), lists.get(i).get(j));
            }
            maps.add(fields);
        }
        jdbcUtil.executeBatchInsert(name, maps);
        excelReader.finish();
        return AxiosResult.success(lists);

    }


    @GetMapping(value = "download")
    public AxiosResult download(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
        UploadFile one = iUploadFileService.getOne(new QueryWrapper<UploadFile>().lambda().eq(UploadFile::getId, id));
        String name = one.getName();
        String suffix = one.getSuffix();
        String address = one.getAddress();
        Long size = one.getSize();

        try (FileInputStream fis = new FileInputStream(address)) {
            response.reset();
            //文件名必须包括.后缀名，不然用户下载的都是.file类型
            response.addHeader("Content-Disposition", "attachment; filename=\"" + name + "." + suffix + "\"");
            //循环读取流
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fis.read(bytes)) > 0) {
                response.getOutputStream().write(bytes, 0, len);
            }
        } catch (IOException e) {
            return AxiosResult.error("文件读取错误！");
        }
        return AxiosResult.success("文件读取成功！");
    }


    @PostMapping("upload")
    public AxiosResult upload(@RequestParam @RequestPart @NotNull Part part) throws IOException {

        String submittedFileName = part.getSubmittedFileName();
        log.info("收到用户上传的文件：" + submittedFileName);

        BufferedInputStream bis = new BufferedInputStream(part.getInputStream());
        //目录打散生成的文件全路径
        String fileSavePath = filePathGenerate(submittedFileName);
        log.info("文件保存路径：{}", fileSavePath);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(fileSavePath)));

        //BIO写入硬盘
        threadPoolExecutor.submit(() -> {
            try {
                writeToFile(bis, bos);
            } catch (IOException e) {
                log.error("文件写入出现异常，文件地址为：{}", fileSavePath);
            }
        });

        iUploadFileService.save(new UploadFile()
                .setName(getPreSuffix(submittedFileName)[0])
                .setAddress(fileSavePath)
                .setSize(part.getSize()).setCreateTime(LocalDateTime.now())
                .setSuffix(getPreSuffix(submittedFileName)[1]));
        return AxiosResult.success();
    }

    private void writeToFile(BufferedInputStream bis, BufferedOutputStream bos) throws IOException {
        long s = System.currentTimeMillis();
        //相对当前位置的偏移量
        int off = 0;
        //读取的长度，单位为字节
        int len = 1024;
        //读取的数据存储到该字节数组
        //数组初始化之后，会有1024个字节，默认值为0。所以下面读取数据到b字节数组的时候，假如读取的长度不够，会有0填充的问题。
        //所以具体要从b中读取多少个数据，要看len= bufferedInputStream.read(b, off, len)中，b到底从输入流中读取到了多少字节，也就是Len的值为多少
        //以下的写法就是正常的，不会出现message中有乱码或者0填充的情况。
        byte[] b = new byte[1024];
        while ((len = bis.read(b, off, len)) != -1) {
            bos.write(b, 0, len);
        }
        bis.close();
        bos.close();
        long e = System.currentTimeMillis();
        log.error("花费时间：{} 毫秒", e - s);
    }

    private String filePathGenerate(String submittedFileName) {
        String suffix = getPreSuffix(submittedFileName)[1];
        /*
         * 1. 得到文件保存的路径
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String root = "d:/fileUpload/" + sdf.format(new Date());
        /*
         * 2. 生成二层目录
         * 1). 得到文件名称
         * 2). 得到hashCode
         * 3). 转发成16进制
         * 4). 获取前二个字符用来生成目录
         */

        /*
         * 给文件名称添加uuid前缀，处理文件同名问题
         */
        String savename = UUID.randomUUID().toString().replaceAll("-", "");

        /*
         * 1. 得到hashCode
         */
        int hCode = savename.hashCode();
        String hex = Integer.toHexString(hCode);

        /*
         * 2. 获取hex的前两个字母，与root连接在一起，生成一个完整的路径
         */
//        File dirFile = new File(root, hex.charAt(0) + "/" + hex.charAt(1));
        String dir = root + "/" + hex.charAt(0) + "/" + hex.charAt(1) + "/";
        //创建目录链
        new File(dir).mkdirs();
        return dir + savename + "." + suffix;

    }

    /**
     * 根据文件全名获取前后缀
     *
     * @param submittedFileName
     * @return [文件名，文件类型]
     */
    private String[] getPreSuffix(String submittedFileName) {

        int lastIndex = submittedFileName.lastIndexOf(".");
        String prefix = submittedFileName.substring(0, lastIndex);
        //jpg之类的
        String suffix = submittedFileName.substring(lastIndex + 1);
        return new String[]{prefix, suffix};
    }


}
