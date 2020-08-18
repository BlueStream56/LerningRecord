package com.example.emaildemo.util.ecard;

import com.alibaba.fastjson.JSON;
import com.wondersgroup.ecard.sdk.client.Client;
import com.wondersgroup.ecard.sdk.client.impl.DefaultClientImpl;
import com.wondersgroup.ecard.sdk.core.MessageRequest;
import com.wondersgroup.ecard.sdk.core.MessageResponse;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EcardQrcodeInfoVerifyTest {
    @Test
    public void codeInfoVerifyTest() {
        //外网访问实例化客户端
        //读取证书流
//        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("cer.pfx");
        //实例化
        Client client = new DefaultClientImpl(Config.SERVER_URL, Config.SIGN_PRIVATE_KEY, Config.SIGN_PUBLIC_KEY);

        //内网访问实例化客户端
//        Client client = new DefaultClientImpl(Config.SERVER_URL, Config.SIGN_PRIVATE_KEY, Config.SIGN_PUBLIC_KEY);
        /**
         * 实例化请求报文对象
         * 已经默认初始化部分参数
         * 默认参数:
         * charset=UTF-8
         * signType=RSA2
         * timestamp=系统当前时间
         * version=1.0
         */
        MessageRequest request = new MessageRequest();
        request.setAppId(Config.APP_ID);
        request.setMethod("sicard.person.ecard.query");//调用的接口代码
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("ecard_no", "D4982224961B11C8B452364302606F39");
        String biz = JSON.toJSONString(bizContent);
        request.setBizContent(biz);//业务JSON参数,参考接口文档,
        try {
            //调用接口
            MessageResponse response = client.invoke(request);
            //服务端应答的完整字符串,正常业务可以忽略,可以作为log日志记录,便于发生故障时排错
            String responseContent = response.getResponseContent();
            System.out.println("服务端应答完整参数=>" + responseContent);
            if (response.isSuccess()) {
                //调用成功
                String content = response.getBizContent();
                System.out.println("调用成功:");
                System.out.println("code=>" + response.getCode());
                System.out.println("msg=>" + response.getMsg());
                System.out.println("sub_code=>" + response.getSubCode());
                System.out.println("sub_msg=>" + response.getSubMsg());
                System.out.println("业务JSON参数=>" + bizContent);
//					应答报文反序列化Java对象
                EcardQrcodeInfoVerifyResponse ecardQrcodeInfoVerifyResponse = JSON.parseObject(content, EcardQrcodeInfoVerifyResponse.class);
                //TODO 做你的业务逻辑
                System.out.println("area_code=>" + ecardQrcodeInfoVerifyResponse.getSiCardIssueArea());
                System.out.println("IdNo=>" + ecardQrcodeInfoVerifyResponse.getIdNo());
                System.out.println("Gender=>" + ecardQrcodeInfoVerifyResponse.getGender());
                System.out.println("Name=>" + ecardQrcodeInfoVerifyResponse.getName());
                System.out.println("IdType=>" + ecardQrcodeInfoVerifyResponse.getIdType());
                System.out.println("SiCardNo=>" + ecardQrcodeInfoVerifyResponse.getSiCardNo());
                System.out.println("SiNo=>" + ecardQrcodeInfoVerifyResponse.getSiNo());
            } else {
                //调用失败
                if (!"000000".equals(response.getCode())) {
                    //网关调用失败
                    System.out.println("网关调用异常:");
                    System.out.println("code=>" + response.getCode());
                    System.out.println("msg=>" + response.getMsg());
                } else {
                    System.out.println("业务异常:");
                    System.out.println("sub_code=>" + response.getSubCode());
                    System.out.println("sub_msg=>" + response.getSubMsg());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
