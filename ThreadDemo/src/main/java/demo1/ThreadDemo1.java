package demo1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * <ul>
 * <li>文件名称：ThreadDemo1 </li>
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
public class ThreadDemo1 {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parse(String date){
        Date parse = null;
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            Date parse = parse("2020-04-01 14:27:00");
            System.out.println("当前时间：" + parse);
        });

        Thread thread2 = new Thread(() -> {
            Date parse = parse("2020-04-01 14:27:00");
            System.out.println("当前时间：" + parse);
        });

        Thread thread3 = new Thread(() -> {
            Date parse = parse("2020-04-01 10:10:10");
            System.out.println("当前时间：" + parse);
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
        }
        System.out.println("现场执行完毕");
    }

}
