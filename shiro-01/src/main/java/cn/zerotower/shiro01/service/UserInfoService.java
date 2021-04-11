package cn.zerotower.shiro01.service;

import cn.zerotower.shiro01.model.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
 *
 * @Author ZeroTower
 * @Date 2021/4/7 10:20
 * @Description
 * @Package cn.zerotower.shiro01.service
 * @PROJECT shiro-01
 **/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 增加用户
     *
     * @param user
     * @return
     */
    Integer insertOne(UserInfo user);

    /**
     * 删除用户通过用户名
     *
     * @param username
     */
    void deleteByUsername(String username);

}
