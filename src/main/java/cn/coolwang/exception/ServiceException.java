package cn.coolwang.exception;

import cn.coolwang.code.CommonErrorCode;
import cn.coolwang.code.IErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 自定义业务服务错误
 * @Author 邓军
 * @Date 2020年04月15日18:50:34
 * @Version 1.0
 */
@Setter
@Getter
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误描述
     */
    private String message;

    /**
     * 构造器
     *
     * @param code
     * @param message
     */
    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }


    /**
     * 基于标注定义的错误码构造自定义业务异常
     *
     * @param message
     */
    public ServiceException(IErrorCode message) {
        this(message.getCode(), message.getMessage());
    }

    /**
     * 基于标注定义的错误码和自定义的错误信息构造自定义业务异常
     *
     * @param message
     */
    public ServiceException(IErrorCode message, String msg) {
        this(message.getCode(), msg);
    }

    /**
     * 指定错误描述构造自定义业务异常
     *
     * @param message
     */
    public ServiceException(String message) {
        this(CommonErrorCode.SERVER_ERROR, message);
    }
}
