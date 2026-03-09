package com.lianyue.config;

import com.lianyue.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 默认拦截所有路径
                //白名单：不需要登录也能访问的接口
                .excludePathPatterns(
                        "/user/login",      // 登录
                        "/user/register"   // 注册
                );
    }
}