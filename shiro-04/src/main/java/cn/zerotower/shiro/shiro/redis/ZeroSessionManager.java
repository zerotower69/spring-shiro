package cn.zerotower.shiro.shiro.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author ZeroTower
 */
@Slf4j
public class ZeroSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "header";
    private static final String MY_SESSION_ATTRIBUTE = "MY_SESSION_ATTRIBUTE";

    public ZeroSessionManager() {
        super();
    }


    /**
     * 获取sessionId的方法
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }


    /**
     * 分情况判断从 request还是redis中取出这个数据
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);

        if (sessionId == null) {
            return null;
        }

        ServletRequest request = WebUtils.getRequest(sessionKey);

        if(request.getAttribute(MY_SESSION_ATTRIBUTE) != null){
//            log.debug("Get Session from request!");
            return (Session)request.getAttribute(MY_SESSION_ATTRIBUTE);
        }else{
//            log.debug("Get Session from redis!");
            Session s = retrieveSessionFromDataSource(sessionId);
            if (s == null) {
                //session ID was provided, meaning one is expected to be found, but we couldn't find one:
                String msg = "Could not find session with ID [" + sessionId + "]";
                //TODO：此异常未正确处理
                throw new UnknownSessionException(msg);
            }
            request.setAttribute(MY_SESSION_ATTRIBUTE,s);
            return s;
        }
    }
}