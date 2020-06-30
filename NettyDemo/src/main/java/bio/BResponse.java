package bio;

import java.io.OutputStream;

public class BResponse {

    private OutputStream outputStream;

    public BResponse(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void write(String s) throws Exception {
        //输出也要遵循HTTP
        //状态码为200
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        System.out.println("response:");
        System.out.println(stringBuilder.toString());
        outputStream.write(stringBuilder.toString().getBytes());
    }

}
