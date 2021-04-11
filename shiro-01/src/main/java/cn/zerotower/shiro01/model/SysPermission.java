package cn.zerotower.shiro01.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ZeroTower
 * @TableName sys_permission
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父编号,本权限可能是该父编号权限的子权限
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private Integer parentId;

    /**
     * 父编号列表
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String parentIds;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String permission;

    /**
     * 资源类型，[menu|button]
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String resourceType;

    /**
     * 资源路径 如：/userinfo/list
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String url;

    /**
     * 权限名称
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String name;

    /**
     * 是否可用0可用  1不可用
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private String available;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_permission
     *
     * @mbg.generated Wed Apr 07 09:05:59 CST 2021
     */
    private static final long serialVersionUID = 1L;
}