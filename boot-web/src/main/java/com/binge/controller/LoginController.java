//package com.binge.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.binge.annotation.RequestLimit;
//import com.binge.annotation.groups.LoginGroup;
//import com.binge.common.Constant;
//import com.binge.common.mail.EmailSender;
//import com.binge.entity.User;
//import com.binge.service.IUserService;
//import com.binge.utils.JsonUtil;
//import com.binge.utils.JwtTokenUtils;
//import com.binge.utils.JwtUtil;
//import com.binge.webentity.AxiosResult;
//import io.swagger.annotations.Api;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.RandomUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
///**
// * 佛祖保佑  永无BUG
// *
// * @author: HuangJiaBin
// * @date: 2020年 12月18日
// * @description:
// **/
//@RestController
//@RequestMapping("login")
//@Api("登录模块")
//public class LoginController {
//    @Autowired
//    IUserService iUserService;
//    @Autowired
//    RedisTemplate redisTemplate;
//    @Autowired
//    JsonUtil jsonUtil;
//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    EmailSender emailSender;
//
//
//    @PostMapping("email")
//
//    public AxiosResult doLogin(@RequestBody @Validated(LoginGroup.class) User user) {
//
//        String email = user.getEmail();
//        String authInRedis = String.valueOf(redisTemplate.opsForValue().get(Constant.REDIS_EMAIL + email));
//        if (ObjectUtils.isEmpty(authInRedis)){
//            return AxiosResult.error("请先发送邮箱验证码,未找到您的邮箱！");
//        }
//        if ( !user.getAuth().equals(authInRedis)){
//            return AxiosResult.error("邮箱验证码不正确！");
//        }
//        User one = iUserService.getOne(new QueryWrapper<User>().lambda().eq(User::getEmail, email).last("limit 1"));
//        String uuid = UUID.randomUUID().toString();
//        System.err.println("生成的uuid是"+uuid);
//        String token = JwtTokenUtils.createToken(uuid);
//        //redis存储：常量前缀+uuid---User对象
//        redisTemplate.opsForValue().set(Constant.REDIS_UUID+uuid, jsonUtil.obj2Str(one));
//        //token生成后返回给前端
//        return AxiosResult.success(token);
//    }
//    @GetMapping("sendEmailCode")
//    /**
//     *
//     */
//    @RequestLimit(second = 60,maxCount = 1)
//    public AxiosResult sendEmailCode(@RequestParam  @NotNull @NotBlank @Email String email){
//        email=email.trim();
//        //如果已经有了该邮件地址，说明不久前才发送过
//        String s = (String) redisTemplate.opsForValue().get(Constant.REDIS_EMAIL + email);
//        if (ObjectUtils.isNotEmpty(s)){
//            sendVerifyCodeEmail(email);
//            return AxiosResult.error("验证码已经发送过了，本次发送将重置验证码");
//        }
//
//        sendVerifyCodeEmail(email);
//        return AxiosResult.success();
//    }
//
//    private void sendVerifyCodeEmail(@RequestParam @NotNull @NotBlank @Email String email) {
//        int code = RandomUtils.nextInt(100000, 999999);
//        //30分钟过期
//        redisTemplate.opsForValue().set(Constant.REDIS_EMAIL + email, code + "", 30, TimeUnit.MINUTES);
//        emailSender.sendSimpleMail(email, "您的验证码是：" + code + "\n30分钟后失效，请尽快登录");
//    }
//}
