package cn.zerotower.shiro.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author ZeroTower
 * @Date 2021/4/10 16:40
 * @Description
 * @Package cn.zerotower.shiro03.vo
 * @PROJECT shiro-03
 **/
@Data
@AllArgsConstructor
@Builder
public class LoginUser {

    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
}
