package com.binge.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@Component
@Slf4j
public class EmailSender {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ThreadPoolExecutor executor;
    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.subject}")
    private String subject;

    public void sendSimpleMail(String to, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        executor.submit(()->{
            log.info("向:{}发送了邮件",to);
            mailSender.send(message);
        });
    }
}

