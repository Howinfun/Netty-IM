package com.hyf.ActualCombat567;

import com.hyf.ActualCombat567.command.Command;
import com.hyf.ActualCombat567.packet.LoginRequestPacket;
import com.hyf.ActualCombat567.packet.LoginResponsePacket;
import com.hyf.ActualCombat567.packet.MessageRequestPacket;
import com.hyf.ActualCombat567.packet.MessageResponsePacket;
import com.hyf.ActualCombat567.packet.Packet;
import com.hyf.ActualCombat567.serialize.JsonSerializer;
import com.hyf.ActualCombat567.serialize.Serializer;
import com.hyf.ActualCombat567.serialize.SerializerAlogrithm;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class PacketCodeC {

    private final Map<Byte,Object> serializerMap = new HashMap<>(1);
    private final Map<Byte,Class<? extends Packet>> packetMap = new HashMap<>(4);

    private PacketCodeC(){
        serializerMap.put(SerializerAlogrithm.JSON,new JsonSerializer());
        packetMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

    }
    /** 静态内部类做单例 */
    private static class SingleTon{
        static PacketCodeC packetCodeC = new PacketCodeC();
    }

    public static PacketCodeC INSTANCE = SingleTon.packetCodeC;

    public ByteBuf encode(ByteBuf byteBuf, Packet packet){
       // 魔数
       byteBuf.writeInt(Packet.MAGIC_NUMBER);
        // 版本号
       byteBuf.writeByte(Packet.VERSION);
        // 序列算法
       byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        // 指令
       byteBuf.writeByte(packet.getCommand());
        // 序列化
       byte[] data = Serializer.DEFAULT.serialize(packet);
        // 数据长度
       int dataLength = data.length;
       byteBuf.writeInt(dataLength);
       byteBuf.writeBytes(data);
       return byteBuf;
    }

    public Packet deCode(ByteBuf byteBuf){
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 获取序列化算法
        Byte serializerAlogrithm = byteBuf.readByte();
        Serializer serializer = (Serializer)serializerMap.get(serializerAlogrithm);
        // 获取指令
        Byte command = byteBuf.readByte();
        // 数据长度
        int dataLength = byteBuf.readInt();
        // 读取数据内容
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        // 反序列化
        Packet packet = serializer.deSerialize(data,packetMap.get(command));
        return packet;
    }
}
