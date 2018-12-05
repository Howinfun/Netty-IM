package com.hyf.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.hyf.serialize.Serializer;
import com.hyf.serialize.SerializerAlgorithm;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {

        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes,clazz);
    }
}
