package com.example.emaildemo.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;


public class RSAUtils {
	 public static final String SIGN_ALGORITHMS = "SHA1withRSA";
     private static  final BouncyCastleProvider PROVIDER = new  BouncyCastleProvider();
     
     static{
  		if(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null){
 			Security.addProvider(PROVIDER);
 		}
     }
     /**
     * 获取文件流
     * @param resource
     * @return
     * @throws Exception
     */
     private static InputStream getResourceAsStream(String resource) throws Exception {
         InputStream in = null;
         String rootPath = null;
         File file = null;
         if(in==null){
         	rootPath = RSAUtils.class.getResource("/").getPath()+resource;
         	file = new File(rootPath);
         	if(file==null){
         		in =  new FileInputStream(file);
         	}
         }
         if(in==null){
         	rootPath = Thread.currentThread().getContextClassLoader().getResource("") + resource;
         	file = new File(rootPath);
         	if(file==null){
         		in =  new FileInputStream(file);
         	}
         }
         if(in==null){
         	rootPath = Thread.currentThread().getContextClassLoader().getResource("") + resource;
         	file = new File(rootPath);
         	if(file==null){
         		in =  new FileInputStream(file);
         	}
         }
         if(in==null){
         	rootPath = RSAUtils.class.getClassLoader().getResource("") + resource;
         	file = new File(rootPath);
         	if(file==null){
         		in =  new FileInputStream(file);
         	}
         }
         if (in==null) {
			 in = RSAUtils.class.getClassLoader().getResourceAsStream(resource);
		 }
         if (in==null) {
			 in = ClassLoader.getSystemResourceAsStream(resource);
		 }
         if (in==null) {
			 throw new Exception("请将密钥文件"+resource+"放到工程classpath目录！");
		 }
         return in;
 	}
 	 
 	 /**
 	  * 获取公钥
 	  * @param key
 	  * @return
 	  * @throws Exception
 	  */
// 	 public static PublicKey getPublicKey() throws Exception {
// 		InputStream in = getResourceAsStream("publickey.keystore");
// 		byte[] bytes = new byte[0];
// 		bytes = new byte[in.available()];
// 		in.read(bytes);
// 		return bytesToPublicKey(bytes);
// 	 }
     
 	 /**
 	  * 获取私钥
 	  * @return
 	  * @throws Exception
 	  */
// 	 public static PrivateKey getPrivateKey()throws Exception {
// 		InputStream in = getResourceAsStream("privatekey.keystore");
// 		byte[] bytes = new byte[0];
// 		bytes = new byte[in.available()];
// 		in.read(bytes);
// 		return bytesToPrivateKey(bytes);
// 	 }
     
     
     /**
 	 * * 生成密钥对 *
 	 * 
 	 * @return KeyPair *
 	 * @throws Exception
 	 */
 	public static KeyPair generateKeyPair() throws Exception {
 		try {
 			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",PROVIDER);
 		    // 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
 			final int KEY_SIZE = 1024;
 			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
 			KeyPair keyPair = keyPairGen.generateKeyPair();
 			return keyPair;
 		} catch (Exception e) {
 			throw new Exception(e.getMessage());
 		}
 	}
 	
 	/***
 	 *  使用私钥对数据签名
 	 * @param content 待签名数据
 	 * @return 签名数据
 	 */
// 	public static String sign(String content){
// 		PrivateKey privateKey = null;
// 		try {
// 			privateKey = getPrivateKey();
// 			Signature signature = Signature.getInstance(SIGN_ALGORITHMS,PROVIDER);
// 	 		signature.initSign(privateKey);
// 	 		signature.update(content.getBytes("UTF-8"));
// 	 		byte[] signed = signature.sign();
// 	 		return Hex.encodeHexString(signed);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 		return null;
// 	}
 	
 	/**
 	 * 使用公钥对签名数据进行验证
 	 * @param content  待签名数据
 	 * @param sign     签名数据
 	 * @return 验证结果
 	 */
// 	public static boolean verify(String content , String sign){
// 		PublicKey publicKey = null;
// 		try {
// 			publicKey = getPublicKey();
// 			Signature signature = Signature.getInstance(SIGN_ALGORITHMS,PROVIDER);
// 	 		signature.initVerify(publicKey);
// 	 		signature.update(content.getBytes("UTF-8"));
// 			if(Pattern.matches("^[0-9a-f]+$", sign)){
// 				return signature.verify(Hex.decodeHex(sign.toCharArray()));
// 			}else if(Base64.isBase64(sign)){
// 				byte[] signData = Base64.decodeBase64(sign);
// 				return signature.verify(signData);
// 			}else{
// 				return false;
// 			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 		return false;
// 	}
 	

 	
 	/**
 	 * 将公钥/私钥转为pem格式密钥串
 	 * @param key
 	 * @return
 	 * @throws IOException
 	 */
 	/*public static String keyToString(Key key)throws IOException{
        PemObject pemObject = new PemObject("RSA PRIVATE KEY",);
 		StringWriter stringWriter = new StringWriter(); 
 		PemWriter pemFormatWriter = new PemWriter(stringWriter); 
 		//pemFormatWriter.writeObject(key);
 		pemFormatWriter.writeObject(pemObject);
		pemFormatWriter.close();
 		return stringWriter.toString();
 	}*/
 	
