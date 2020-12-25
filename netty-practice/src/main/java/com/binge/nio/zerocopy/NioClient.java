package com.binge.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月25日
 * @description:
 **/
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName="d:/output.docx";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long start = System.currentTimeMillis();

        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println(" 发 送 的 总 的 字 节 数 ="+transferCount+" \n耗时: "+(System.currentTimeMillis()-start));
        fileChannel.close();
    }
}
