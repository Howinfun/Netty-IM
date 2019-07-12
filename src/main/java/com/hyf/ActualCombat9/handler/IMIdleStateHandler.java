package com.hyf.ActualCombat9.handler;

import com.hyf.ActualCombat9.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/12
 */
public class IMIdleStateHandler extends IdleStateHandler {

    /** 读空闲时间 如果为0则不关注*/
    private static final Integer READER_IDEL_TIME = 15;
    /** 写空闲时间 如果为0则不关注*/
    private static final Integer WRITER_IDEL_TIME = 0;
    /** 读写空闲时间 如果为0则不关注*/
    private static final Integer ALL_IDLE_TIME = 0;

    public IMIdleStateHandler() {
        super(READER_IDEL_TIME, WRITER_IDEL_TIME, ALL_IDLE_TIME, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        // 到这个方法，客户端已经超时，可以直接断开连接
        System.out.println("客户端["+ SessionUtil.getSession(ctx.channel()).getUserName() +"]读超时,断开连接");
        ctx.channel().close();
    }
}
