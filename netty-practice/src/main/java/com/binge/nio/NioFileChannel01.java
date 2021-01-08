package com.binge.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月24日
 * @description:
 **/
public class NioFileChannel01 {
    /**
     * 使用NIO进行写入
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        writeToFile();
//        writeToPrint();
//        fileCopy01();
        //transferFrom，从通道到通道
//        fileCopy02();

//        opHeapOutsideMem();

        //使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到 socket ，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建 buffer 数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        //假定从客户端接收 8 个字节
        int messageLength = 8;
        while (true){
            int byteRead = 0;
            while (byteRead<messageLength){
                //采用 buffer 数组，依次读
                long read = socketChannel.read(byteBuffers);
                //累计读取的字节数
                byteRead+=read;
                System.out.println("byteRead:"+byteRead);
                //使用流打印, 看看当前的这个 buffer 的 position 和 limit
                Arrays.asList(byteBuffers).stream().map(buffer->
                        "position:"+buffer.position()+"\tlimit:"+buffer.limit())
                        .forEach(System.out::println);
            }
            //将所有的 buffer 进行 flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            //将数据读出显示到客户端
            long byteWirte = 0;
            while (byteWirte<messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWirte+=write;
            }
            //将所有的 buffer 进行 clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead:=" + byteRead + " byteWrite=" + byteWirte + ", messagelength" +
                    messageLength);
        }
    }

    private static void opHeapOutsideMem() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/file01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        /*** 参数 1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * * 参数 2： 0 ： 可以直接修改的起始位置
         * * 参数 3: 5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * * 可以直接修改的范围就是 0-5* 实际类型 DirectByteBuffer*/
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
//        mappedByteBuffer.put(5, (byte) 'Y');//IndexOutOfBoundsException，只有5个字节映射到内存，不能操作第6个字节

        randomAccessFile.close();
        System.out.println("修改成功");
    }

    private static void fileCopy02() throws IOException {
        FileInputStream fis = new FileInputStream("d:/1.jpg");
        FileOutputStream fos = new FileOutputStream("d:/copyOf1.jpg");
        FileChannel channel1 = fis.getChannel();
        FileChannel channel2 = fos.getChannel();

        //使用transferFrom进行拷贝,直接将文件缓冲区的数据发送到目标Channel，避免了传统需要先暂存再多拷贝一次的问题。
        channel2.transferFrom(channel1, 0, channel1.size());
        fis.close();
        fos.close();
        channel1.close();
        channel2.close();
    }

    private static void fileCopy01() throws IOException {
        FileInputStream fis = new FileInputStream(new File("d:/file01.txt"));
        FileChannel channel1 = fis.getChannel();
        FileOutputStream fos = new FileOutputStream("d:/copyOfFile01.txt");
        FileChannel channel2 = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        //循环读取，循环写入
        while (true) {
            buffer.clear();
            int read = channel1.read(buffer);
            System.out.println("read:" + read);
            if (read == -1) {
                //读完了
                break;
            }
            //将break的数据写入复制后的文件
            buffer.flip();
            channel2.write(buffer);
        }
        fis.close();
        fos.close();
    }

    private static void writeToPrint() throws IOException {
        File file = new File("d:/file01.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        //将通道的数据读入Buffer
        channel.read(buffer);
        //将Buffer的字节数据转成string
        System.out.println(new String(buffer.array()));
        //别忘了关闭文件输入流
        fis.close();
    }

    private static void writeToFile() throws IOException {
        String h = "hello中国";
        //输出流--->channel
        FileOutputStream fos = new FileOutputStream("D:/file01.txt");
        //nio对输出流进行包装，实际channel是流的一个成员变量
        FileChannel fileChannel = fos.getChannel();
        //创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将数据写入buffer
        buffer.put(h.getBytes());
        //对buffer进行反转，从buffer角度看，从读h变成写h
        buffer.flip();
        //将数据写入channel
        fileChannel.write(buffer);
        //关闭流
        fos.close();
    }
}
