package netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class NResponse {

    private ChannelHandlerContext handlerContext;

    private HttpRequest request;

    public NResponse(ChannelHandlerContext handlerContext, HttpRequest request){
        this.handlerContext = handlerContext;
        this.request = request;
    }

    public void write(String out) throws Exception {
        try {
            if (null == out || out.length() == 0){
                return;
            }
            FullHttpResponse response = new DefaultFullHttpResponse(
                    //设置版本为HTTP 1.1
                    HttpVersion.HTTP_1_1,
                    //设置响应状态码
                    HttpResponseStatus.OK,
                    //将输出内容编码格式设置为UTF-8
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8"))
            );

            response.headers().set("Content-Type", "text/html");

            handlerContext.write(response);
        } finally {
            handlerContext.flush();
            handlerContext.close();
        }
    }

}
