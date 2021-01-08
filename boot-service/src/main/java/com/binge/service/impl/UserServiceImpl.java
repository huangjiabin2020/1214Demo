package com.binge.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binge.entity.*;
import com.binge.mapper.UserMapper;
import com.binge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author binge
 * @since 2021-01-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    IRoleMenuService roleMenuService;
    @Autowired
    IRoleService roleService;
    @Override
    public List<Menu> selectMenuByUserId(Integer userId) {
        UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userId));
        List<RoleMenu> roleMenus = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, userRole.getRoleId()));
        List<Integer> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return menuService.list(new LambdaQueryWrapper<Menu>().in(Menu::getMenuId, menuIds));
    }

    @Override
    public Role selectRoleByUserId(Integer userId) {
        UserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        return roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleId, userRole.getRoleId()).last("limit 1"));
    }
}
