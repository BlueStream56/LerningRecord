package com.example.emaildemo;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class XyTestDemo {

    private static final String START_BODY = "SSSS|%s|*|%s|%s|%s|%s|ZZZZ";
    private static final String SBKJQ_BODY = "SSSS|JQ01|?|%s|%s|%s|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL|NULL;%s;%s|ZZZZ";

    private static String host="192.168.10.130";// IP
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
            return "ERROR:RRRR|-1|调用新余医保失败|ZZZZ";
        }
    }

    /**
     * 社保卡鉴权
     * @return
     */
    public String invokeSbkjq(String businessType, String businessBody){
        return invode(String.format(SBKJQ_BODY, termCode, user, pwd, businessType, businessBody));
    }

    public static void main(String[] args) {
//        js08();
//        js09();
        cx01();
    }

    public static void cx01(){
        XyTestDemo xyTestDemo = new XyTestDemo();
        String cx01 = "SSSS|CX01|*|20161018|99_1000|99_1000|BDJY|NULL|674447490|NULL|ZZZZ";
        String returnMsg = xyTestDemo.invode(cx01);
        System.out.println(returnMsg);
    }

    public static void js07(){
        XyTestDemo xyTestDemo = new XyTestDemo();
        String js07 = "SSSS|JS07|*|20200613|99_1000|99_1000|NULL|674447490|NULL|11|NULL|NULL|NULL|NULL|20131209110781|黄琳|20200613|081833|1|FALSE|NULL|NULL|360521;1|117643|阿司匹林|NULL|NULL|15|1|15|X-B01AC-A056-A001|阿司匹林|NULL|NULL;2|118072|阿司匹林|NULL|NULL|0|1|0|X-B01AC-A056-E003|阿司匹林|NULL|NULL;3|118707|复方阿魏酸钠阿司匹林|NULL|NULL|0|1|0|X-B01AC-F122-E001|复方阿魏酸钠阿司匹林|NULL|NULL|ZZZZ";
        String returnMsg = xyTestDemo.invode(js07);
        System.out.println(returnMsg);
    }

    public static void js08(){
        XyTestDemo xyTestDemo = new XyTestDemo();
        String js08 = "SSSS|JS08|*|20200613|99_1000|99_1000|NULL|674447490|NULL|11|NULL|NULL|NULL|NULL|20131209110781|黄琳|20200613|081833|1|FALSE|NULL|NULL|360521;1|117643|阿司匹林|NULL|NULL|15|1|15|X-B01AC-A056-A001|阿司匹林|NULL|NULL;2|118072|阿司匹林|NULL|NULL|0|1|0|X-B01AC-A056-E003|阿司匹林|NULL|NULL;3|118707|复方阿魏酸钠阿司匹林|NULL|NULL|0|1|0|X-B01AC-F122-E001|复方阿魏酸钠阿司匹林|NULL|NULL|ZZZZ";
        String returnMsg = xyTestDemo.invode(js08);
        System.out.println(returnMsg);
    }

    public static void js09(){
        XyTestDemo xyTestDemo = new XyTestDemo();
        String js09 = "SSSS|JS09|*|20200613|99_1000|99_1000|NULL|674447490|NULL|00000000000028110256|32272685|360502|ZZZZ";
        String returnMsg = xyTestDemo.invode(js09);
        System.out.println(returnMsg);
    }

}
