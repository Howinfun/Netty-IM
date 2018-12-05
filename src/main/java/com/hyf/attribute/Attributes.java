package com.hyf.attribute;

import com.hyf.server.session.Session;
import io.netty.util.AttributeKey;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/4
 * @company XMJBQ
 */
public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
