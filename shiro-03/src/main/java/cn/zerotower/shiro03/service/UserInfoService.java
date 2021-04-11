package cn.zerotower.shiro03.service;

import cn.zerotower.shiro03.model.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户服务
 * @Author ZeroTower
 * @Date 2021/4/7 10:20
 * @Description
 * @Package cn.zerotower.shiro01.service
 * @PROJECT shiro-01
 **/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 增加用户
     * @param user
     * @return
     */
    Integer insertOne(UserInfo user);

    /**
     * 删除用户通过用户名
     * @param username
     */
    void deleteByUsername(String username);

    /**
     * 根据用户id找到所有的角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<String> getAllRolesByUserId(Integer userId);

    /**
     * 根据用户id找到所有的权限
     * @param userId 用户id
     * @return 权限列表
     */
    List<String> getAllPermsByUsername(Integer userId);

}
