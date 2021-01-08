package com.binge.controller;


import com.binge.annotation.RequestLimit;
import com.binge.annotation.groups.AddGroup;
import com.binge.annotation.groups.EditGroup;
import com.binge.entity.User;
import com.binge.service.IUserService;
import com.binge.webentity.AxiosResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author binge
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户管理模块")
public class UserController {
    @Autowired
    IUserService iUserService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("test")
    public ResponseEntity test() {
        List<User> list = iUserService.list();
        return ResponseEntity.ok(list);
    }


    @ApiOperation(value = "添加用户", tags = {"添加用户tags"}, notes = "添加用户notes...")
    @ApiImplicitParams({@ApiImplicitParam(name = "user", value = "用户对象")})
    @PostMapping("add")
    public AxiosResult add(@RequestBody @Validated(AddGroup.class) User user) {
        //加密后保存用户认证信息auth，比如密码等等
        String auth = user.getAuth();
        user.setAuth(bCryptPasswordEncoder.encode(auth));
        if (iUserService.save(user)) {
            return AxiosResult.success();
        }
        return AxiosResult.error();
    }

    @DeleteMapping("delete/{id}")
    public AxiosResult delete(@PathVariable int id) {
        if (iUserService.removeById(id)) {
            return AxiosResult.success();
        }
        return AxiosResult.error();
    }

    @PutMapping
    public String edit(@RequestBody @Validated(EditGroup.class) User user) {
        if (iUserService.updateById(user)) {
            return "ok";
        }
        return "error";
    }

    @GetMapping("query/{id}")
    public AxiosResult queryById(@PathVariable int id) {
        User byId = iUserService.getById(id);
        byId = clearUserAuth(byId);
        if (!ObjectUtils.isEmpty(byId)) {
            return AxiosResult.success(byId);
        }
        return AxiosResult.error();
    }

    private User clearUserAuth(User user) {
        return user.setAuth(null);
    }

    @GetMapping("queryAll")
    @RequestLimit(maxCount = 3, second = 30)
    public AxiosResult queryAll(@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "3") int pageSize) {
//        Page<User> userPage = new Page<>(currentPage, pageSize);
//        Page<User> page = iUserService.page(userPage);
//
//        if (!ObjectUtils.isEmpty(page.getRecords())) {
//            return AxiosResult.success(PageResult.instance(page));
//        }
//        return AxiosResult.error();

        PageHelper.startPage(currentPage, pageSize);
        List<User> userList = iUserService.list();
        PageInfo<User> pageInfo = new PageInfo<>(userList);


        userList.forEach(user -> {
            redisTemplate.opsForZSet().add("users", user, RandomUtils.nextInt(10000, 999999));
        });
        Set users = redisTemplate.opsForZSet().reverseRangeByScore("users", 100000, 999999);
        assert users != null;
        users.forEach(System.out::println);
        return AxiosResult.success(new HashMap<String, Object>(userList.size()) {{
            put("data", userList);
            put("total", pageInfo.getTotal());
        }});
    }

    private void asyncFunc1() {
        Future<Object> future = threadPoolExecutor.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                log.info("执行结束");
                return "result";
            }
        });

        threadPoolExecutor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.err.println(future.get());
            }
        });
    }

}
