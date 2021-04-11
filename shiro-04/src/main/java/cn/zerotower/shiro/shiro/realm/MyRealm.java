package cn.zerotower.shiro.shiro.realm;

import cn.zerotower.shiro.dao.SysRoleDao;
import cn.zerotower.shiro.dao.UserInfoDao;
import cn.zerotower.shiro.model.UserInfo;
import cn.zerotower.shiro.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 9:50
 * @Description
 * @Package cn.zerotower.shiro01.shiro
 * @PROJECT shiro-01
 **/
@Slf4j
public class MyRealm extends AuthorizingRealm {

//    @Override
//    protected String

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private SysRoleDao sysRoleDao;

    @Autowired
    @Lazy
    protected UserInfoService userInfoService;

//    //重写这个构造函数
//    public MyRealm(@Autowired HashedCredentialsMatcher matcher) {
//        super.setCredentialsMatcher(matcher);
//    }



    /**
     * 配置返回的realm名称
     * @return
     */
    @Override
    public String getName(){
        return "MyRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }



    /**
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
     * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     *
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     *
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
     *
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
     *
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
     * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();

        //获取用户的角色
        authorizationInfo.addRoles(userInfoService.getAllRolesByUserId(user.getUid()));
        authorizationInfo.addStringPermissions(userInfoService.getAllPermsByUsername(user.getUid()));
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
        log.info("查询数据库从这里开始");
        UserInfo user = this.userInfoDao.findOneByUsername(username);
        if (user == null) {
            log.info("是否能进入到这里");
            throw new UnknownAccountException("未知账号");  //底层抛出  UnknownAccountException
        }
        if ("1".equals(user.getState())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }

        String temp=user.getUsername()+"zero";
        ByteSource  salt=ByteSource.Util.bytes(temp);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user,//安全数据
                user.getPassword(), //密码
                salt, //盐
                getName() //realm 名 可能有多个realm
        );
        return info;
    }
}
