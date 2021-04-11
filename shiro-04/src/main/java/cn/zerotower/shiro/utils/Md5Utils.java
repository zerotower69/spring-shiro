package cn.zerotower.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5加密处理
 * @Author ZeroTower
 * @Date 2021/4/10 14:22
 * @Description
 * @Package cn.zerotower.shiro03.utils
 * @PROJECT shiro-03
 **/
public class Md5Utils {

    //可以使用随机数，这里用于测试，使用固定盐
//    public static final String SALT = "zero";
    public static String computeMd5(String username,String password) {
        /**
         * MD5
         * 将原始密码加盐（上面生成的盐），并且用md5算法加密三次，将最后结果存入数据库中
         */
        return new SimpleHash("MD5",password,username+"zero",3).toHex();
    }
}
