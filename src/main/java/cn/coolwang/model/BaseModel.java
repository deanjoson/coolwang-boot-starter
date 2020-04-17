package cn.coolwang.model;

import cn.coolwang.enums.StringType;
import cn.coolwang.util.BeanEx;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.util.Map;

/**
 * 基础模型
 * @author 邓军
 * @date
 * @version 1.0
 */
public class BaseModel implements Serializable {
    /**
     * 将当前对象转换为Map
     * @return
     */
    public Map<String,Object> toMap(){
        return toMap(StringType.NORMAL,false);
    }

    /**
     * 将当前对象转换为Map
     * @param stringType KEY转换类型
     * @return
     */
    public Map<String,Object> toMap(StringType stringType){
        return toMap(stringType,false);
    }

    /**
     * 将当前对象转换为Map
     * @param stringType KEY转换类型
     * @param ignoreNullValue 是否忽略空值字段
     * @return
     */
    public Map<String,Object> toMap(StringType stringType, boolean ignoreNullValue){
        return BeanEx.toMap(this,stringType,ignoreNullValue);
    }

    /**
     * 将当前对象转换为另一对象
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T toBean(Class<T> tClass){
        return  BeanEx.toBean(this,tClass);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
