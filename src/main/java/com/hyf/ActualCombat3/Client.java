package com.hyf.ActualCombat3;

import com.hyf.ActualCombat3.handler.ClientHandler;
import com.hyf.ActualCombat3.packet.MessageRequestPacket;
import com.hyf.ActualCombat3.utils.LoginUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class Client {
    private static final Integer MAX_RETRY = 5;
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap,"127.0.0.1",1000,5);
    }

    /**
     *
     * @param bootstrap
     * @param hostName 服务端IP或域名
     * @param port 服务端端口号
     * @param retry 剩余尝试连接次数
     */
    private static void connect(Bootstrap bootstrap,String hostName,Integer port,int retry){
        bootstrap.connect(hostName,port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("连接成功");
                // 开启一条线程接收用户输入
                Channel channel = ((ChannelFuture) future).channel();
                startThread(channel);
            }else if(retry == 0){
                System.err.println("连接失败，重试次数已用完");
            }else{
                System.out.println("继续尝试连接，剩余次数为："+retry);
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                // 定时任务
                bootstrap.config().group().schedule(()->{connect(bootstrap,hostName,port,retry-1);},delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startThread(Channel channel){
        new Thread(()->{
            while(!Thread.interrupted()){
                if (LoginUtils.isLogin(channel)){
                    System.out.println(" 请输入消息：");
                    Scanner scanner = new Scanner(System.in);
                    String msg = scanner.nextLine();
                    MessageRequestPacket requestPacket = new MessageRequestPacket();
                    requestPacket.setMessage(msg);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(),requestPacket);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
