package demo1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <ul>
 * <li>文件名称：ThreadDemo2 </li>
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
public class ThreadDemo2 {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Date date1 = null;
    private static Date date2 = null;
    private static Date date3 = null;

    static {
        try {
            date1 = sdf.parse("2020-12-12 12:12:12");
            date2 = sdf.parse("2019-11-11 11:11:11");
            date3 = sdf.parse("2018-10-10 10:10:10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            String parse = sdf.format(date1);
            System.out.println("当前日期：" + parse);
        });

        Thread t2 = new Thread(() -> {
            String parse = sdf.format(date2);
            System.out.println("当前日期：" + parse);
        });


        Thread t3 = new Thread(() -> {
            String parse = sdf.format(date3);
            System.out.println("当前日期：" + parse);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
