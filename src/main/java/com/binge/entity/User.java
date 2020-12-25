package com.binge.entity;

import 你自己的父类实体,没有就不用设置!;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author binge
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends 你自己的父类实体,没有就不用设置! {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 测试驼峰
     */
    private String tTest;

    /**
     * 验证字段
     */
    private String auth;


}
