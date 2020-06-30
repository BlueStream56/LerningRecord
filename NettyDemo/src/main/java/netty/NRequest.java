package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class NRequest {

    private ChannelHandlerContext handlerContext;

    private HttpRequest request;

    public NRequest(ChannelHandlerContext handlerContext, HttpRequest request){
        this.handlerContext = handlerContext;
        this.request = request;
    }

    public String getUrl(){
        return request.uri();
    }

    public String getMethod(){
        return request.method().name();
    }

    public Map<String, List<String>> getParameters(){
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name){
        Map<String, List<String>> params = getParameters();
        List<String> param = params.get(name);
        if (null != param && !param.isEmpty()){
            return null;
        }else {
            return param.get(0);
        }
    }

}
