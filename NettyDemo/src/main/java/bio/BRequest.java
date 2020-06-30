package bio;

import java.io.InputStream;

public class BRequest {

    private String method;
    private String url;

    public BRequest(InputStream inputStream) {
        try {
            //获取HTTP内容
            String content = "";
            byte[] buff = new byte[1024];
            int len = 0;
            if ((len = inputStream.read(buff)) > 0){
                content = new String(buff, 0, len);
            }
            System.out.println("request:");
            System.out.println(content);
            String line = content.split("\\n")[0];
            String[] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}
