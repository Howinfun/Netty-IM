package com.hyf.ActualCombat5.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class JsonSerializer implements Serializer {

    @Override
    public Byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> T) {
        return JSON.parseObject(data,T);
    }
}
