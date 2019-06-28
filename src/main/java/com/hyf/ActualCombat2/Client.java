package com.hyf.ActualCombat2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author Howinfun
 * @desc
 * @date 2019/6/27
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
}
