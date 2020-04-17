package cn.coolwang.restful.aop;


import cn.coolwang.code.CommonErrorCode;
import cn.coolwang.exception.ServiceException;
import cn.coolwang.model.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 控制器错误转换器
 * @Author 邓军
 * @Date 2019年11月22日17:31:08
 * @Version 1.0
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Response serviceExceptionHandler(ServiceException ex) {
        //自定义异常只记录提示信息
        log.warn("捕获业务错误 code:{}, msg:{}", ex.getCode(), ex.getMessage());
        return Response.error(ex);
    }

    /**
     * 参数校验失败抛出此异常
     **/
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        String msg = "参数%s验证失败：%s";
        List<String> errorDetail = ex.getBindingResult().getFieldErrors().stream().map(e -> String.format(msg,e.getField(),e.getDefaultMessage())).collect(Collectors.toList());
        log.warn("捕获参数验证错误：{}", errorDetail);
        return Response.error(CommonErrorCode.ARGUMENT_NOT_VALID, message, errorDetail);
    }

    /**
     * 这个异常时由于前端请求的参数格式不正确，导致不能正确转换类型。
     * 比如前端传递的参数不是json格式等错误。由于是前端的错误只记录警告日志
     **/
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        String detailMessage = ex.getMessage();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException e = (InvalidFormatException) cause;
            String message = "参数（%s：%s）转换目标格式%s错误";
            String paramName = e.getPath().get(0).getFieldName();
            String paramValue = e.getValue().toString();
            String type = e.getTargetType().getName();
            detailMessage = String.format(message, paramName, paramValue, type);
        }
        if (cause instanceof JsonParseException) {
            detailMessage = "请求参数不是有效JSON格式，请检查！";
        }
        log.warn("捕获参数格式错误：{}", detailMessage);
        return Response.error(CommonErrorCode.ARGUMENT_NOT_READABLE, detailMessage);
    }

    /**
     * 系统全局异常处理
     **/
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Response exceptionHandler(RuntimeException ex) {
        log.error("exceptionHandler: {}", ex);
        return  Response.error(CommonErrorCode.SERVER_ERROR);
    }

}
