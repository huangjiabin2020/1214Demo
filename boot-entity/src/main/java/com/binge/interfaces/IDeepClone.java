package com.binge.interfaces;

import java.io.*;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月17日
 * @description:
 **/

public interface IDeepClone<T> {

    public default T myDeepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        //反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();


    }

//    public static void main(String[] args) {
//        User zs = new User().setName("张三").setUser(new User().setName("张三儿子"));
//        try {
//            User clone = zs.myDeepClone();
//            System.out.println(zs);
//            System.out.println(clone);
//            System.out.println("----------");
//            zs.setName("aaa");
//            zs.setUser(new User().setName("张三的新儿子"));
//            System.out.println(zs);
//            System.out.println(clone);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
}
