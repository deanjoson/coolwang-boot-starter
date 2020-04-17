package cn.coolwang.code;

/**
 * @Description 错误消息接口
 * @Author 邓军
 * @Date 2019年11月22日17:36:15
 * @Version 1.0
 */
public interface IErrorCode {

    /**
     * 获取错误码
     * @return
     */
    String getCode();

    /**
     * 获取错误消息
     * @return
     */
    String getMessage();
}
