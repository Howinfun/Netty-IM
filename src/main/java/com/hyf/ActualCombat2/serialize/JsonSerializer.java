package com.hyf.ActualCombat2.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public class JsonSerializer implements Serializer {
    @Override
    public Byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON_SERIALIZE;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
