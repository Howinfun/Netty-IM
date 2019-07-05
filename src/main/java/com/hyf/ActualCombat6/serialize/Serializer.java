package com.hyf.ActualCombat6.serialize;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public interface Serializer {

    /** 默认为JSON序列化算法 */
    Serializer DEFAULT = new JsonSerializer();

    /**
     * 获取序列化算法
     * @return
     */
    Byte getSerializerAlogrithm();

    /**
     * 序列化
     * @param object 被序列化实体
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     * @param data 字节数组数据
     * @param T 反序列化实体类型
     * @param <T>
     * @return
     */
    <T> T deSerialize(byte[] data, Class<T> T);
}
