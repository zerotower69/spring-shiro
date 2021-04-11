package cn.zerotower.shiro.controller;

import cn.zerotower.shiro.common.Result;
import cn.zerotower.shiro.common.StatusConst;
import cn.zerotower.shiro.model.SysRole;
import cn.zerotower.shiro.model.UserInfo;
import cn.zerotower.shiro.service.UserInfoService;
import cn.zerotower.shiro.utils.Md5Utils;
import cn.zerotower.shiro.vo.LoginUser;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author: wangsaichao
 * @date: 2018/5/11
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    protected UserInfoService userInfoService;



    /**
     * 用户登录,并防止重复登录
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result loginUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        //使用该方法会调用Realm中的doGetAuthenticationInfo
        if(subject.isAuthenticated()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId",subject.getSession().getId());
            return new  Result(true,StatusConst.ERROR,"已登录，切勿重复登录",jsonObject);
        }
        subject.login(usernamePasswordToken);
        if(subject.isAuthenticated()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId",subject.getSession().getId());
           return  new Result(true, StatusConst.OK,"登录成功",jsonObject);
        }
        else{
           return new Result(true,StatusConst.LOGIN_FAILED,"登录失败");
        }
    }


    /**
     * 获取已经登录的用户信息以及角色名和权限信息
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/get/userInfo")
    public Result getUserInfo(HttpServletRequest request,HttpSession session){
        Subject subject=SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return new Result(true,StatusConst.ERROR,"未登录，请先登录");
        }

       JSONObject data=new JSONObject();
        //TODO: 后续还要把密码信息移除
       UserInfo principals=(UserInfo)SecurityUtils.getSubject().getPrincipal();
       data.put("userInfo",principals);
        List<String> roleList=userInfoService.getAllRolesByUserId(principals.getUid());
        data.put("role",roleList);
        List<String>permsList=userInfoService.getAllPermsByUsername(principals.getUid());
        data.put("permission",permsList);
       //TODO  补充信息
        return new Result(true,StatusConst.OK,"获取用户信息成功",data);
    }


    /**
     * 退出登录，并防止没有登录用户也登录
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpSession session, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
            return new Result(true,StatusConst.OK,"退出账号成功");
        }
          else{
              return new Result(true,StatusConst.ERROR,"当前客户端无登录用户");
        }
    }

    /**
     * 注册账号
     * @param request
     * @return
     */
    @PostMapping("/logup")
    public Result logup(HttpServletRequest request, @RequestBody JSONObject logupUser){

        String username=logupUser.getString("username");
        String password=logupUser.getString("password");
        //系统中用户是否存在？
        UserInfo userInfo=userInfoService.findByUserName(username);
        if(userInfo!=null){
            return new Result(true,StatusConst.ERROR,"此用户名已经被注册");
        }
        //密码加密
        password= Md5Utils.computeMd5(username,password);
        UserInfo userInfo1=new UserInfo();
        userInfo.setPassword(password);
        userInfo.setUsername(username);
        userInfo.setState("0");
        userInfoService.saveOrUpdate(userInfo);
        return new Result(true,StatusConst.OK,"用户添加成功");
    }
}
