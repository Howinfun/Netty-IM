package com.hyf.ActualCombat567;

import com.hyf.ActualCombat567.handler.LoginResponseHandler;
import com.hyf.ActualCombat567.handler.MessageResponseHandler;
import com.hyf.ActualCombat567.handler.PacketDecoder;
import com.hyf.ActualCombat567.handler.PacketEncoder;
import com.hyf.ActualCombat567.handler.Spliter;
import com.hyf.ActualCombat567.packet.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class Client2 {
    private static final Integer MAX_RETRY = 5;
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                // 此处的标识是为了判断是哪方断开了通道
                .attr(AttributeKey.newInstance("name"),"客户端")
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()//.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4))
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new PacketEncoder());
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
                // 身份校验逻辑放在服务端，客户端不在进行登录判断
                /*if (SessionUtil.isLogin(channel)){*/
                System.out.println("请输入消息：");
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                MessageRequestPacket requestPacket = new MessageRequestPacket();
                requestPacket.setMessage(msg);
                channel.writeAndFlush(requestPacket);
                /*}*/
            }
        }).start();
    }
}
