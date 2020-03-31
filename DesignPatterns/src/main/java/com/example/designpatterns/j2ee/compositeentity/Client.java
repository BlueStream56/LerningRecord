package com.example.designpatterns.j2ee.compositeentity;

import java.util.Arrays;

/**
 * <ul>
 * <li>文件名称：Client </li>
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
public class Client {

    private CompositeEntity compositeEntity = new CompositeEntity();

    public void printData(){
        if (compositeEntity != null && compositeEntity.getData() != null && compositeEntity.getData().length > 0){
            Arrays.asList(compositeEntity.getData()).forEach(s -> {
                System.out.println("Data: " + s);
            });
        }
    }

    public void setData(String data1, String data2){
        compositeEntity.setData(data1, data2);
    }

}
