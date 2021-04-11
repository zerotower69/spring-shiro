package cn.zerotower.shiro.shiro.filter;

import cn.zerotower.shiro.common.Result;
import cn.zerotower.shiro.common.StatusConst;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author ZeroTower
 * @Date 2021/4/10 19:13
 * @Description 继承  AccessControlFilter  ，为了拒绝登录时不跳转到登录页面，而是返回JSON数据
 * @Package cn.zerotower.shiro.shiro
 * @PROJECT shiro-04
 **/
@Component("customUser")
public class CustomUserFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return true;
    }

    /**
     * 拦截时返回  JSON，而不是跳转到一个loginUrl
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws  Exception{
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));

        res.setHeader("Access-Control-Allow-Credentials","true");

        res.setCharacterEncoding("UTF-8");

        res.setContentType("application/json");
       Result result= new Result(true, StatusConst.NOT_LOGIN,"未登录");
        res.getWriter().write(JSONObject.toJSON(result).toString());
        return false;
    }
}
