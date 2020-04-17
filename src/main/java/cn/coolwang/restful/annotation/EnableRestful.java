package cn.coolwang.restful.annotation;

import cn.coolwang.restful.aop.ControllerExceptionHandler;
import cn.coolwang.restful.aop.HttpPrint;
import cn.coolwang.restful.config.RestfulProperties;
import cn.coolwang.restful.config.WebMvcConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许使用Restful方式web
 *
 * @author 邓军
 * @version 1.0
 * @date 2020年04月15日15:47:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({
        ControllerExceptionHandler.class,
        RestfulProperties.class,
        WebMvcConfig.class,
        HttpPrint.class
})
public @interface EnableRestful {
}
