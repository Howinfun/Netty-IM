package com.hyf.ActualCombat9;

import com.hyf.ActualCombat9.handler.IMIdleStateHandler;
import com.hyf.ActualCombat9.handler.PacketCodecHandler;
import com.hyf.ActualCombat9.handler.Spliter;
import com.hyf.ActualCombat9.handler.server.AuthHandler;
import com.hyf.ActualCombat9.handler.server.HeartBeatRequestHandler;
import com.hyf.ActualCombat9.handler.server.IMServerHandler;
import com.hyf.ActualCombat9.handler.server.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

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
                         * 1、增加基于长度域拆包器LengthFieldBasedFrameDecoder（适合带有数据长度的自定义协议），防止出现粘包半包
                         *    我们的自定义协议： 魔数(4) 版本号(1) 序列化算法(1) 指令(1) 数据长度(4) 数据(n)
                         *    可以看到，数据长度在4+1+1+1个字节后，然后占4个字节
                         *
                         *    拒绝非本协议连接,之前我们用到的魔数，就是为了能更早的拒绝非本协议的链接，防止浪费过多的链接资源
                         *    因此我们可以继承LengthFieldBasedFrameDecoder，重新decode方法，来判断当前接收的数据是否是本协议的
                         * 2、在PacketDecoder里头重写exceptionCaught方法，就可以捕捉到客户端断开报的异常，不会导致服务端因此断开
                         *
                         * 3、AuthHandler必须放在LoginRequestHandler后面，即先进行登录逻辑再进行验证逻辑
                         *  注意：因为LoginRequestHandler中是直接调用writeAndFlush响应客户端，所以当客户端下次发送消息过来才会进行身份校验
                         *  如果登录失败，此时channel才会被断开，活跃连接数才会被减少1（这个待改进）。
                         *
                         * 4、除了Spliter之外的handler，都可以做成单例，共享的，因为他们对于Channel是无状态的，如果每个通道都new一个出来，非常的浪费资源，消耗内存
                         * 5、IdleStateHandler放在最前面，因为他只会更新lastReadTime和判断是否超时，然后会调用ctx.fileChannelRead给下一个InBoundHandler的channelRead处理读逻辑
                         */
                        ch.pipeline()//.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4))
                                .addLast(new IMIdleStateHandler())
                                .addLast(new Spliter())
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(LoginRequestHandler.INSTANCE)
                                // 因为心跳检测不需要AuthHandler来认证，所以放在AuthHandler前面
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(AuthHandler.INSTANCE)
                                .addLast(IMServerHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap,1000);
    }

    private static void bind(ServerBootstrap serverBootstrap,int port){
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("服务端启动成功,端口号为"+port);
                // 启动定时任务打印活跃连接数
                serverBootstrap.config().group().scheduleAtFixedRate(()->{
                    System.out.println("当前活跃连接数："+ LoginRequestHandler.count);
                },0,1, TimeUnit.SECONDS);
            }else{
                bind(serverBootstrap,port+1);
            }
        });
    }
}
