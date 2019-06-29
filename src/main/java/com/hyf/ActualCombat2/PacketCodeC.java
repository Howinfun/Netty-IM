package com.hyf.ActualCombat2;

import com.hyf.ActualCombat2.command.Command;
import com.hyf.ActualCombat2.packet.LoginRequestPacket;
import com.hyf.ActualCombat2.packet.LoginResponsePacket;
import com.hyf.ActualCombat2.packet.Packet;
import com.hyf.ActualCombat2.serialize.JsonSerializer;
import com.hyf.ActualCombat2.serialize.Serializer;
import com.hyf.ActualCombat2.serialize.SerializerAlogrithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
 */
public class PacketCodeC {

    /** 存放Packet的Class，反序列化需要根据Class反序列化 */
    private final Map<Byte,Class<? extends Packet>> packetMap = new HashMap<>(2);

    private final Map<Byte, Serializer> serializerMap = new HashMap<>(1);

    private PacketCodeC(){
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap.put(SerializerAlogrithm.JSON_SERIALIZE,new JsonSerializer());
    }

    /** 单例 */
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    public ByteBuf encode(ChannelHandlerContext ctx,Packet packet){
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        // 魔数
        byteBuf.writeInt(Packet.MAGIC_NUMBER);
        // 版本号
        byteBuf.writeByte(Packet.VERSION);
        // 序列化算法
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        // 指令
        byteBuf.writeByte(packet.getCommand());
        //数据&数据长度
        byte[] data = Serializer.DEFAULT.serialize(packet);
        int dataLength = data.length;
        byteBuf.writeInt(dataLength);
        byteBuf.writeBytes(data);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法
        byte serilizerAlogrithm = byteBuf.readByte();
        Serializer serializer = getSerializer(serilizerAlogrithm);
        // 指令
        Byte command = byteBuf.readByte();
        // 数据长度
        int dataLength = byteBuf.readInt();
        // 数据
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        Class<? extends Packet> packetType = getPacketType(command);
        if (packetType != null && serializer != null){
            return serializer.deSerialize(packetType,data);
        }
        return null;
    }

    private  Serializer getSerializer(byte serilizerAlogrithm){
        return serializerMap.get(SerializerAlogrithm.JSON_SERIALIZE);
    }
    private Class<? extends Packet> getPacketType(byte command){
        return packetMap.get(command);
    }
}
