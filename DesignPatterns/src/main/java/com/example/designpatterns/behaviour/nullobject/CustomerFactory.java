package com.example.designpatterns.behaviour.nullobject;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * <ul>
 * <li>文件名称：CustomerFactory </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/3/31 </li>
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
public class CustomerFactory {

    private static final String[] names = {"Rob", "Joe", "Julie"};

    public static AbstractCustomer getCustomer(String name) {
        if (!name.isEmpty()) {
            for (int i = 0; i < names.length; i++) {
                if (name == names[i] || name.equalsIgnoreCase(names[i])){
                    return new RealCustomer(name);
                }
            }
        }
        return new NullCustomer();
    }
}
