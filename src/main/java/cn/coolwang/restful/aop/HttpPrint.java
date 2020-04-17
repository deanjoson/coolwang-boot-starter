package cn.coolwang.restful.aop;

import cn.coolwang.model.BaseModel;
import cn.coolwang.restful.config.RestfulProperties;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 打印Http日志
 *
 * @author 邓军
 * @version 1.0
 * @date 2020年04月15日16:58:31
 */
@Slf4j
@Aspect
public class HttpPrint {

    /**
     * 记录请求的开始时间，放到request请求做为key
     */
    private static final String REQUEST_BEGIN_TIME_KEY = HttpPrint.class.getName() + "#request_begin_time";

    @Resource
    private RestfulProperties restfulProperties;

    /**
     * 拦截所有控制器的方法
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) || " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        if (!restfulProperties.isPrintRequest()) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute(REQUEST_BEGIN_TIME_KEY, System.currentTimeMillis());

        StringBuilder requestLog = new StringBuilder();
        requestLog.append("【请求参数】");

        requestLog.append("【").append(request.getMethod()).append(":").append(request.getRequestURI()).append("】");
        //类的名称
        requestLog.append(joinPoint.getTarget().getClass().getSimpleName()).append("#");
        //方法的名称
        Signature signature = joinPoint.getSignature();
        requestLog.append(signature.getName()).append("(");
        //方法参数
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (BaseModel.class.isAssignableFrom(arg.getClass())){
                    if (i != 0) {
                        requestLog.append(", ");
                    }
                    requestLog.append(JSON.toJSONString(arg));
                }

            }
        }
        requestLog.append(")");
        log.info(requestLog.toString());

    }

    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        if (!restfulProperties.isPrintResponse()) {
            return;
        }
        //判断是否记录响应数据日志
        Signature signature = joinPoint.getSignature();
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("【响应数据】");
        //类的名称
        responseLog.append(joinPoint.getTarget().getClass().getSimpleName()).append("#");
        //方法的名称
        responseLog.append(signature.getName()).append("(");
        responseLog.append(result == null ? "" : result.toString());
        responseLog.append(")");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            Object requestBeginTime = request.getAttribute(REQUEST_BEGIN_TIME_KEY);
            long dealTime = System.currentTimeMillis() - Long.parseLong(requestBeginTime == null ? "0" : requestBeginTime.toString());
            responseLog.append(" 请求耗时【").append(dealTime).append("】毫秒");
        }
        log.info(responseLog.toString());
    }

}
