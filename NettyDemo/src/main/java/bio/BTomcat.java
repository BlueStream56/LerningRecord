package bio;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BTomcat {

    private int port = 8080;
    private ServerSocket serverSocket;
    private Map<String, BServlet> servletMapping = new HashMap<String, BServlet>();

    private Properties webxml = new Properties();

    /**
     * 第一阶段：初始化阶段，主要是完成对 web.xml 文件的解析
     */
    private void init(){
        //加载 web.xml 文件，同时初始化 ServletMapping 对象
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fis);

            if (!webxml.isEmpty()){
                for (Object k: webxml.keySet()) {
                    String key = k.toString();
                    if (key.endsWith(".url")){
                        String serviceName = key.replaceAll("\\.url$","");
                        String url = webxml.getProperty(key);
                        String className = webxml.getProperty(serviceName+".className");
                        //单实例，多线程
                        BServlet servlet = (BServlet) Class.forName(className).newInstance();
                        servletMapping.put(url, servlet);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 第二阶段：服务就绪阶段，完成 ServerSocket 的准备工作
     */
    public void start(){
       //1.加载配置文件，初始化 ServletMapping
       init();

       try {
           serverSocket = new ServerSocket(this.port);

           System.out.println("BTomcat 已启动，监听的端口是：" + this.port);

           //2.等待用户请求，用一个死循环来等待用户请求
           while (true){
               Socket socket = serverSocket.accept();
               //3.HTTP请求，发送的数据就是字符串——有规律的字符串（HTTP）
               process(socket);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     * 第三阶段：接受请求阶段，完成每一次请求的处理
     * @param socket
     * @throws Exception
     */
    private void process(Socket socket) throws Exception {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        //4.Request(InputStream)/Response(OutputStream)
        BRequest request = new BRequest(is);
        BResponse response = new BResponse(os);

        //5.从协议内容中获取 URL，把相应的 Servlet 用反射进行实例化
        String url = request.getUrl();

        if (servletMapping.containsKey(url)){
            //6.调用实例化对象的 service() 方法，执行具体的逻辑 doGet()/doPost() 的方法
            servletMapping.get(url).service(request, response);
        }else {
            response.write("404 - Not Found");
        }

        os.flush();
        os.close();

        is.close();
        socket.close();
    }

    public static void main(String[] args) {
        new BTomcat().start();
    }

}
