package com.li.emp.config;

import com.li.emp.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 * 用于注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    /**
     * 添加拦截器配置
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册登录检查拦截器，拦截所有请求
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns(
                        "/login",  // 排除登录接口
                        "/**/*.html",  // 排除 HTML 文件
                        "/**/*.js",  // 排除 JS 文件
                        "/**/*.css",  // 排除 CSS 文件
                        "/**/*.png",  // 排除图片文件
                        "/**/*.jpg",  // 排除图片文件
                        "/**/*.jpeg",  // 排除图片文件
                        "/**/*.gif",  // 排除图片文件
                        "/**/*.ico"  // 排除图标文件
                );
    }
}
