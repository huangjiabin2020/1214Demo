package com.binge.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: JiaBin Huang
 * @date: 2020年 12月15日
 * @description:
 **/
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        //添加head参数start
////        ParameterBuilder tokenPar = new ParameterBuilder();
////        List<Parameter> pars = new ArrayList<Parameter>();
////        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
////        pars.add(tokenPar.build());
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.binge"))
//                .paths(PathSelectors.any())
//                .build();
////                .globalOperationParameters(pars);
//    }
//
//    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                //页面标题
//                .title("测试标题")
//                //创建人
//                .contact(new Contact("Curl-Li", "", "handsomebinge@163.com"))
//                //版本号
//                .version("1.0")
//                //描述
//                .description("描述测试")
//                .build();
//    }
}
