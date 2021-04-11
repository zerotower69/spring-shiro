package cn.zerotower.shiro.service;

import cn.zerotower.shiro.dto.UserDto;
import cn.zerotower.shiro.model.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

/**
 * 用户服务
 * @Author ZeroTower
 * @Date 2021/4/7 10:20
 * @Description
 * @Package cn.zerotower.shiro01.service
 * @PROJECT shiro-01
 **/
@CacheConfig(cacheNames = "user")
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

    /**
     * 用户主要信息，不包含密码
     * @return 用户主要信息列表
     */
    List<UserDto> listAllUserInfo();

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    UserInfo findByUserName(String username);

}
