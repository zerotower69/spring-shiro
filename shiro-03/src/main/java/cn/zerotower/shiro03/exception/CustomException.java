package cn.zerotower.shiro03.exception;

/**
 * 自定义异常(CustomException)
 * @author dolyw.com
 * @date 2018/8/30 13:59
 */
public class CustomException extends RuntimeException {

    /**
     * 消息
     * @param msg  消息
     */
    public CustomException(String msg){
        super(msg);
    }

    public CustomException() {
        super();
    }
}
