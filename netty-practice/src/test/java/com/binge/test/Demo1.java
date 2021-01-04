package com.binge.test;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月24日
 * @description:
 **/
public class Demo1 {

    @Test
    public void test1() {
        ByteBuffer buffer = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            buffer.put(new Byte((i + "")));
        }
        //读写切换
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
