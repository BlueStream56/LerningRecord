package com.example.designpatterns.j2ee.servicelocator;

import com.sun.org.apache.xml.internal.security.Init;

import java.io.Serializable;

/**
 * <ul>
 * <li>文件名称：ServiceLocator </li>
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
public class ServiceLocator {

    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Service getService(String jndiName){
        Service service = cache.getService(jndiName);
        if (service != null){
            return service;
        }

        InitialContext initialContext = new InitialContext();
        Service service1 = (Service) initialContext.lookup(jndiName);
        cache.addService(service1);
        return service1;
    }

}