 	/**
 	 * 将公钥/私钥转为pem格式密钥byte数组
 	 * @param key
 	 * @return
 	 * @throws IOException
 	 */
 	/*public static byte[] keyToBytes(Key key) throws IOException{
 		return keyToString(key).getBytes();
 	}*/
 	
 	 	
 	/**
 	 * 将pem格式的byte数组转为私钥
 	 * @param bytes
 	 * @return
 	 * @throws IOException
 	 */
// 	public static PrivateKey bytesToPrivateKey(byte[] bytes) throws IOException {
// 		PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
// 		KeyPair kp = (KeyPair)reader.readObject();
// 		reader.close();
// 		return  kp.getPrivate();
// 	}
 	
 	/**
 	 * 将pem格式的byte数组转为公钥
 	 * @param bytes
 	 * @return
 	 * @throws IOException
 	 * @throws NoSuchAlgorithmException
 	 * @throws InvalidKeySpecException
 	 */
// 	public static PublicKey bytesToPublicKey(byte[] bytes) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
// 		if(Base64.isBase64(bytes)){
// 			PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
// 			PublicKey publicKey = (PublicKey)reader.readObject();
// 	 		reader.close();
// 	 		if(publicKey == null){
// 	 			byte[] publicKeyBytes = Base64.decodeBase64(bytes);
// 	 	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
// 	 	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
// 	 	        publicKey =  keyFactory.generatePublic(x509KeySpec);
// 	 		}
// 		    return publicKey;
// 		}
// 		return null;
// 	}
 	
 	
 	 /**  
     * 公钥加密  
     * @param data  
     * @return
     * @throws Exception
     */    
//    public static String encryptByPublicKey(String data)
//            throws Exception {
//    	RSAPublicKey publicKey = null;
//    	publicKey = (RSAPublicKey) getPublicKey();
//        Cipher cipher = Cipher.getInstance("RSA", PROVIDER);
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        // 模长
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        //如果明文长度大于模长-11则要分组加密
//        for (String s : datas) {
//            mi += bcd2Str(cipher.doFinal(s.getBytes()));
//        }
//        return mi;
//    }
    
    /**  
     * 私钥解密  
     * @param data  
     * @return
     * @throws Exception
     */    
//    public static String decryptByPrivateKey(String data)
//    		throws Exception {
//    	RSAPrivateKey privateKey = (RSAPrivateKey) getPrivateKey();
//    	Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
//    	cipher.init(Cipher.DECRYPT_MODE, privateKey);
//    	//模长
//    	int key_len = privateKey.getModulus().bitLength() / 8;
//    	byte[] bytes = data.getBytes();
//    	byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//    	//System.err.println(bcd.length);
//    	//如果密文长度大于模长则要分组解密
//    	String ming = "";
//    	byte[][] arrays = splitArray(bcd, key_len);
//    	for(byte[] arr : arrays){
//    		ming += new String(cipher.doFinal(arr));
//    	}
//    	return ming;
//    }
    
    /**  
     * ASCII码转BCD码  
     *   
     */    
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {    
        byte[] bcd = new byte[asc_len / 2];    
        int j = 0;    
        for (int i = 0; i < (asc_len + 1) / 2; i++) {    
            bcd[i] = asc_to_bcd(ascii[j++]);    
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));    
        }    
        return bcd;    
    }    
    public static byte asc_to_bcd(byte asc) {    
        byte bcd;    
    
        if ((asc >= '0') && (asc <= '9')) {
			bcd = (byte) (asc - '0');
		} else if ((asc >= 'A') && (asc <= 'F')) {
			bcd = (byte) (asc - 'A' + 10);
		} else if ((asc >= 'a') && (asc <= 'f')) {
			bcd = (byte) (asc - 'a' + 10);
		} else {
			bcd = (byte) (asc - 48);
		}
        return bcd;    
    }    
 	
 	/**  
     * 拆分字符串  
     */    
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;    
        int y = string.length() % len;    
        int z = 0;    
        if (y != 0) {    
            z = 1;    
        }    
        String[] strings = new String[x + z];
        String str = "";
        for (int i=0; i<x+z; i++) {    
            if (i==x+z-1 && y!=0) {    
                str = string.substring(i*len, i*len+y);    
            }else{    
                str = string.substring(i*len, i*len+len);    
            }    
            strings[i] = str;    
        }    
        return strings;    
    }    
    /**  
     *拆分数组   
     */    
    public static byte[][] splitArray(byte[] data,int len){    
        int x = data.length / len;    
        int y = data.length % len;    
        int z = 0;    
        if(y!=0){    
            z = 1;    
        }    
        byte[][] arrays = new byte[x+z][];    
        byte[] arr;    
        for(int i=0; i<x+z; i++){    
            arr = new byte[len];    
            if(i==x+z-1 && y!=0){    
                System.arraycopy(data, i*len, arr, 0, y);
            }else{    
                System.arraycopy(data, i*len, arr, 0, len);
            }    
            arrays[i] = arr;    
        }    
        return arrays;    
    } 
    /**  
     * BCD转字符串  
     */    
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;    
    
        for (int i = 0; i < bytes.length; i++) {    
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);    
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');    
    
            val = (char) (bytes[i] & 0x0f);    
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');    
        }    
        return new String(temp);
    }
    
    public static String getJSONString(String jsonname) {
    	String retStr = "";
    	try {
			InputStream is = getResourceAsStream(jsonname);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while ((i = is.read()) != -1) {  
			    baos.write(i);  
			}
			retStr = baos.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return retStr;
    }
    
}
