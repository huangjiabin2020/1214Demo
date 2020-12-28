package com.binge.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月25日
 * @description:
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup 完成
        //两个都是无限循环
        // bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数:默认实际 cpu 核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            //创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    //使用 NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //.handler(通道初始化对象)，将会在bossGroup生效
                   /* .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    })*/
                    //.childHandler(通道初始化对象)，将会在workGroup生效，创建一个通道初始化对象(匿名对象)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //每个客户端连接成功，都会在这里设置它的通道处理器
                            System.out.println("客户端的socket channel："+ch.hashCode());
                            System.out.println(ch.remoteAddress());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println(".....服务器 is ready...");
            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            //启动服务器(并绑定端口)
            ChannelFuture channelFuture = serverBootstrap.bind(6668).sync();

            //给channelFuture注册监听器，监听我们关系的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听端口成功");
                    }else {
                        System.out.println("监听端口失败");
                    }
                }
            });

            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
