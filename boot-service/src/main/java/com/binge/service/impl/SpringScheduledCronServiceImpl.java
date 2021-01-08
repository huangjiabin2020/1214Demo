package com.binge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binge.entity.SpringScheduledCron;
import com.binge.mapper.SpringScheduledCronMapper;
import com.binge.service.ISpringScheduledCronService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author binge
 * @since 2020-12-23
 */
@Service
public class SpringScheduledCronServiceImpl extends ServiceImpl<SpringScheduledCronMapper, SpringScheduledCron> implements ISpringScheduledCronService {


}
