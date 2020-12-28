package com.binge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binge.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author binge
 * @since 2020-12-14
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> myQuery(String name);

}
