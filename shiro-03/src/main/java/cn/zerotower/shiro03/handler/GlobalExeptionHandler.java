package cn.zerotower.shiro03.handler;

import cn.zerotower.shiro03.common.Result;
import cn.zerotower.shiro03.common.StatusConst;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.WebParam;

/**
 * 全局异常捕获处理类（针对控制层）
 * @Author ZeroTower
 * @Date 2021/4/10 9:10
 * @Description
 * @Package cn.zerotower.shiro03.handler
 * @PROJECT shiro-02
 **/
@ControllerAdvice
public class GlobalExeptionHandler {

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result  HttpMediaTypeNotSupported(){
        return new Result(false, StatusConst.ERROR,"输入的类型不支持");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public String unAuthentication(){
        return "unauthorized";
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public String  wrongPassword(Model model){

        model.addAttribute("msg","密码错误");
        return "login";
    }
    @ExceptionHandler(UnknownAccountException.class)
    public String unknownAccount(Model model){
        model.addAttribute("msg","账号不存在");
        return "login";
    }



//    @ExceptionHandler(value = Exception.class)
//    public Result exceptionHandler(HttpServletRequest request,Exception e){
//        List<String> exceptions=new ArrayList<>();
//       if(!(e instanceof HttpMediaTypeNotSupportedException) && !(e instanceof UnauthenticatedException)){
//
//       }
//    }

}
