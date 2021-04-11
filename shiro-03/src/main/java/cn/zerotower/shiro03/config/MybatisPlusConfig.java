package cn.zerotower.shiro03.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 8:58
 * @Description
 * @Package cn.zerotower.shiro01.config
 * @PROJECT shiro-01
 **/
@Configuration
@MapperScan(basePackages = "cn.zerotower.shiro03.dao")
public class MybatisPlusConfig {

    /**
     * 分页配置
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}
