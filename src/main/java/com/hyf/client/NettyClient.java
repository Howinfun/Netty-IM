package com.hyf.client;

import com.hyf.client.handler.LoginResponseHandler;
import com.hyf.client.handler.MessageResponseHandler;
import com.hyf.codec.PacketDecoder;
import com.hyf.codec.PacketEncoder;
import com.hyf.protocol.request.LoginRequestPacket;
import com.hyf.protocol.request.MessageRequestPacket;
import com.hyf.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author howinfun
 * @version 1.0
 * @desc
 * @date 2018/12/3
 * @company XMJBQ
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final int PORT = 8000;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new FirstClientHandler());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        //ChannelFuture future = bootstrap.connect(HOST,PORT);
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }

    // 将连接方法抽取出来，可以做到重连次数的限制
    private static void connect(Bootstrap bootstrap,String host,int port,final int retry){
        bootstrap.connect(host,port).addListener(future -> {
            if(future.isSuccess()){
                System.out.println("成功连接到服务器，启动控制台线程.....");
                Channel channel = ((ChannelFuture) future).channel();
                // 等待服务器返回结果
                startConsoleThread(channel);
            }else if(retry == 0){
                System.err.println("重试次数已用完，放弃连接");
            }else{
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);;
            }

        } );
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                // 判断此用到有没有登录过用户，如果没有则进行登录
                if (!SessionUtil.hasLogin(channel)) {
                    System.out.println("请输入用户名:");
                    Scanner sc = new Scanner(System.in);
                    String username = sc.nextLine();
                    LoginRequestPacket packet = new LoginRequestPacket();
                    packet.setUserName(username);
                    packet.setPassword("123456");
                    channel.writeAndFlush(packet);
                    waitForLoginResponse();
                // 登录后可以进行对其他用户发送消息，格式：userId+空格+message
                }else{
                    System.out.println("输入消息发送至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    // 要发送的用户的id
                    String toUserId = sc.next();
                    // 发送的消息
                    String message = sc.next();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId,message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
