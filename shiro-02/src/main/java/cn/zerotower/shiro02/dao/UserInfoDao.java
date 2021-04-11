package cn.zerotower.shiro02.dao;

import cn.zerotower.shiro02.dto.UserDto;
import cn.zerotower.shiro02.model.SysRole;
import cn.zerotower.shiro02.model.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author ZeroTower
 * @Entity cn.zerotower.shiro01.model.UserInfo
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

    /**
     * @param userId
     * @return
     */
    UserDto findAllRoleByUserID(Integer userId);

    UserInfo findOneByUsername(String username);

    Set<SysRole> findRolesByUserId(Integer userId);
}