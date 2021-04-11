package cn.zerotower.shiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 14:06
 * @Description 数据源事务管理
 * @Package cn.zerotower.shiro02.config
 * @PROJECT shiro-02
 **/
@Configuration
public class TransactionConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
