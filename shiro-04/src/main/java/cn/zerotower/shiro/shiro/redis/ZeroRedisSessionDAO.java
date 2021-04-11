package cn.zerotower.shiro.shiro.redis;

import cn.zerotower.shiro.shiro.redis.RedisCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;


/**
 * Redis 操作类
 * @author ZeroTower
 */
@Slf4j
public class ZeroRedisSessionDAO extends AbstractSessionDAO {

    //自定义的前缀
    //会话的前缀
    private final String PREFIX = "ZEROTOWER";

    @Autowired
    protected RedisCacheManager redisCacheManager;

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    /**
     *创建session 保存到数据库
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable id = super.generateSessionId(session);
        ((SimpleSession)session).setId(id);
        log.info("doCreate session:" + id);
        // 必须要将生成的id设置到session实例当中
        redisCacheManager.hset(PREFIX,id.toString(),session);
        return id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = (Session)redisCacheManager.hget(PREFIX,sessionId.toString());
        log.info("doReadSession session:" + sessionId);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Serializable id = session.getId();
        log.info("update session id,and id={}",id);
        if(id == null){
            throw new NullPointerException();
        }
        redisCacheManager.hset(PREFIX,id.toString(),session);

    }

    @Override
    public void delete(Session session) {
        Serializable id = session.getId();
        log.info("delete id, now ,the id={}",id);
        redisCacheManager.hdel(PREFIX,id.toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        log.debug("get all active sessions");
        return redisCacheManager.hgetAllValues(PREFIX);
    }
}
