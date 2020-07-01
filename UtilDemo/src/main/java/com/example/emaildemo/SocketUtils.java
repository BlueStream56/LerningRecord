package com.example.emaildemo;

import java.io.*;
import java.net.Socket;

public class SocketUtils {

    private static Socket socket = null;

    public static Socket getSocket(String host, Integer port) throws IOException {
        if (socket == null){
            socket = new Socket(host, port);
        }
        return socket;
    }

    public SocketUtils() {
    }

    public static String sendMsg(String host, Integer port, String msg, String charset) throws IOException {
        System.out.println(msg);
        try {
            Long start = System.currentTimeMillis();
            socket = new Socket(host, port);
//            getSocket(host, port);
            Long end = System.currentTimeMillis();
            System.out.println("==================共花费"+(end-start)+"==================");
        } catch (Exception var11) {
            var11.printStackTrace();
            return "ERROR:找不到对应的通讯地址，IP地址:" + host + ",端口：" + port;
        }

        BufferedReader br = getReader(socket, charset);
        PrintWriter pw = getWriter(socket, charset);
//        socket.setSoTimeout(5000);
        pw.println(msg);
        char[] buf = new char[2048];
        int len = 0;
        StringBuilder dataLines = new StringBuilder();

        String text;
        while((len = br.read(buf)) != -1) {
            text = new String(buf, 0, len);
            dataLines.append(text);
            if (text.indexOf("\n") > 0 || text.indexOf("\r") > 0) {
                break;
            }
        }

        text = dataLines.toString();
        text = text.replace("\r", "").replace("\n", "");
        return text;
    }

    private static BufferedReader getReader(Socket socket, String charset) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn, charset));
    }

    private static PrintWriter getWriter(Socket socket, String charset) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charset));
        return new PrintWriter(bw, true);
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        Integer port = 7000;
        String msg = "BASHJGSC;;;360999|361099|GFT0000000000001|01|362202201901010012|周|2|未通过原因|20200623101232|审核人|20200623|20200623|310|GFT0000000000001";
        String charset = "GBK";
        try {
            String returnMsg = sendMsg(host,port,msg,charset);
            System.out.println(returnMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}