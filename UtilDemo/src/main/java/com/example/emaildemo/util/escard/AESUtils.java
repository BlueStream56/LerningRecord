package com.example.emaildemo.util.escard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 * Created by Kevin on 2018/6/21.
 */
public class AESUtils {

    static String iv = "d22b0a851e014f7b";

    private final static Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Base64.Decoder decoder = Base64.getDecoder();

    private static final String defaultCharset = "UTF-8";
    private static final String KEY_AES = "AES";
    public static final String KEY = "8192553d3db81630";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static String encrypt(String data, String key) {
        if (ObjectUtils.isEmpty(key)){
            key = KEY;
        }
        return doAES(data, key, iv.getBytes(), Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return
     */
    public static String decrypt(String data, String key) {
        if (ObjectUtils.isEmpty(key)){
            key = KEY;
        }
        return doAES(data, key, iv.getBytes(), Cipher.DECRYPT_MODE);
    }

    public static String doAES(String data, String secretKey, byte[] iv, int mode) {

        try {
            boolean encrypt = mode == Cipher.ENCRYPT_MODE;
            byte[] content;
            //true 加密内容 false 解密内容
            if (encrypt) {
                content = data.getBytes(defaultCharset);
            } else {
                content = decoder.decode(data);
            }

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), KEY_AES);
            cipher.init(mode, skeySpec, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            if (encrypt) {
                return new String(encoder.encode(result));
            } else {
                return new String(result, defaultCharset);
            }
        } catch (Exception e) {
            logger.error("aes error:", e);
        }
        return null;
    }

    /**
     * 文件加密
     *
     * @param input 需要加密的内容
     * @param secretKey  加密密码
     * @param output  输出的文件
     */
    public static void encryptFile(File input, String secretKey, File output) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), KEY_AES);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));

            byte[] buffer = new byte[1024];

            FileInputStream in = new FileInputStream(input);
            CipherInputStream cin = new CipherInputStream(in, cipher);

            FileOutputStream out = new FileOutputStream(output);

            int r = 0;

            while((r = cin.read(buffer)) > 0){
                out.write(buffer,0,r);
            }

            cin.close();
            in.close();
            out.close();
        } catch (Exception e) {
            logger.error("aes error:", e);
        }
    }

    /**
     * 文件解密
     *
     * @param input 需要解密的内容
     * @param secretKey  解密密码
     * @param output  输出的文件
     */
    public static void decryptFile(File input, String secretKey, File output) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec skeySpec = new SecretKeySpec(secretKey.getBytes(), KEY_AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));

            byte[] buffer = new byte[1024];

            FileInputStream in = new FileInputStream(input);
            FileOutputStream out = new FileOutputStream(output);
            CipherOutputStream cout = new CipherOutputStream(out, cipher);

            int r = 0;

            while((r = in.read(buffer)) > 0){
                cout.write(buffer,0,r);
            }

            cout.close();
            in.close();
            out.close();
        } catch (Exception e) {
            logger.error("aes error:", e);
        }
    }

    public static void main(String[] args) {
        String localPath = "d:\\6401000100_20190313.tar.gz";
        String encrypt = "d:\\encrypt.tar.gz";
        String decrypt = "d:\\decrypt.tar.gz";
        String key = "4p8zelq0qvvaq90m";
        encryptFile(new File(localPath),key, new File(encrypt));
        decryptFile(new File(encrypt),key, new File(decrypt));

    }
}

