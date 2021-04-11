package cn.zerotower.shiro03.service.impl;

import cn.zerotower.shiro03.dao.SysRoleDao;
import cn.zerotower.shiro03.dao.UserInfoDao;
import cn.zerotower.shiro03.exception.CustomException;
import cn.zerotower.shiro03.model.SysPermission;
import cn.zerotower.shiro03.model.SysRole;
import cn.zerotower.shiro03.model.UserInfo;
import cn.zerotower.shiro03.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 10:25
 * @Description
 * @Package cn.zerotower.shiro01.service.impl
 * @PROJECT shiro-01
 **/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao,UserInfo> implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private SysRoleDao sysRoleDao;


    @Override
    public Integer insertOne(UserInfo user) {
      return   userInfoDao.insert(user);
    }

    @Override
    public void deleteByUsername(String username) {
        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        userInfoDao.delete(queryWrapper);
    }

    @Override
    public List<String> getAllRolesByUserId(Integer userId) {
        Set<SysRole> roleSet=userInfoDao.findRolesByUserId(userId);
        if(roleSet==null||roleSet.size()==0){
            throw new CustomException("无法找到用户角色信息");
        }
        List<String> rt=new ArrayList<>();
       roleSet.forEach(sysRole -> {
            rt.add(sysRole.getRole());
        });
       return rt;
    }

    @Override
    public List<String> getAllPermsByUsername(Integer userId) {
        Set<SysRole> roleSet=userInfoDao.findRolesByUserId(userId);
        if(roleSet==null||roleSet.size()==0){
            throw new CustomException("无法找到用户角色信息");
        }
        List<String> rt=new ArrayList<>();
        roleSet.forEach(sysRole -> {
            Set<SysPermission> permissionSet=sysRoleDao.findPermissionsByRoleId(sysRole.getId());
            if(permissionSet.size()!=0){
                permissionSet.forEach(sysPermission -> {
                    rt.add(sysPermission.getPermission());
                });
            }
        });
        return rt;
    }
}
