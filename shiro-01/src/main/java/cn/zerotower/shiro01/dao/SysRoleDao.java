package cn.zerotower.shiro01.dao;

import cn.zerotower.shiro01.dto.RoleDto;
import cn.zerotower.shiro01.model.SysPermission;
import cn.zerotower.shiro01.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author ZeroTower
 * @Entity cn.zerotower.shiro01.model.SysRole
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRole> {

    /**
     * 找到所有的权限
     *
     * @param roleId
     * @return
     */
    RoleDto findAllPermissionsByRoleId(Integer roleId);

    /**
     * 找到所有的权限,优化写法
     *
     * @param roleId
     * @return
     */
    Set<SysPermission> findPermissionsByRoleId(Integer roleId);

}