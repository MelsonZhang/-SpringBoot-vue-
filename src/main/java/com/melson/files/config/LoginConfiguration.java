package com.melson.files.config;

import com.melson.files.Utils.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/FileTransport.html");
        loginRegistry.addPathPatterns("/Messages.html");
        // 排除路径
        loginRegistry.excludePathPatterns("/Login.html");
        loginRegistry.excludePathPatterns("/getFilesByMoble");
        loginRegistry.excludePathPatterns("/downloadByMobel");

    }
}