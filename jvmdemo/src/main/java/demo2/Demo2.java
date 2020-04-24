package demo2;

/**
 * <ul>
 * <li>文件名称：Demo2 </li>
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
public class Demo2 {

    public static void main(String[] args) {
        Demo2 demo2 = new Demo2();
        demo2 = null;
        System.gc();//手动回收垃圾，该方法只能告知GC该资源可以被回收，具体什么时候被回收无法知道
        System.out.println("最大内存");
        System.out.println(Runtime.getRuntime().maxMemory() / 1024.0 / 1024.0 + "M");
    }

    @Override
    protected void finalize() throws Throwable {
        //GC回收垃圾之前调用
        System.out.println("GC回收垃圾之前调用的方法");
    }
}
