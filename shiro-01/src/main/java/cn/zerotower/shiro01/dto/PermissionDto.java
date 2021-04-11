package cn.zerotower.shiro01.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ZeroTower
 * @Date 2021/4/7 9:28
 * @Description
 * @Package cn.zerotower.shiro01.dto
 * @PROJECT shiro-01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {

    private Integer id;


    private Integer parentId;


    private String parentIds;


    private String permission;


    private String resourceType;

    private String url;

    private String name;


    private String available;

}
