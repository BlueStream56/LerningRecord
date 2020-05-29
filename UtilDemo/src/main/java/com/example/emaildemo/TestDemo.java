package com.example.emaildemo;

import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <ul>
 * <li>文件名称：TestDemo </li>
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
public class TestDemo {

    private static final String START_BODY = "SSSS|%s|*|%s|%s|%s|%s|ZZZZ";

    private static String host="192.168.10.149";// IP
    private static int post = 7020;// 端口
    private static String charset = "GBK";// 编码格式
    private static String termCode = "2.0007#3806974923E2E9D3CB";// 收费终端唯一标识符
    private static String user = "00050478";// 中心分配的网点用户名
    private static String pwd = "123456";// 中心分配的网点密码

    public String invode(String businessType, String businessBody) {
        if (businessBody.startsWith("SSSS|")) {
            businessBody = businessBody.substring(5);
        }
        if (businessBody.endsWith("|ZZZZ")) {
            businessBody = businessBody.substring(0, businessBody.length() - 5);
        }
        if (businessBody.endsWith("|")) {
            businessBody = businessBody.substring(0, businessBody.length() - 1);
        }
        return invode(String.format(START_BODY, businessType, termCode, user, pwd, businessBody));
    }

    public String invode(String requestBody) {
        try {
            String responseBody = SocketUtils.sendMsg(host, post, requestBody, charset);
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR:RRRR|-1|调用抚州医保失败|ZZZZ";
        }
    }

    public static void main(String[] args) {
        cx01();
        cx01();
//        dj01();
//        dj04();
    }

    public static void cx01(){
        TestDemo testDemo = new TestDemo();
        String cx01 = "sfz|362526197003050510|冯国平";
        String resultMsg = testDemo.invode("CX01",cx01);
        System.out.println(resultMsg);
        System.out.println(cx01);
    }

    public static void dj01(){
        TestDemo testDemo = new TestDemo();
        String akc190 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mzrq = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String mzsj = new SimpleDateFormat("HHmmss").format(new Date());
        System.out.println(akc190+"|"+mzrq+"|"+mzsj);
        String dj01 = "0100001500|36100000075554|冯国平|11|10|ksdm00|0|"+ mzrq +"|"+ mzsj +"|"+akc190+"|NULL|361001";
        String resultMsg = testDemo.invode("DJ01", dj01);
        System.out.println(resultMsg);
    }

    public static void dj04(){
        TestDemo testDemo = new TestDemo();
        String akc190 = "1047689998";
        String mzrq = "20200516";
        String mzsj = "141038";
        String dj01 = "0100001500|36100000075554|冯国平|"+akc190+"|11|NULL|NULL|ysbm|医师姓名|"+20200516+"|"+141038+"|0|0|NULL|NULL|361001;1|78416|一般诊疗费|||10|1|10|60011020000100000000|普通门诊诊查费|次|;2|40016157|感冒清热颗粒|12g*20袋||1|6|6|Z-A01BA-G0120|感冒清热颗粒|包|tid;3|4001359|阿莫西林克拉维酸钾分散片|12s||1.3975|6|8.39|X-J01CA-A040-A006|阿莫西林|片|tid";
        String resultMsg = testDemo.invode("JS04", dj01);
        System.out.println(resultMsg);
    }


}
