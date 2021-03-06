package com.hyf.ActualCombat9.attribute;

import com.hyf.ActualCombat9.entity.Session;
import io.netty.util.AttributeKey;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
