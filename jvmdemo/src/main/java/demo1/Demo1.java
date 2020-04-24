package demo1;

import java.io.BufferedWriter;
import java.nio.ByteBuffer;

/**
 * <ul>
 * <li>文件名称：Demo1 </li>
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
public class Demo1 {

    public static void main(String[] args) {
        allocateCompare();//分配比较
        operateCompare();//读写比较
        //结论：
        // 在内存分配上 堆内存分配远快于直接内存分配
        // 在读写性能上 堆内存读写慢于直接内存读写
    }

    /**
     * 直接内存 和 堆内存 的 分配空间比较
     */
    private static void allocateCompare(){
        int time = 10000000; //操作次数
        long st = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);//非直接内存分配申请
        }
        long et = System.currentTimeMillis();
        System.out.println("在进行" + time + "次分配操作时，堆内存：分配耗时:" + (et - st) + "ms");

        long st_heap = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2);//直接内存分配申请
        }
        long et_direct = System.currentTimeMillis();
        System.out.println("在进行" + time + "次分配操作时，直接内存：分配耗时:" + (et_direct - st_heap) + "ms");
    }

    /**
     * 直接内存 和 堆内存 的读写性能比较
     */
    public static void operateCompare(){
        //如果报错那么修改次数，将次数修改小点即可
        int time = 10000000;//操作次数
        ByteBuffer buffer = ByteBuffer.allocate(2 * time);
        long st = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            buffer.putChar('a');
        }
        buffer.flip();
        for (int i = 0; i < time; i++) {
            buffer.getChar();
        }
        long et = System.currentTimeMillis();
        System.out.println("在进行" + time + "次读写操作时，堆内存：读写耗时：" + (et - st) + "ms");

        ByteBuffer buffer_d = ByteBuffer.allocateDirect(2 * time);
        long st_direct = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            buffer_d.putChar('a');
        }
        buffer_d.flip();
        for (int i = 0; i < time; i++) {
            buffer_d.getChar();
        }
        long et_direct = System.currentTimeMillis();
        System.out.println("在进行" + time + "次读写操作时，直接内存：读写耗时:" + (et_direct - st_direct) + "ms");
    }

}
