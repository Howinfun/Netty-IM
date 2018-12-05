package com.hyf.serialize;

import com.hyf.serialize.impl.JSONSerializer;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public interface Serializer {

    // 序列化算法
    byte getSerializerAlgorithm();

    /**
     * java对象转为二进制
     */
    byte[] serialize(Object object);

    // 二进制转为java对象
    <T> T deserialize(Class<T> clazz,byte[] bytes);

    // 默认的序列化为JSON序列化
    Serializer DEFAULT = new JSONSerializer();

}
