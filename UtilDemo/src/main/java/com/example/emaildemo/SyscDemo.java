package com.example.emaildemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class SyscDemo {

    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void execut(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("=================开始调用异步方法"+this.getName()+"=================");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("=================调用异步方法完成"+this.getName()+"=================");
            }
        }.start();
    }

    public static String test(){
        String msg = "0|成功";
        execut();
        return msg;
    }

    public static void main(String[] args) {
        System.out.println("==================开始处理业务===============");
        String msg = test();
        System.out.println("==================不管处理结果如何都往下执行===============");
        System.out.println(msg);
        System.out.println("==================处理业务完成===============");
    }

}
