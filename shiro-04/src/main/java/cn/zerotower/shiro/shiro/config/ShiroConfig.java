package cn.zerotower.shiro.shiro.config;



import cn.zerotower.shiro.shiro.filter.CustomUserFilter;
import cn.zerotower.shiro.shiro.realm.MyRealm;
import cn.zerotower.shiro.shiro.redis.ZeroRedisSessionDAO;
import cn.zerotower.shiro.shiro.redis.ZeroSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: zerotower
 * @date: 2021/4/11
 * @description: Shiro配置
 * 记得导入此包： import org.apache.shiro.mgt.SecurityManager;
 */
@Slf4j
@Configuration
public class ShiroConfig {


    @Resource
    protected LettuceConnectionFactory factory;


//    private static final String CACHE_KEY = "shiro:cache:";
//    private static final String SESSION_KEY = "shiro:session:";
//    private static final String NAME = "custom.name";
//    private static final String VALUE = "/";

    /**
     * Realm的配置 自定义安全域，用户验证、权限等数据在此提供
     *
     * @return
     */
    @Bean("myRealm")
    public Realm getMyRealm() {
        MyRealm realm = new MyRealm();
        //开启缓存
        realm.setCachingEnabled(true);
        //自行处理这个加密过程
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
//        //委托redis cache 缓存
//        realm.setCacheManager(redisCacheManager());
        return realm;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
     *
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
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
     * 注册shiro的filter，用于拦截所有请求
     * @return 注册的filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }


    /**
     * ShiroFilterFactoryBean 过滤器工厂
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     *
     * @param securityManager rrr
     * @return bean
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //获取filters
        Map filters = shiroFilterFactoryBean.getFilters();
        //注入自定义的AccessControlFilter，返回json数据
        filters.put("authc", new CustomUserFilter());
        //必须设置 SecurityManager,Shiro的核心安全接口
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * @Mark 由于重写了 UserFilter，不再需要前台页面
         *@authc 重写了FormAuthenticationFilter过滤器，不需要再写setLoginUrl，否则会发生多次重定向
         */
        //自定义拦截器限制并发人数,参考博客：
        //LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);

        // 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 --> : 这是一个坑，一不小心代码就不好使了
        // 构造需要接口的访问权限 anon表示所有人可以访问  authc表示需要认证
        // 接口是linkedHashMap 会按顺序过滤
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //登录部分权限完全开放
        filterChainDefinitionMap.put("/user/**", "anon");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "anon");
        //此时访问/user/delete需要delete权限,在自定义Realm中为用户授权。
        //filterChainDefinitionMap.put("/userInfo/del", "perms[\"userInfo:del\"]");

        //其他资源都需要认证  authc 表示需要认证才能进行访问
        filterChainDefinitionMap.put("/userInfo/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器  核心
     * @return  安全管理器
     */
    @Bean("securityManager")
    @Primary
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getMyRealm());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
//        securityManager.setCacheManager(ca);
        return securityManager;
    }

    /**
     * 自定义sessionManager，使用http header方式传入sessionId
     *
     * @return  会话管理器
     */
    @Bean
    public SessionManager sessionManager() {
        ZeroSessionManager zeroSessionManager = new ZeroSessionManager();
        zeroSessionManager.setSessionIdCookieEnabled(true);
        zeroSessionManager.setSessionDAO(sessionDAO());
        return zeroSessionManager;
    }

    /**
     * redis template
     * @param factory
     * @return  redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplateBean(LettuceConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<String,Object>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis session dao
     *
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        return new ZeroRedisSessionDAO();
    }

//    /**
//     * 自定义的缓存管理
//     * @return
//     */
//    @Bean("cacheManager")
//    public RedisCacheManager cacheManager(){
//        RedisCacheManager redisCacheManager=new RedisCacheManager(redisTemplateBean(factory));
//        return redisCacheManager;
//    }

    /**
     * xxx 代理  解决权限不生效的问题
     *
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
     *
     * @return
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启aop注解,也就是controller层的权限和角色的注解
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
