package cn.zerotower.shiro03.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 会话监听器用于监听会话创建、过期及停止事件：
 * https://www.w3cschool.cn/shiro/rmvk1if1.html
 * @Author ZeroTower
 * @Date 2021/4/7 14:37
 * @Description
 * @Package cn.zerotower.shiro02.shiro
 * @PROJECT shiro-02
 **/

@Slf4j
public class ShiroSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    /**
     * 创建会话时触发
     * @param session
     */
    @Override
    public void onStart(Session session) {
        log.info("会话创建，会话id={}",session.getId());
        sessionCount.incrementAndGet();
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        log.info("会话停止，会话id={}",session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        log.info("会话过期,会话id={}",session.getId());
    }
    /**
     * 获取在线人数使用
     * @return
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
