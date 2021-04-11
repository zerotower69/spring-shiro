package cn.zerotower.shiro02.service.impl;

import cn.zerotower.shiro02.dao.UserInfoDao;
import cn.zerotower.shiro02.model.UserInfo;
import cn.zerotower.shiro02.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 10:25
 * @Description
 * @Package cn.zerotower.shiro01.service.impl
 * @PROJECT shiro-01
 **/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;


    @Override
    public Integer insertOne(UserInfo user) {
        return userInfoDao.insert(user);
    }

    @Override
    public void deleteByUsername(String username) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        userInfoDao.delete(queryWrapper);
    }
}
