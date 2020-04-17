package cn.coolwang.model;

import cn.coolwang.code.CommonErrorCode;
import cn.coolwang.code.IErrorCode;
import cn.coolwang.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 响应参数
 * @author 邓军
 * @date 2020年04月15日19:03:01
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
public class Response<T> extends BaseModel {

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 错误详细信息(不为空时显示)
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object errorDetail;

    /**
     * 响应时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 响应数据
     */
    private T data;

    /**
     * 构造方法
     */
    private Response(IErrorCode message){
        this.code = message.getCode();
        this.message = message.getMessage();
    }

    /**
     * 成功默认响应
     */
    public static Response success(){
        return new Response(CommonErrorCode.SUCCESS);
    }

    /**
     * 成功默认响应并返回数据
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>(CommonErrorCode.SUCCESS);
        response.data = data;
        return response;
    }

    /**
     * 错误响应
     * @return
     */
    public static Response error(IErrorCode errorCode){
        Response response =  new Response(errorCode);
        return response;
    }
    /**
     * 错误响应
     * @return
     */
    public static Response error(ServiceException exception){
        Response response =  new Response();
        response.setCode(exception.getCode());
        response.setMessage(exception.getMessage());
        return response;
    }

    /**
     * 错误响应
     * @param message
     * @param errorDetail
     * @return
     */
    public static Response error(IErrorCode errorCode, String message, Object errorDetail){
        Response response =  new Response(errorCode);
        if (!StringUtils.isEmpty(message)){
            response.setMessage(message);
        }
        response.setErrorDetail(errorDetail);
        return response;
    }

    /**
     * 错误响应
     * @param errorCode
     * @param errorDetail
     * @return
     */
    public static Response error(IErrorCode errorCode,Object errorDetail){
        Response response =  new Response(errorCode);
        response.setErrorDetail(errorDetail);
        return response;
    }

}
