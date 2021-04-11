package cn.zerotower.shiro.controller;

import cn.zerotower.shiro.common.Result;
import cn.zerotower.shiro.common.StatusConst;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZeroTower
 * @Date 2021/4/10 23:40
 * @Description  根路径下获取资源
 * @Package cn.zerotower.shiro.controller
 * @PROJECT shiro-04
 **/
@RestController
public class IndexController {


    /**
     * 访问项目根路径
     * @return
     */
    @GetMapping("/")
    public Result root(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            return new Result(true, StatusConst.OK,"后台资源根路径，你已经登录,请访问其它资源");
        }
        else{
            return new Result(true,StatusConst.NOT_LOGIN,"未登录，请登录");
        }
    }
}
