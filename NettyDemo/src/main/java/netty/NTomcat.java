package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//Netty 就是一个同时支持多协议的网络通信架构
public class NTomcat {

    //打开 Tomcat 源码，全局搜索 ServerSocket
    private int port = 8080;

    private Map<String, NServlet> servletMapping = new HashMap<String, NServlet>();

    private Properties webxml = new Properties();

    private void init(){
        try {
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "nweb.properties");

            webxml.load(fis);

            if (null != webxml && !webxml.isEmpty()){
                for (Object k: webxml.keySet()) {
                    String key = k.toString();
                    if (key.endsWith(".url")){
                        String servletName = key.replaceAll("\\.url$","");
                        String url = webxml.getProperty(key);
                        String className = webxml.getProperty(servletName + ".className");

                        NServlet servlet = (NServlet) Class.forName(className).newInstance();
                        servletMapping.put(url, servlet);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        init();

        //Netty 封装了 NIO 的 Reactor 模式，Boss，Worker
        //Boss 线程
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        //Worker 线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //1.创建对象
            ServerBootstrap server = new ServerBootstrap();

            //2.配置参数
            server.group(boosGroup, workerGroup)
            //主线程处理类，看到这样的写法，底层就是用反射
            //如果没有加上这部分就会报错
            //java.lang.IllegalStateException: channel or channelFactory not set
            //  at io.netty.bootstrap.AbstractBootstrap.validate(AbstractBootstrap.java:215)
            //  at io.netty.bootstrap.ServerBootstrap.validate(ServerBootstrap.java:191)
            //  at io.netty.bootstrap.ServerBootstrap.validate(ServerBootstrap.java:43)
            //  at io.netty.bootstrap.AbstractBootstrap.bind(AbstractBootstrap.java:274)
            //  at io.netty.bootstrap.AbstractBootstrap.bind(AbstractBootstrap.java:253)
            //  at netty.NTomcat.start(NTomcat.java:87)
            //  at netty.NTomcat.main(NTomcat.java:126)
            .channel(NioServerSocketChannel.class)
            //子线程处理类，Handler
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //无锁化串行编程
                    //Netty 对 HTTP 的封装，对顺序有要求
                    //HttpResponseEncoder 编码器
                    //责任链模式，双向链表 Inbound OutBound
                    socketChannel.pipeline().addLast(new HttpResponseEncoder());
                    //HTTPRequestDecoder 解码器
                    socketChannel.pipeline().addLast(new HttpRequestDecoder());
                    //业务逻辑处理
                    socketChannel.pipeline().addLast(new NTomcatHandler());
                }
            })
            //针对主线程的配置 分配线程最大数值 128
            .option(ChannelOption.SO_BACKLOG, 128)
            //针对子线程的配置，保持长连接
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            //3.启动服务器
            ChannelFuture channelFuture = server.bind(port).sync();
            System.out.println("NTomcat 已启动，监听的端口是：" + port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public class NTomcatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest){
                System.out.println("hello");
                HttpRequest req = (HttpRequest) msg;

                //转交给我们自己的 Request 实现
                NRequest request = new NRequest(ctx, req);
                //转交给我们自己的 Response 实现
                NResponse response = new NResponse(ctx, req);
                //实际业务处理
                String url = request.getUrl();
                if (servletMapping.containsKey(url)) {
                    servletMapping.get(url).service(request, response);
                }else {
                    response.write("404 - Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        }
    }

    public static void main(String[] args) {
        new NTomcat().start();
    }

}
