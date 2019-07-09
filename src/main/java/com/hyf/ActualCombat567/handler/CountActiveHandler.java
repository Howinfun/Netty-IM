package com.hyf.ActualCombat567.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Howinfun
 * @desc
 * @date 2019/7/3
 */
public class CountActiveHandler extends ChannelInboundHandlerAdapter {
    /** 使用静态成员变量来统计，因为静态成员变量只属于类，所有类的实例都持有同一个静态成员变量
     *  注意：此静态成员变量的修饰词为public，因为要在服务端启动后，打印出来 */
    public static int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.count += 1;
        // 定时任务，每隔一秒钟打印一遍活跃连接数
        /*ctx.executor().scheduleAtFixedRate(()->{
            System.out.println("当前活跃连接数："+this.count);
        },1,1, TimeUnit.SECONDS);*/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.count -= 1;
        /*System.out.println("活跃连接数："+this.count);*/
    }
}
