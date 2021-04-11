package cn.zerotower.shiro01.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZeroTower
 * @TableName sys_role
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated Wed Apr 07 09:05:32 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 是否可用0可用  1不可用
     *
     * @mbg.generated Wed Apr 07 09:05:32 CST 2021
     */
    private String available;

    /**
     * 角色标识程序中判断使用,如"admin"
     *
     * @mbg.generated Wed Apr 07 09:05:32 CST 2021
     */
    private String role;

    /**
     * 角色描述,UI界面显示使用
     *
     * @mbg.generated Wed Apr 07 09:05:32 CST 2021
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_role
     *
     * @mbg.generated Wed Apr 07 09:05:32 CST 2021
     */
    private static final long serialVersionUID = 1L;
}