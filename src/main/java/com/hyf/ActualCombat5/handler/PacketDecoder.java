package com.hyf.ActualCombat5.handler;

import com.hyf.ActualCombat5.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.io.IOException;
import java.util.List;

/**
 * @author Howinfun
 * @desc 解码
 * @date 2019/7/1
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodeC.INSTANCE.deCode(byteBuf));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException){
            Attribute<String> nameKey = ctx.channel().attr(AttributeKey.valueOf("name"));
            System.out.println(nameKey.get());
            if ("服务端".equals(nameKey.get())){
                // 客户端断开了，服务端继续为其他客户端提供服务
                System.out.println("客户端断开连接");
            }else {
                // 如果是服务端断开了，客户端也关闭
                System.out.println("服务端断开连接");
                ctx.channel().close();
            }
        }
    }
}
