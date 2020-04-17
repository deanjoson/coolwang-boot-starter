package cn.coolwang.util;

import cn.coolwang.enums.StringType;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 对象扩展工具
 *
 * @author 邓军
 * @version 1.0
 * @date 2020年04月15日19:08:35
 */
public class BeanEx {

    /**
     * 对象转为map对象
     *
     * @param source 来源对象
     * @return
     */
    public static Map<String, Object> toMap(Object source) {
        return toMap(source, false);
    }

    /**
     * 对象转为map对象
     *
     * @param source 来源对象
     * @return
     */
    public static Map<String, Object> toMap(Object source, boolean ignoreNullVal) {
        return toMap(source,StringType.NORMAL,ignoreNullVal);
    }


    /**
     * 对象转为map对象
     *
     * @param source 来源对象
     * @return
     */
    public static Map<String, Object> toMap(Object source, StringType type, boolean ignoreNullVal) {
        Assert.notNull(source,"source object cannot be null");
        Field[] fields = source.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(source);
                if (ignoreNullVal && Objects.isNull(val)) {
                    continue;
                }
                switch (type) {
                    case UPPER_CASE:
                        map.put(field.getName().toUpperCase(), val);
                        break;
                    case LOWER_CASE:
                        map.put(field.getName().toLowerCase(), val);
                        break;
                    default:
                        map.put(field.getName(), val);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 转换为目标对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(Object source,Class<T> clazz){
        Assert.notNull(source,"source object cannot be null");
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
