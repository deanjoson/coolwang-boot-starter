package cn.coolwang.restful.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Restful 配置信息
 *
 * @author 邓军
 * @version 1.0
 * @date 2020年04月15日16:37:14
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "cn.coolwang.restful")
@Configuration
@Data
public class RestfulProperties {

    /**
     * java.time.LocalDate 标准传入传出格式
     */
    private String localDateFormat = "yyyy-MM-dd";

    /**
     * java.time.LocalDateTime 标准传入传出格式
     */
    private String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * java.time.LocalTime 标准传入传出格式
     */
    private String localTimeFormat = "HH:mm:ss";

    /**
     * 打印Http请求响应参数
     */
    private boolean printRequest = true;

    /**
     * 日志打印Http响应参数
     */
    private boolean printResponse = true;

}
