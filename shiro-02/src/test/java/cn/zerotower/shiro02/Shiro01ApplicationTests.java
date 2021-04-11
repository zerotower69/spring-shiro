package cn.zerotower.shiro02;

import cn.zerotower.shiro02.dao.SysRoleDao;
import cn.zerotower.shiro02.dao.UserInfoDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Shiro01ApplicationTests {

    @Resource
    protected UserInfoDao userInfoDao;

    @Resource
    protected SysRoleDao sysRoleDao;

    @Test
    void contextLoads() {
        System.out.println(userInfoDao.findRolesByUserId(1));
    }

    @Test
    void roleDaoTest() {
        System.out.println(sysRoleDao.findPermissionsByRoleId(1));
    }

}
