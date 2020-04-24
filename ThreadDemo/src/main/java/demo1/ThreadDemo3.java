package demo1;

import java.util.Random;

/**
 * <ul>
 * <li>文件名称：ThreadDemo3 </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/1 </li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author caoxx
 * @version 1.0.0
 */
public class ThreadDemo3 {

    private static Random random = new Random();

    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> random.nextInt(10)+1);

    private static Integer get(){
        return threadLocal.get();
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(()->{
            Integer num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" "+ num);
            num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" 第二次获取 "+ num);
        });
        Thread thread2 = new Thread(()->{
            Integer num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" "+ num);
            num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" 第二次获取 "+ num);
        });
        Thread thread3 = new Thread(()->{
            Integer num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" "+ num);
            num = get();
            System.out.println("线程名："+Thread.currentThread().getName()+" 第二次获取 "+ num);
        });
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadLocal.remove();
        }
        System.out.println("执行完毕");
    }

}
