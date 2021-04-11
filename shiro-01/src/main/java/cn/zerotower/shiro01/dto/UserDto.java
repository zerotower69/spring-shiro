package cn.zerotower.shiro01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 9:10
 * @Description
 * @Package cn.zerotower.shiro01.dto
 * @PROJECT shiro-01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * 用户id
     *
     * @mbg.generated Wed Apr 07 09:05:02 CST 2021
     */
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

    /**
     * 角色列表
     */
    private List<RoleDto> roleList;
}
