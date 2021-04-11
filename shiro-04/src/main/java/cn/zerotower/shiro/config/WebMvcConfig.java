package cn.zerotower.shiro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ZeroTower
 * @Date 2021/4/10 23:17
 * @Description WebMvc 配置
 * @Package cn.zerotower.shiro.config
 * @PROJECT shiro-04
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
//        super.configurePathMatch(configurer);
        // 常用的两种
        // 匹配结尾 / :会识别 url 的最后一个字符是否为 /
        // 设置为true: localhost:8080/test 与 localhost:8080/test/ 等价
        configurer.setUseTrailingSlashMatch(true);
        // 匹配后缀名：会识别 xx.* 后缀的内容
        // 设置为true: localhost:8080/test 与 localhost:8080/test.jsp 等价
        configurer.setUseSuffixPatternMatch(true);
    }

    /**
     * 静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
