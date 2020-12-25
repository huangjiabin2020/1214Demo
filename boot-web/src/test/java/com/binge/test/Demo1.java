package com.binge.test;

import com.binge.task.MyTask2;
import com.deepoove.poi.XWPFTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: JiaBin Huang
 * @date: 2020年 12月14日
 * @description: 尝试使用free market生成word文档
 **/

public class Demo1 {

    @Test
    public void test1() {
        try {
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("wordname", "aaaaa");
            dataMap.put("username", "bbbbb");
            dataMap.put("password", "cccccc");

            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            //指定模板路径的第二种方式,我的路径是D:/      还有其他方式
            configuration.setDirectoryForTemplateLoading(new File("D:/"));

            // 输出文档路径及名称
            File outFile = new File("D:/test.doc");
            //以utf-8的编码读取ftl文件
            Template t = configuration.getTemplate("demo.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
            t.process(dataMap, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFTemplate template = XWPFTemplate.compile("D:/test.docx").render(new HashMap<String, Object>() {{
            put("wordname", "文档名");
            put("username", "张三");
            put("password", "123456");
        }});
        FileOutputStream out = new FileOutputStream("D:/demo.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }


    @Test
    public void test3() {
        List<Double> doubles = Arrays.asList(new Double(3.3), new Double(3.3), new Double(3.3));
        double sum = doubles.stream().mapToDouble(Double::doubleValue).sum();
        System.out.println(sum);// 9.899999999999999
        System.out.println(formatDigit(sum, 2));// 9.9
        System.out.println(formatDigit_down(sum, 2)); // 9.89
        List<Double> doubles1 = Arrays.asList(new Double(1.1), new Double(1.1), new Double(1.1));
        double sum1 = doubles1.stream().mapToDouble(Double::doubleValue).sum();
        System.out.println(sum1);// 3.3000000000000003
        System.out.println(formatDigit(sum1, 2));// 3.3
        System.out.println(formatDigit_down(sum1, 2));// 3.3
    }

    /**
     * 将数据转换为保留指定小数位数（0，1，2）格式的数，四舍五入
     */
    public static double formatDigit(double num, int decimalPlace) {
        DecimalFormat fm = null;
        switch (decimalPlace) {
            case 0:
                fm = new DecimalFormat("##");
                break;
            case 1:
                fm = new DecimalFormat("##.#");
                break;
            case 2:
                fm = new DecimalFormat("##.##");
                break;
            case 3:
                fm = new DecimalFormat("##.###");
                break;
            case 4:
                fm = new DecimalFormat("##.####");
                break;
            default:
                break;
        }

        if (fm == null) {
            return num;
        }

        StringBuffer sbf = new StringBuffer();
        fm.format(num, sbf, new FieldPosition(java.text.NumberFormat.FRACTION_FIELD));
        return Double.parseDouble(sbf.toString());
    }

    /**
     * 将数据转换为保留指定小数位数（0，1，2）格式的数。向下取值：如 5.567 -> 5.56 -5.567 -> -5.56
     */
    public static double formatDigit_down(double num, int decimalPlace) {
        DecimalFormat fm = null;
        switch (decimalPlace) {
            case 0:
                fm = new DecimalFormat("##");
                break;
            case 1:
                fm = new DecimalFormat("##.#");
                break;
            case 2:
                fm = new DecimalFormat("##.##");
                break;
            case 3:
                fm = new DecimalFormat("##.###");
                break;
            case 4:
                fm = new DecimalFormat("##.####");
                break;
            default:
                break;
        }

        if (fm == null) {
            return num;
        }

        StringBuffer sbf = new StringBuffer();
        fm.setRoundingMode(RoundingMode.DOWN);// 向下取值设置、
        fm.format(num, sbf, new FieldPosition(java.text.NumberFormat.FRACTION_FIELD));
        return Double.parseDouble(sbf.toString());
    }


    @Test
    public void test4() throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        String result = Base64.encodeBase64String(messageDigest.digest("123456".getBytes()));
        System.out.println(result);


    }
    @Test
    public void test5() throws Exception {

        MyTask2 myTask2 = new MyTask2();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:/task2.txt"));
        oos.writeObject(myTask2);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("d:/task2.txt"));
        Object o = ois.readObject();
        if (o instanceof Runnable){
            ((Runnable) o).run();
        }
    }



}
