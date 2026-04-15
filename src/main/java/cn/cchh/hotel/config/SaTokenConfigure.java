package cn.xt.sup.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
                    if (!(handler instanceof org.springframework.web.method.HandlerMethod)) {
                        return;
                    }
                    String path = ((org.springframework.web.method.HandlerMethod) handler).getBeanType().getName() +
                            "#" + ((org.springframework.web.method.HandlerMethod) handler).getMethod().getName();
                    StpUtil.checkLogin();
                }))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/login",
                        "/auth/register",
                        "/error",
                        "/ShoppingRecord/ShoppingRecordPage",
                        "/images/**",           // 放行图片资源
                        "/static/**",           // 放行静态资源
                        "/*.jpg",               // 放行根目录下的jpg文件
                        "/*.png",               // 放行根目录下的png文件
                        "/*.gif",               // 放行根目录下的gif文件
                        "/*.jpeg",              // 放行根目录下的jpeg文件
                        "/*.webp" ,              // 放行根目录下的webp文件
                        "/user/test"
                );
    }
}