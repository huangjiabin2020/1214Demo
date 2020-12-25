package com.binge.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月23日
 * @description: Handles a server-side channel.
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    /**
     * 每当从客户端接收到新数据时，就使用接收到的消息来调用此方法。在此示例中，接收到的消息的类型为ByteBuf。
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*
        单独打印客户端发来的消息
       ByteBuf in = (ByteBuf) msg;
        try {
//            while (in.isReadable()) { // (1)
//                System.out.print((char) in.readByte());
//                System.out.flush();
//            }
            //等效于上面的循环
            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }*/
//        ctx.write(msg); // (1)
//        ctx.flush(); // (2)
        //二合一
        ByteBuf in = (ByteBuf) msg;
        System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
        Channel channel = ctx.channel();
        channel.write(msg);
        //ctx.writeAndFlush(msg);



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
