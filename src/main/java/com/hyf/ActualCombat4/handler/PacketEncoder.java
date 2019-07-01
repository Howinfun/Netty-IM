package com.hyf.ActualCombat4.handler;

import com.hyf.ActualCombat4.PacketCodeC;
import com.hyf.ActualCombat4.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Howinfun
 * @desc 编码
 * @date 2019/7/1
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodeC.INSTANCE.encode(byteBuf,packet);
    }
}
