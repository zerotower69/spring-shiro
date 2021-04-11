package cn.zerotower.shiro.shiro.filter;

import cn.zerotower.shiro.shiro.realm.MyRealm;
import cn.zerotower.shiro.shiro.redis.ZeroSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Author ZeroTower
 * @Date 2021/4/11 13:34
 * @Description  继承logoutFilter 来实现退出后清除  redis 中的值
 * @Package cn.zerotower.shiro.shiro.filter
 * @PROJECT shiro-04
 **/
//@Slf4j
//@Component("loginFilter")
//public class ZeroLogoutFilter extends LogoutFilter {
//
//    @Autowired
//    protected RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//
//        //1.获取Subject
//        Subject subject=getSubject(request,response);
//        //2.转换为获取securityManager
//        DefaultWebSecurityManager securityManager=(DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
//        //3.获取realm
//        MyRealm myRealm=(MyRealm) securityManager.getRealms().iterator().next();
//        PrincipalCollection principals = subject.getPrincipals();
//        ZeroSessionManager manager=new ZeroSessionManager();
//        Serializable sessionId=manager.getSessionId(request,response);
//        boolean delete =redisTemplate.delete(sessionId.toString());
//        log.info("删除结果,{}",delete);
//        return false;
//    }
//
//}
