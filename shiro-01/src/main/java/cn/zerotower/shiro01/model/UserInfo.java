package cn.zerotower.shiro01.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.slf4j.Logger;

/**
 * @author ZeroTower
 * @TableName user_info
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    /**
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户名
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private String username;

    /**
     * 登录密码
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private String password;

    /**
     * 用户真实姓名
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private String name;

    /**
     * 用户身份证号
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private String idCardNum;

    /**
     * 用户状态：0:正常状态,1：用户被锁定
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_info
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
    private static final long serialVersionUID = 1L;
}