package com.binge.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.binge.annotation.Age;
import com.binge.annotation.IdNotNull;
import com.binge.annotation.IdNull;
import com.binge.annotation.Username;
import com.binge.annotation.groups.AddGroup;
import com.binge.annotation.groups.EditGroup;
import com.binge.annotation.groups.LoginGroup;
import com.binge.interfaces.IDeepClone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements IDeepClone<User>, Serializable {
    @TableId
    @IdNotNull(groups = {EditGroup.class}, message = "分组校验，添加时id必须为空")
    @IdNull(groups = {AddGroup.class}, message = "分组校验，修改时id必须非空")
//    @Null(groups = {AddGroup.class}, message = "分组校验，添加时id必须为空")
//    分组校验，修改时id必须非空
//    @NotNull(groups = {EditGroup.class}, message = "分组校验，修改时id必须非空")
    private Integer id;
    @NotBlank(groups = {AddGroup.class, EditGroup.class})
    @Username(groups = {AddGroup.class, EditGroup.class})
    private String name;
    @Age(groups = {AddGroup.class, EditGroup.class})
    private Long age;
    @Email(groups = {AddGroup.class, EditGroup.class, LoginGroup.class})
    private String email;
    /**
     * 测试驼峰
     */
    private String tTest;
    /**
     * 认证字段
     */
    @NotNull
    @Length(min = 6, max = 30, message = "最小6位，最大30位")
    private String auth;

    /**
     * 1：正常;0：异常
     */
    @NotNull
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

}
