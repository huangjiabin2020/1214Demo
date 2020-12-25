package com.binge.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binge.entity.SpringScheduledCron;
import com.binge.mapper.SpringScheduledCronMapper;
import com.binge.service.ISpringScheduledCronService;
import com.binge.utils.GetBean;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月23日
 * @description:
 **/
public interface ScheduledOfTask extends Runnable {
    /**
     * 定时任务方法
     */
    void execute();
    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        ISpringScheduledCronService scheduledCronService = GetBean.getBean(ISpringScheduledCronService.class);
        SpringScheduledCron scheduledCron = scheduledCronService.getOne(new QueryWrapper<SpringScheduledCron>().lambda().eq(SpringScheduledCron::getCronKey,this.getClass().getName()));
        if ("2".equals(scheduledCron.getStatus())) {
            // 任务是禁用状态
            return;
        }
        execute();
    }
}