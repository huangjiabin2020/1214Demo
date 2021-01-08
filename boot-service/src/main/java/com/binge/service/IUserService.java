package com.binge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binge.entity.Menu;
import com.binge.entity.Role;
import com.binge.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author binge
 * @since 2021-01-04
 */
public interface IUserService extends IService<User> {

    List<Menu> selectMenuByUserId(Integer userId);

    Role selectRoleByUserId(Integer userId);
}
