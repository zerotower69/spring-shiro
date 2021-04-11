package cn.zerotower.shiro03.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.apache.shiro.mgt.SecurityManager;
import java.util.LinkedHashMap;

/**
 * @author: wangsaichao
 * @date: 2018/5/10
 * @description: Shiro配置
 * 记得导入此包： import org.apache.shiro.mgt.SecurityManager;
 */
@Slf4j
@Configuration
public class ShiroConfig {

    private static final String CACHE_KEY = "shiro:cache:";
    private static final String SESSION_KEY = "shiro:session:";
    private static final String NAME = "custom.name";
    private static final String VALUE = "/";


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     * @param securityManager rrr
     * @return   bean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        log.info("进入shiro过滤链");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager( securityManager);
        //这里的/login是后台的接口名,非页面，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //这里的/index是后台的接口名,非页面,登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面,该配置无效，并不会进行页面跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        //自定义拦截器限制并发人数,参考博客：
        //LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);

        // 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 --> : 这是一个坑，一不小心代码就不好使了
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "logout");
        //此时访问/user/delete需要delete权限,在自定义Realm中为用户授权。
        //filterChainDefinitionMap.put("/userInfo/del", "perms[\"userInfo:del\"]");

        //其他资源都需要认证  authc 表示需要认证才能进行访问
        filterChainDefinitionMap.put("/userInfo/**","authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * web管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getSessionManager() {
        // 默认的web管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义的realm
        securityManager.setRealm(getMyRealm(hashedCredentialsMatcher()));
        // 返回
        return securityManager;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *      * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(3);
        //此处的设置，true加密用的hex编码，false用的base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 只有添加这个才能在thymeleaf tag进行权限控制
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {

        return new ShiroDialect();
    }

        /**
         * xxx 代理  解决权限不生效的问题
         * @return
         */
    @Bean("defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        //指定强制使用cglib为action创建代理对象
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 生命周期
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public Realm getMyRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyRealm realm = new MyRealm();
        //自行处理这个加密过程
        //禁止缓存
        realm.setCachingEnabled(false);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return realm;
    }

    /**
     * 开启aop注解,也就是controller层的权限和角色的注解
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
