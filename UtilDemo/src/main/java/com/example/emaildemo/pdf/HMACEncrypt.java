package com.example.emaildemo.pdf;


import sun.misc.BASE64Decoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * <ul>
 * <li>文件名称：PDFHmacsha </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/9 </li>
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
public class HMACEncrypt {

    private final static String HMACSHA256 = "HmacSHA256";
    private final static String appSecret = "49ae8438-8beb-41eb-abfd-02ebbd1a9641";
    private final static String appKey = "bdb34151-d1ca-420f-a021-1da7f8a1144e";
    private final static String username = "rsapp";
    private final static String password = "888888";
//    private final static Long clientId = 202004100932L;
    private final static Long clientId = 202004101114L;
    private final static String fileType_jfzm = "jfzm";
    private final static String fileType_dzd = "dzd";


    public static byte[] encryptHMACSHA256(String secretKey, String plaintext) {
        return encrypt(secretKey, plaintext, HMACSHA256);
    }

    private static byte[] encrypt(String secretString, String plaintext, String encryptType) {
        SecretKey secretKey = new SecretKeySpec(secretString.getBytes(StandardCharsets.UTF_8), encryptType);
        Mac mac = null;
        try {
            mac = Mac.getInstance(encryptType);
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD签名失败！");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("MD签名失败！");
            e.printStackTrace();
        }
        byte[] result = mac.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return result;
    }


    public static void main(String[] args) {
        String fileName = "缴费证明(易香保).pdf";
//        String fileName = "缴费证明_1586482120499.pdf";

        String encodeToString = Base64.getEncoder().encodeToString(HMACEncrypt.encryptHMACSHA256(appSecret,fileName+clientId));
        String token = appKey + ":" + encodeToString;
        String filetype = fileType_jfzm;
//        String filetype = fileType_dzd;
        System.out.println("token = " + token);
        System.out.println("fileType = " + filetype);
        System.out.println("clientId = " + clientId);
    }



}
