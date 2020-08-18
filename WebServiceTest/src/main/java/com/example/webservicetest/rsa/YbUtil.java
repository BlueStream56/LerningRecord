package com.example.webservicetest.rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class YbUtil {

    private static final Logger log = LoggerFactory.getLogger(YbUtil.class);

    //字符串进行加密算法的名称
    public static final String ALGORITHM = "RSA";
    //字符串进行加密填充的名称
    public static final String PADDING = "RSA/NONE/NoPadding";
    //字符串持有安全提供者的名称
    public static final String PROVIDER = "BC";

    public static final String PRIVATE_KEY_FILE = YbUtil.class.getClassLoader().getResource("").getPath()+"bcp-java.keystore";

    /**
     * 加载公钥文件
     * @param path
     * @return
     * @throws Exception
     */
    public static String loadPrivateKeyByFile(String path) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = decryptBASE64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static void main(String[] args) throws Exception {
        String content = "这是用于签名的原始数据";
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(loadPrivateKeyByStr(loadPrivateKeyByFile(PRIVATE_KEY_FILE)));
        signature.update(content.getBytes("UTF-8"));
        System.out.println(new BASE64Encoder().encode(signature.sign()));
    }

}