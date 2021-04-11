package cn.zerotower.shiro03.controller;

import cn.zerotower.shiro03.common.Result;
import cn.zerotower.shiro03.common.StatusConst;
import cn.zerotower.shiro03.model.UserInfo;
import cn.zerotower.shiro03.service.UserInfoService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: wangsaichao
 * @date: 2018/5/12
 * @description:
 */
@RestController
@RequestMapping("userInfo")
public class UserController {

    @Autowired
    private UserInfoService userService;

    /**
     * 创建某用户
     * @return
     */
    @RequiresPermissions("userInfo:add")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, @RequestBody JSONObject object) {
        try{
            UserInfo user = new UserInfo();
            user.setUsername(object.getString("username"));
            user.setPassword(object.getString("password"));

            userService.insertOne(user);
            return new Result(true, StatusConst.OK,"用户创建成功");
        }
        catch (Exception e){
            return new Result(false,StatusConst.ERROR,"用户创建失败");
        }

    }

    /**
     * 删除固定写死的用户
     * @return
     */
    @RequiresPermissions("userInfo:del")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public Result del(HttpServletRequest request, @RequestBody String username) {
        try{
            userService.deleteByUsername(username);
            return new Result(true,StatusConst.OK,"删除用户成功");
        }

        catch (Exception e){
            return new Result(false,StatusConst.ERROR,"删除用户失败");
        }

    }

    /**
     * 用户列表页
     * @param model
     * @return
     */
    @RequiresPermissions("userInfo:add")
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    @ResponseBody
    public String view(Model model) {

        return "这是用户列表页";

    }


}
