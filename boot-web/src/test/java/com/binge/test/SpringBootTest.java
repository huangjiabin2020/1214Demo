package com.binge.test;

import com.binge.DemoApplication;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@org.springframework.boot.test.context.SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootTest {
    @Autowired
    BCryptPasswordEncoder encoder;
    @Test
    public void test1(){
        String encode = encoder.encode("123456");
        System.out.println(encode);
        System.out.println(encoder.matches("123456", encode));
    }
    @Test
    public void test2() throws Exception {
        ArrayList<Goods> goods = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; i < 10; i++) {
            double v = Double.parseDouble(df.format(new Random().nextDouble()));
            int i1 = new Random().nextInt(100);
            goods.add(new Goods().setName("商品"+i+"号")
                    .setPrice(v)
                    .setPicture(new PictureRenderData(30,40,"d:/1.jpg"))
                    .setCount(i1)
                    .setTotal(Double.valueOf(df.format(v*i1))));
        }
        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();

        Configure config = Configure.newBuilder()
                .bind("goods", policy).build();

        XWPFTemplate template = XWPFTemplate.compile("d:/test.docx",config).render(
                new HashMap<String, Object>(){{
                    put("title", "Hi, poi-tl Word模板引擎");
                    // 本地图片
                    put("mypic", new PictureRenderData(80, 100, "d:/1.jpg"));
                    put("goods", goods);
                }});
        FileOutputStream out = new FileOutputStream("d:/output.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @Data
    @Accessors(chain = true)
    class Goods{
        private int count;
        private String name;
        private Double price;
        private Double total;
        private PictureRenderData picture;
    }
}
