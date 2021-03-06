package cn.zerotower.shiro01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class RoleDto {
    private Integer id;

    private String role;

    /**
     * ๆ้ๅ่กจ
     */
    private List<PermissionDto> permissions;
}
