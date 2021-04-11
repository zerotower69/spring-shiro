package cn.zerotower.shiro.common;

/**
 * 返回码常量
 *
 * @author xiaojie
 */
public class StatusConst {

    /**
     * 成功
     */
    public static final int OK = 20000;

    /**
     * 登录失败
     */
    public static final int LOGIN_FAILED=40023;

    /**
     * 失败
     */
    public static final int ERROR = 20001;

    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = 50000;

    /**
     * 未登录
     */
    public static final int NOT_LOGIN = 40001;

    /**
     * 没有操作权限
     */
    public static final int AUTHORIZED = 40003;

    /**
     * 请求方法错误
     */
    public static final int METHOD_NOT_SUPPORT=40004;

    /**
     * 密码错误
     */
    public static final int WRONG_PASSWORD=40005;

    /**
     * 用户不存在
     */
    public static final int ACCOUNT_NOT_EXIST=40006;
}
