package cn.zerotower.shiro01.shiro;

import cn.zerotower.shiro01.dao.SysRoleDao;
import cn.zerotower.shiro01.dao.UserInfoDao;
import cn.zerotower.shiro01.dto.RoleDto;
import cn.zerotower.shiro01.model.SysPermission;
import cn.zerotower.shiro01.model.SysRole;
import cn.zerotower.shiro01.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 9:50
 * @Description
 * @Package cn.zerotower.shiro01.shiro
 * @PROJECT shiro-01
 **/
public class MyRealm extends AuthorizingRealm {

//    @Override
//    protected String

    @Resource
    protected UserInfoDao userInfoDao;

    @Resource
    protected SysRoleDao sysRoleDao;

    /**
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
     * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     * <p>
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     * <p>
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
     * <p>
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
     * <p>
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     * <p>
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
     * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //获取用户的角色
        Set<SysRole> roles = this.userInfoDao.findRolesByUserId(user.getUid());

        for (SysRole role : roles) {
            authorizationInfo.addRole(role.getRole());
            //获取权限
            Set<SysPermission> permissions = this.sysRoleDao.findPermissionsByRoleId(role.getId());
            for (SysPermission permission : permissions) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }

        return authorizationInfo;

    }

    //获取用户名密码 第一种方式
    //String username = (String) authenticationToken.getPrincipal();
    //String password = new String((char[]) authenticationToken.getCredentials());

    //获取用户名 密码 第二种方式
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        //从数据库查询用户信息
        UserInfo user = this.userInfoDao.findOneByUsername(username);

        //可以在这里直接对用户名校验,或者调用 CredentialsMatcher 校验
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if ("1".equals(user.getState())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        //调用 CredentialsMatcher 校验 还需要创建一个类 继承CredentialsMatcher  如果在上面校验了,这个就不需要了
        //配置自定义权限登录器 参考博客：

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return info;
    }
}
