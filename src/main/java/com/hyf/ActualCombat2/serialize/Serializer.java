package com.hyf.ActualCombat2.serialize;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public interface Serializer {

    /** 默认是Json序列算法 */
    Serializer DEFAULT = new JsonSerializer();

    /**
     * 获取序列化算法
     * @return
     */
    Byte getSerializerAlogrithm();

    /**
     * 序列化
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deSerialize(Class<T> clazz,byte[] bytes);
}
