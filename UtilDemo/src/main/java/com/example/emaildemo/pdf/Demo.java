package com.example.emaildemo.pdf;

import com.alibaba.fastjson.JSON;
import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <ul>
 * <li>文件名称：Demo </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/16 </li>
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
public class Demo {

    public static void main(String[] args) {
        String url = "http://v3rbrp.natappfree.cc/pdf/show?clientId=202004101114&t=1587022720429&appKey=null&token=19ab75350238b103e08b108a873e6cac&fileName=ac93f0c4-9350-4252-9ea7-1ce90486c9ff&logId=56def4fe23854d8192f39f9bcd1bbcbd";
        URL resourceUrl = null;
        HttpURLConnection connection = null;
        DataInputStream reader = null;
        ByteArrayOutputStream outputStream = null;
        try {
            //在外网用域名进行测试会多出端口参数去除该端口
            url = url.replaceAll(":8017","");
            System.out.println(url);
//            Thread.sleep(1000*2);
            resourceUrl = new URL(url);
            connection = (HttpURLConnection) resourceUrl.openConnection();
            reader = new DataInputStream(connection.getInputStream());

            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len=reader.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }
            FileOutputStream fos = new FileOutputStream(
                    new File("d:\\缴费证明\\到账单——" + System.currentTimeMillis() + ".pdf"));
            fos.write(bytes);
            fos.close();
            /*String base64PDF = "";
            System.out.println(new String(bytes));
            if (bytes.length <= 2048 && new String(bytes).length() < 500){
                base64PDF = new String(bytes);
            }else {
                base64PDF = Base64.
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            outputStream.close();
//            reader.close();
        }

    }

}
