package com.hyf.ActualCombat9;

import com.hyf.ActualCombat9.handler.AuthHandler;
import com.hyf.ActualCombat9.handler.CreateGroupRequestHandler;
import com.hyf.ActualCombat9.handler.GroupMessageRequestHandler;
import com.hyf.ActualCombat9.handler.JoinGroupRequestHandler;
import com.hyf.ActualCombat9.handler.ListGroupMembersRequestHandler;
import com.hyf.ActualCombat9.handler.LoginRequestHandler;
import com.hyf.ActualCombat9.handler.LogoutRequestHandler;
import com.hyf.ActualCombat9.handler.MessageRequestHandler;
import com.hyf.ActualCombat9.handler.PacketDecoder;
import com.hyf.ActualCombat9.handler.PacketEncoder;
import com.hyf.ActualCombat9.handler.QuitGroupRequestHandler;
import com.hyf.ActualCombat9.handler.Spliter;
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
                         */
                        ch.pipeline()//.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4))
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new CreateGroupRequestHandler())
                                .addLast(new JoinGroupRequestHandler())
                                .addLast(new QuitGroupRequestHandler())
                                .addLast(new ListGroupMembersRequestHandler())
                                .addLast(new GroupMessageRequestHandler())
                                .addLast(new LogoutRequestHandler())
                                .addLast(new PacketEncoder());
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
