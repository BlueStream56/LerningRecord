package com.example.emaildemo.util.escard;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class Sha1withRSAUtil {
    private static final String publicKeyFileName = "D:\\huahua_user_public.cer";
    private static final String privateKeyFileName = "D:\\huahua_user.pfx";
    private static final String pfxPassword = "2078888";//私钥文件获取时设置的密钥
    private static String aliasName = "mykey";//alias名称
    public static final String	BC		= "BC";

    public static void main(String[] args) throws Exception {
        String str = "12342342343242312";
        String sign = sign(str);
        System.out.println("签名："+sign(str));
        System.out.println(verify(sign,str));
    }

    /**
     * 签名
     *
     * @return 签名后经过base64处理的字符串
     * @throws Exception
     */
    public static String sign(String str) {
        String base64Sign = "";
        InputStream fis = null;
        try {
            fis = new FileInputStream(privateKeyFileName);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] pscs = pfxPassword.toCharArray();
            keyStore.load(fis, pscs);
            PrivateKey priKey = (PrivateKey) (keyStore.getKey(aliasName, pscs));
            // 签名
            Security.addProvider(new BouncyCastleProvider());
            Signature sign = Signature.getInstance("SHA1withRSA", BC);
            sign.initSign(priKey);
            byte[] bysData = str.getBytes("UTF-8");
            sign.update(bysData);
            byte[] signByte = sign.sign();
            int len = signByte.length;
            byte[] newSign = new byte[len];
            for (int i = 0; i < len; i++) {
                newSign[i] = signByte[len - 1 - i];
            }
            BASE64Encoder encoder = new BASE64Encoder();
            base64Sign = encoder.encode(newSign);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64Sign;
    }

    /**
     * 数据验证
     *
     * @param signStr 加密后的数据
     * @param verStr  原始字符
     * @return
     */
    public static boolean verify(String signStr, String verStr)
            throws Exception {
        boolean verfy = false;
        InputStream fis = null;
        try {
            fis = new FileInputStream(publicKeyFileName);
            CertificateFactory cf = CertificateFactory.getInstance("x509");
            Certificate cerCert = cf.generateCertificate(fis);
            PublicKey pubKey = cerCert.getPublicKey();
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] signed = decoder.decodeBuffer(signStr);
            int len = signed.length;
            byte[] newSign = new byte[len];
            for (int i = 0; i < len; i++) {
                newSign[i] = signed[len - 1 - i];
            }
            Security.addProvider(new BouncyCastleProvider());
            Signature sign = Signature.getInstance("SHA1withRSA", BC);
            sign.initVerify(pubKey);
            sign.update(verStr.getBytes("UTF-8"));
            verfy = sign.verify(newSign);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return verfy;
    }


    /**
     * 通过公钥文件进行加密数据
     *
     * @return 加密后经过base64处理的字符串
     */
    public static String encrypt(String source) throws Exception {
        InputStream fis = null;
        try {
            fis = new FileInputStream(publicKeyFileName);
            CertificateFactory cf = CertificateFactory.getInstance("x509");
            Certificate cerCert = cf.generateCertificate(fis);
            PublicKey pubKey = cerCert.getPublicKey();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] sbt = source.getBytes();
            byte[] epByte = cipher.doFinal(sbt);
            BASE64Encoder encoder = new BASE64Encoder();
            String epStr = encoder.encode(epByte);
            return epStr;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 通过私钥文件进行解密数据
     *
     * @return 解密后的明文字符串
     */
    public static String decode(String source) throws Exception {
        BASE64Decoder b64d = new BASE64Decoder();
        byte[] keyByte = b64d.decodeBuffer(source);
        InputStream fis = null;
        try {
            fis = new FileInputStream(privateKeyFileName);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] pscs = pfxPassword.toCharArray();
            keyStore.load(fis, pscs);
            PrivateKey priKey = (PrivateKey) (keyStore.getKey(aliasName, pscs));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] epByte = cipher.doFinal(keyByte);
            return new String(epByte, "UTF-8");
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}