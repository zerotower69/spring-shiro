package cn.zerotower.shiro.dao;

import cn.zerotower.shiro.dto.UserDto;
import cn.zerotower.shiro.model.SysRole;
import cn.zerotower.shiro.model.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;

/**
 * @author ZeroTower
 * @Entity cn.zerotower.shiro01.model.UserInfo
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {


    /**
     * 通过用户名找到一个用户实体
     * @param username
     * @return
     */
    UserInfo findOneByUsername(String username);

    /**
     * 找到所有的角色实体
     * @param userId
     * @return
     */
    Set<SysRole> findRolesByUserId(Integer userId);

    /**
     * 通过用户名找到密码
     * @param username
     * @return
     */
    String findPassWordByUsername(String username);

    List<UserDto> findAllDTO();
}