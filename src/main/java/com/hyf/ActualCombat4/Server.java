package com.hyf.ActualCombat4;

import com.hyf.ActualCombat4.handler.LoginRequestHandler;
import com.hyf.ActualCombat4.handler.MessageRequestHandler;
import com.hyf.ActualCombat4.handler.PacketDecoder;
import com.hyf.ActualCombat4.handler.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

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
                // 此处的标识是为了判断是哪方断开了通道
                .childAttr(AttributeKey.newInstance("name"),"服务端")
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 在PacketDecoder里头重写exceptionCaught方法，就可以捕捉到客户端断开报的异常，不会导致服务端因此断开
                        ch.pipeline().addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
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
