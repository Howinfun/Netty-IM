package com.hyf.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
@Data
public abstract class Packet {

    // 协议版本
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    // 指令
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
