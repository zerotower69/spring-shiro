package cn.zerotower.shiro03.controller;

import cn.zerotower.shiro03.model.UserInfo;
import cn.zerotower.shiro03.utils.Md5Utils;
import cn.zerotower.shiro03.vo.LoginUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: wangsaichao
 * @date: 2018/5/11
 * @description:
 */
@Slf4j
@Controller
public class LoginController {

    /**
     * 访问项目根路径
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String root(Model model) {
        Subject subject = SecurityUtils.getSubject();
        UserInfo user=(UserInfo) subject.getPrincipal();
        if (user == null){
            return "redirect:/login";
        }else{
            return "redirect:/index";
        }

    }

    /**
     * 跳转到login页面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model) {
        Subject subject = SecurityUtils.getSubject();
        UserInfo user=(UserInfo) subject.getPrincipal();
        if (user == null){
            return "login";
        }else{
            return "redirect:index";
        }

    }

    /**
     * 用户登录
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginUser(HttpServletRequest request, @RequestParam String username,@RequestParam String password, Model model, HttpSession session) {

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        //使用该方法会调用Realm中的doGetAuthenticationInfo
        subject.login(usernamePasswordToken);
        if(subject.isAuthenticated()){
            log.info("通过这里进入的首页！！！！");
            model.addAttribute("username",username);
            return "index";
        }
        else{
            model.addAttribute("msg","登录失败。重新登录");
            return "login";
        }
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UserInfo user=(UserInfo) subject.getPrincipal();
        log.info("用户名={}",subject.getPrincipal().toString());
        if (user == null){
            return "login";
        }else{
            model.addAttribute("username",user.getUsername());
            return "index";
        }
    }

    /**
     * 登出  这个方法没用到,用的是shiro默认的logout
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }

    /**
     * 跳转到无权限页面
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/unauthorized")
    public String unauthorized(HttpSession session, Model model) {
        return "unauthorized";
    }


}
