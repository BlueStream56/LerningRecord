package com.example.emaildemo.util.escard;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

/**
 * 客户端端验证部平台签字工具
 *
 * <p> 验证算法供客户端开发人员参考 </p>
 *
 * @author pine
 */
public class VerifySignUtil {

    public static final String	X509    = "X.509"; // X509标准
    public static final String	BC		= "BC";
    public static final String	SIGNATURE_ALGORITHM	= "SHA1withRSA"; // 算法名称

    /**
     * 验证数字签名
     *
     * @param data
     *            编码数据
     * @param publicKey
     *            公钥
     * @param sign
     *            签名数据
     * @return boolean 校验成功返回 true 失败返回 false
     * @throws Exception 正常返回boolean值，验证签名过程失败抛出异常
     *
     */
    public static boolean verifySign(byte[] data, String publicKey, String sign) throws Exception {
        InputStream in = null;
        try {
            byte[] buf;
            // 解码公钥值
            BASE64Decoder decoder = new BASE64Decoder();
            buf = decoder.decodeBuffer(publicKey);
            in = new ByteArrayInputStream(buf);
            CertificateFactory cf = CertificateFactory.getInstance(X509);
            Certificate cert = cf.generateCertificate(in);
            Security.addProvider(new BouncyCastleProvider());
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BC);
            // 获取公钥对象
            RSAPublicKey pubKey = (RSAPublicKey)cert.getPublicKey();
            signature.initVerify(pubKey);
            signature.update(data);
            byte sig [] = decoder.decodeBuffer(sign);
            int len = sig.length;
            byte[] bufSig = new byte[len];
            for(int i = 0; i < len; ++i) {
                bufSig[i] = sig[len - 1 - i];
            }
            // 返回验证结果
            return signature.verify(bufSig);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            // 关闭流
            if(in != null){
                try {
                    in.close();
                }catch (Exception e){
                    throw e;
                }
            }
        }
    }

    /**
     * 构建ASCII规则的原串
     *
     * @param map
     * @return
     */
    public static String build(Map map) {
        String result;
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> first, Map.Entry<String, String> second) {
                return first.getKey().compareTo(second.getKey());
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : infoIds) {
            if (item.getKey() != null || item.getKey() != "") {
                String key = item.getKey();
                String val = String.valueOf(item.getValue());
                if (!(val == "" || val == null)) {
                    sb.append(key + "=" + val + "&");
                }
            }
        }
        result = sb.toString();
        result = result.substring(0,result.length()-1);
        System.out.println(result);
        return result;
    }

    /**
     * 获取证书中的公钥值
     *
     * @param certPath
     *            公钥证书路径
     * @return String 公钥值
     * @throws Exception
     */
    public static String getPublicKeyFromCer(String certPath) throws Exception {
        byte[] buf;
        FileInputStream fis = null;
        BASE64Encoder encoder = new BASE64Encoder();
        try{
            fis = new FileInputStream(certPath);
            buf = new byte[fis.available()];
            fis.read(buf);
            fis.close();
            return encoder.encode(buf);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            // 关闭流
            if(fis != null){
                try{
                    fis.close();
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public static void main(String [] args){
        /**
         * 样例串（取出响应的result-JSON格式数据）
         */
        String org = "{\"reason\":null,\"resultFlag\":null,\"busSeqFlag\":\"0\",\"checkNo\":\"+qiHlXQo4oORZfHydMxFAh52/4DoBosGXn0kfIAFM0Ydz9lUN1beL7eqIM5UEm+bUjb1bBTBjuDlMXoecHRSAKGh1YslXV4GGhLEQEFLX52zSgU6WejLYmh43W4tXDuhWgfeee4EMHmnBgnaZrGqMJkOlPnbIXkgyw+qKo7Hotk=\",\"esscNo\":\"B502F3CEF47406417983BB8DE605D9AB\",\"signLevel\":\"1\"}";
        JSONObject map = JSONObject.parseObject(org);
        /**
         * 取出签名串
         */
        String signKey = "checkNo";// 测试样例，根据具体文档自行替换
        String sign = map.get(signKey).toString();
        map.remove(signKey);
        String signOrg = build(map);
        String pubPath = "D:\\WorkSpace\\mspp\\mspp-common\\src\\main\\resources\\ESSC-VERIFY-PUBLIC-CERT0623.cer";
        /**
         * 待签名数据，需替换
         */
        try {
            System.out.println("签名值：\n"+sign);
            String pubKey = getPublicKeyFromCer(pubPath);
            byte myDataByte [] = signOrg.getBytes();
            boolean verifySign = verifySign(myDataByte,pubKey,sign);
            System.out.println("验结果：\n"+verifySign);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}