package com.binge.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月25日
 * @description:
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据实际(这里我们可以读取客户端发送的消息)
     * @param ctx 上下文对象, 含有 管道 pipeline , 通道 channel, 地址
     * @param msg 就是客户端发送的数据 默认 Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx =" + ctx);

        System.out.println("看看 channel 和 pipeline 的关系");
        Channel channel = ctx.channel();
        System.out.println("0:"+Thread.currentThread().getName());
        channel.eventLoop().submit(()->{
            try {
                System.out.println("1:"+Thread.currentThread().getName());
                Thread.sleep(5*1000);
                System.err.println("耗时任务1被服务端执行完毕");
                ctx.writeAndFlush(Unpooled.copiedBuffer("这是耗时任务1，客户端你应该5秒后才收到",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        channel.eventLoop().submit(()->{
            try {
                System.out.println("2:"+Thread.currentThread().getName());
                Thread.sleep(10*1000);
                System.err.println("耗时任务2被服务端执行完毕");
                ctx.writeAndFlush(Unpooled.copiedBuffer("这是耗时任务2，客户端你应该10秒后才收到",CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        channel.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("do sth");
            }
        },10, TimeUnit.SECONDS);

        //本质是一个双向链接, 出站入站
//        ChannelPipeline pipeline = ctx.pipeline();

        //将 msg 转成一个 ByteBuf
        /*
        ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer.
         */
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + channel.remoteAddress());

    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存，并刷新(将缓冲的数据写入管道)
        //一般讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常, 一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
