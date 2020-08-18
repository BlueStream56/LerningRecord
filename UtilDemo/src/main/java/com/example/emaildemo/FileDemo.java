package com.example.emaildemo;

import java.io.*;

/**
 * @author NH55
 */
public class FileDemo {

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        String msg = "adfasdrtfqwaersadfsdf";
        outputStream.write(msg.getBytes());
        InputStream inputStream = new ByteArrayInputStream(outputStream.toString().getBytes());

        OutputStream fos = new FileOutputStream("C:\\Users\\NH55\\Desktop\\test.txt");
        int n;
        while ((n = inputStream.read()) != -1){
            fos.write(n);
        }
        fos.flush();
        fos.close();
    }

}
