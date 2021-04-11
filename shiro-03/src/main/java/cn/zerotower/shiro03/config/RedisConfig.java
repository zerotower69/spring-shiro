package cn.zerotower.shiro03.config;//package cn.zerotower.shiro02.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * Created by yangqj on 2017/4/30.
// */
//@Slf4j
//////@Configuration
//////@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.pool.max-wait-millis}")
//    private long maxWaitMillis;
//
//    @Value("${spring.redis.pool.max-total}")
//    private int maxActive;
//
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Bean
//    public JedisPool redisPoolFactory() {
//        log.info("JedisPool注入成功！！");
//        log.info("redis地址：" + host + ":" + port+"/"+password);
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
//        //TODO: 检查密码设置
////        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
//        Jedis resource=jedisPool.getResource();
//        log.info("数据源={}",resource.toString());
//        return jedisPool;
//    }
//
//}
