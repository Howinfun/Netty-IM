package com.hyf.TestPipelineAndChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/1
 */
public class Server {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        /**
                         * 1、需要在不变Handler的添加顺序，打印这样：inboundA -> inboundC -> outboundB -> outboundA
                         *     解决方案：给Handler增加名字，可根据名字热插拔Handler，ctx.piepeline.remove(String var);
                         * 2、每个Handler输入上一个Handler的结束时间
                         *     解决方案：使用Channel.attr().set() 和 Channel.attr().get()
                         */
                        ch.pipeline().addLast("in_A",new InBoundHandlerA())
                                .addLast("in_B",new InBoundHandlerB())
                                .addLast("in_C",new InBoundHandlerC())
                                .addLast("out_A",new OutBoundHandlerA())
                                .addLast("out_B",new OutBoundHandlerB())
                                .addLast("out_C",new OutBoundHandlerC());
                    }
                });
        bind(serverBootstrap,1000);
    }

    private static void bind(ServerBootstrap serverBootstrap,int port){
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("服务端启动成功,端口号为"+port);
            }else{
                bind(serverBootstrap,port+1);
            }
        });
    }
}
