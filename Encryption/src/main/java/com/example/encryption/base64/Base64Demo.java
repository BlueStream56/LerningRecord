package com.example.encryption.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Base64Demo {

    private static final String imagePath = "C:\\Users\\NH55\\Desktop\\demo.jpg";

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    public static final Map<String, String> TYPES = new HashMap<>();

    static {
        // 图片，此处只提取前六位作为魔数
        TYPES.put("FFD8FF", "jpg");
        TYPES.put("89504E", "png");
        TYPES.put("474946", "gif");
        TYPES.put("524946", "webp");
    }

    public static String readImage() {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(imagePath);
//            byte[] b = new byte[1024];
//            int len = -1;
//            while ((len = inputStream.read(b)) != -1){
//                byteArrayOutputStream.write(b, 0, len);
//            }
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            return encoder.encode(b);
        }catch (Exception e){
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    public static String readImage1() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(imagePath);
            byte[] b = new byte[4];
            inputStream.read(b, 0, b.length);
            String fileCode = getHex(b, 6);
            System.out.println(fileCode);
            return encoder.encode(b);
        }catch (Exception e){
            try {
                inputStream.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;
    }

    public static void copyImage(String base64Image){
        File file = null;
        String fileName = String.valueOf(System.currentTimeMillis());
        FileOutputStream fileOutputStream = null;
        try {
            file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            byte[] bytes = decoder.decodeBuffer(base64Image);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            byte[] headCode = new byte[4];
            byteArrayInputStream.read(headCode, 0, 4);
            String fileCode = getHex(headCode, 6);
            System.out.println(fileCode);
            byte[] buffer = new byte[1024];
            fileOutputStream = new FileOutputStream(file);
            int byteRead = -1;
            while ((byteRead = byteArrayInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, byteRead);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getHex(byte[] bytes, int magicNumberLength){
        StringBuilder stringBuilder = new StringBuilder();
        int magicNumberByteLength = magicNumberLength/2;
        for (int i = 0; i < magicNumberByteLength; i++) {
            stringBuilder.append(Integer.toHexString(bytes[i] >> 4 & 0xF));
            stringBuilder.append(Integer.toHexString(bytes[i] & 0xF));
        }
        return stringBuilder.toString();
    }

    public static String checkType(String headrFile){
        switch (headrFile){
            case "FFD8FF": return "jpg";
            case "89504E": return "png";
            case "474946": return "jif";
            default: return "jpg";
        }
    }

    public static void main(String[] args) throws IOException {
        String base64Image = readImage();
        System.out.println(base64Image);
//        copyImage(base64Image);
    }

}
