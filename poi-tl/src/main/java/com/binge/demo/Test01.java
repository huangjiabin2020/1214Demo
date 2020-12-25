package com.binge.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月24日
 * @description:
 **/
public class Test01 {
    @Data
    static
    class SegmentData {
        String title;
        String content;
    }

    public static void main(String[] args) throws Exception {
//        m1( new HashMap<String, Object>() {{
//                    put("title", "Hi, poi-tl Word模板引擎");
//                    put("list", new ArrayList<HashMap>() {{
//                                add(new HashMap<String, Object>() {{
//                                    put("name", "AAA");
//                                }});
//                                add(new HashMap<String, Object>() {{
//                                    put("name", "BBB");
//                                }});
//                                add(new HashMap<String, Object>() {{
//                                    put("name", "CCC");
//                                }});
//                            }}
//                    );
//
//                }},"d:/template.docx","d:/outputTemplate.docx");

//        pieChart();
//        demo();

        Random random = new Random();
        ArrayList<Good> goods = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Good good = new Good();
            good.setName("商品" + i);
            good.setNum(random.nextInt(1000));
            good.setDesc("描述:" + UUID.randomUUID().toString().substring(0, 6));
            good.setPrice(random.nextInt(15));
            good.setTotal(good.getNum() * good.getPrice());
            goods.add(good);
        }

        Configure config = Configure.newBuilder().bind("detail_table", new DetailTablePolicy()).build();
        m2(goods,config,"D:/payment.docx","d:/outputPayment.docx");
    }

    private static void demo() throws IOException {
        Random random = new Random();
        ArrayList<Good> goods = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Good good = new Good();
            good.setName("商品" + i);
            good.setNum(random.nextInt(1000));
            good.setDesc("描述:" + UUID.randomUUID().toString().substring(0, 6));
            good.setPrice(random.nextInt(15));
            good.setTotal(good.getNum() * good.getPrice());
            goods.add(good);
        }
        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder()
                .bind("goods", policy).bind("labors", policy).build();
        m2(new HashMap<String, Object>() {{
            put("goods", goods);
        }}, config, "d:/payment.docx", "d:/outputPayment.docx");
    }

    @Data
    @Accessors(chain = true)
    static
    class Good extends RowRenderData {
        private int num;
        private String name;
        private String desc;
        private int price;
        private int total;
    }

    static class DetailTablePolicy extends DynamicTableRenderPolicy {

        @Override
        public void render(XWPFTable table, Object o) {
            List<Good> list = null;
            if (o instanceof List ){
                list= (List) o;
            }
            for (int i = 0; i < Objects.requireNonNull(list).size(); i++) {
                XWPFTableRow insertNewTableRow  = table.insertNewTableRow(1);
                for (int j = 0; j < 5; j++) {
                    insertNewTableRow.createCell();
                }
                // 渲染单行货品明细数据
                MiniTableRenderPolicy.Helper.renderRow(table,1,list.get(i));
            }
        }
    }

    private static void pieChart() throws IOException {
        ChartSingleSeriesRenderData pie = new ChartSingleSeriesRenderData();
        pie.setChartTitle("彬哥的饼图");
        pie.setCategories(new String[]{"俄罗斯", "加拿大", "美国", "中国"});
        pie.setSeriesData(new SeriesRenderData("countries", new Integer[]{17098242, 9984670, 9826675, 9596961}));

        m1(new HashMap<String, Object>() {{
            put("pie", pie);
        }}, "d:/pie.docx", "d:/outputPie.docx");
    }

    private static void m1(Object obj, String templateFilePath, String outputFilePath) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(templateFilePath).render(
                obj);
        FileOutputStream out = new FileOutputStream(outputFilePath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    private static void m2(Object obj, Configure configure, String templateFilePath, String outputFilePath) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(templateFilePath, configure).render(
                obj);
        FileOutputStream out = new FileOutputStream(outputFilePath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
