package cn.coolwang.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 公共错误消息 0 - 10000
 * @Author 邓军
 * @Date 20019年11月22日17:52:48
 * @Version 1.00
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode implements IErrorCode {

    //基础错误码
    SUCCESS("0","响应成功"),
    SERVER_ERROR("-1","抱歉，服务器开了下小差，请稍后再试！"),

    ARGUMENT_NOT_VALID("-11","请求参数验证失败！"),
    ARGUMENT_NOT_READABLE("-12","请求参数格式错误！"),

    ;

    private String code;

    private String message;
}
