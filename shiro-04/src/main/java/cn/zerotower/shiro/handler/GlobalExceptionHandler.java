package cn.zerotower.shiro.handler;

import cn.zerotower.shiro.common.Result;
import cn.zerotower.shiro.common.StatusConst;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.MethodNotAllowedException;

import java.sql.ResultSet;

/**
 * 全局异常捕获处理类
 * @Author ZeroTower
 * @Date 2021/4/10 9:10
 * @Description
 * @Package cn.zerotower.shiro.handler
 * @PROJECT shiro-02
 **/
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result  HttpMediaTypeNotSupported(){

        return new Result(false, StatusConst.ERROR,"输入的类型不支持");
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result methodNotAllow(){
        return new Result(true,StatusConst.METHOD_NOT_SUPPORT,"请求方法不受支持");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public Result unAuthentication(){

        return new Result(true,StatusConst.LOGIN_FAILED,"未登录，请先登录");
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Result unAuthorizedException(){
        return new Result(true,StatusConst.AUTHORIZED,"权限不足");
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public Result wrongPassword(){

      return new Result(true,StatusConst.WRONG_PASSWORD,"密码错误");
    }
    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    public Result unknownAccount(){
          return new Result(true,StatusConst.ACCOUNT_NOT_EXIST,"账号不存在");
    }



//    @ExceptionHandler(value = Exception.class)
//    public Result exceptionHandler(HttpServletRequest request,Exception e){
//        List<String> exceptions=new ArrayList<>();
//       if(!(e instanceof HttpMediaTypeNotSupportedException) && !(e instanceof UnauthenticatedException)){
//
//       }
//    }

}
