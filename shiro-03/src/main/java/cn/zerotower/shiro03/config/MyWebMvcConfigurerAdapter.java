package cn.zerotower.shiro03.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 10:46
 * @Description
 * @Package cn.zerotower.shiro01.config
 * @PROJECT shiro-01
 **/
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

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

}
