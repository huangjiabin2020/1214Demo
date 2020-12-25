package com.binge.test;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月24日
 * @description:
 **/
public class CalendarDemo {

    @Test
    public void test1(){
        Calendar calendar = Calendar.getInstance();
        System.out.println("当前年月日");
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("指定月份最后一天");
        calendar.set(Calendar.YEAR,2020);
        calendar.set(Calendar.MONTH,1);
        int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(actualMaximum);
    }
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Calendar instance = Calendar.getInstance();
        System.out.println(Thread.currentThread()+String.valueOf(instance.hashCode()));
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<String> future = pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Calendar instance = Calendar.getInstance();
                System.out.println(Thread.currentThread() + String.valueOf(instance.hashCode()));
                return String.valueOf(instance.hashCode());
            }
        });
        System.out.println(future.get().equals(String.valueOf(instance.hashCode())));
        instance.setTime(new Date());

    }
}
