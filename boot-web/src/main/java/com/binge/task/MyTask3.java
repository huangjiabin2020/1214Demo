package com.binge.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月23日
 * @description:
 **/
@Component
@Slf4j
public class MyTask3 implements Serializable, Runnable {

    private static final long serialVersionUID = 366199411949704749L;
    private int i;

    @Override
    public void run() {
        log.info("thread id:{},任务3 execute times:{}", Thread.currentThread().getId(), ++i);
    }
}
