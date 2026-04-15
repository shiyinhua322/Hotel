package cn.xt.sup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:1080/"); // 允许的源（不能包含末尾斜杠）
        config.addAllowedHeader("*"); // 允许的头部
        config.addAllowedMethod("*"); // 允许的方法
        source.registerCorsConfiguration("/**", config); // 对所有路径应用 CORS 配置
        return new CorsFilter(source);
    }
}