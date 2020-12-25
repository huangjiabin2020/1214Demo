package com.binge.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月24日
 * @description:
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)when a connection is established and ready to generate traffic
        final ByteBuf time = ctx.alloc().buffer(4); // (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
        /*
        ChannelFuture表示一个尚未发生的I/O操作。这意味着，任何请求的操作可能还没有被执行，因为Netty中的所有操作都是异步的。
        因此，您需要在ChannelFuture完成之后调用close()方法
         */
        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        /*
        当写请求完成时，我们如何得到通知呢?这就像将一个ChannelFutureListener添加到返回的ChannelFuture一样简单。
        这里，我们创建了一个新的匿名ChannelFutureListener，它在操作完成时关闭通道。
         */
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        }); // (4)
        //(4)相当于：
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
