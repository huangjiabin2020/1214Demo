package com.binge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * 定时任务表
 * </p>
 *
 * @author binge
 * @since 2020-12-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SpringScheduledCron implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "cron_id", type = IdType.AUTO)
    private Integer cronId;

    /**
     * 定时任务完整类名
     */
    private String cronKey;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务描述
     */
    private String taskExplain;

    /**
     * 状态,1:正常;2:停用
     */
    private Integer status;


    private transient ScheduledFuture scheduledFuture;
}
