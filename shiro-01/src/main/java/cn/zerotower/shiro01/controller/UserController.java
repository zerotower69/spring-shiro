package cn.zerotower.shiro01.controller;

import cn.zerotower.shiro01.model.UserInfo;
import cn.zerotower.shiro01.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
     * 创建固定写死的用户
     *
     * @param model
     * @return
     */
    @RequiresPermissions("userInfo:add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public String login(Model model) {

        UserInfo user = new UserInfo();
        user.setName("王赛超");
        user.setUid(564684);
        user.setUsername("wangsaichao");

        userService.insertOne(user);

        return "创建用户成功";

    }

    /**
     * 删除固定写死的用户
     *
     * @param model
     * @return
     */
    @RequiresPermissions("userInfo:del")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    @ResponseBody
    public String del(Model model) {

        userService.deleteByUsername("wangsaichao");

        return "删除用户名为wangsaichao用户成功";

    }

    /**
     * 用户列表页
     *
     * @param model
     * @return
     */
    @RequiresPermissions("userInfo:add")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    public String view(Model model) {

        return "这是用户列表页";

    }


}
