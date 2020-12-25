package com.binge.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月25日
 * @description:
 **/
public class NioServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);
        ServerSocketChannel channel = ServerSocketChannel.open();
        ServerSocket socket = channel.socket();
        socket.bind(address);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel acceptChannel = channel.accept();
            int readCount=0;
            while (readCount!=-1){
                readCount=acceptChannel.read(buffer);
            }
            //倒带 position = 0 mark 作废
            buffer.rewind();
        }

    }
}
