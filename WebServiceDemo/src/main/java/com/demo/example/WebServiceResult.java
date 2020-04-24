package com.demo.example;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.HashMap;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称：WebServiceResult </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/22 </li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author caoxx
 * @version 1.0.0
 */
public class WebServiceResult {

    private final static String JXYDJYWS_WSDL_URL = "http://10.105.65.19:8001/doa003Web/services/JxydjyWS?wsdl";
    private final static String JXYDJYWS_METHODE = "JxydjyWS";

    private static Map<String, Client> clientMap = new HashMap<>();

    public static void main(String[] args) {

    }

    private static Client getClient(String url) {
        Client client = clientMap.get(url);
        if(null == client) {
            DynamicClientFactory factory = DynamicClientFactory.newInstance();
            System.out.println("========创建cxf client url：" + url);
            client = factory.createClient(url);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy =  new  HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(60000);
            httpClientPolicy.setAllowChunking(false);
            httpClientPolicy.setReceiveTimeout(30000);
            http.setClient(httpClientPolicy);
            clientMap.put(url, client);
        }
        return client;
    }

    /**
     * 个人权益单调用（入参xml，返回xml）
     */
    public static String sendRequest(String inputstr) {
        Client client = getClient(JXYDJYWS_WSDL_URL);
        String logPrefix = "";
        for(int i = 1; i <= 5; i++) {	//未接到正常返回值时，自动重新连接最多5次
            logPrefix = "========" + i + " time ";
            System.out.println(logPrefix + "call webservice url=" + JXYDJYWS_WSDL_URL + ", inputstr=" + inputstr);
            try {
                Object[] results = client.invoke(JXYDJYWS_METHODE, inputstr);
                if(null != results && results.length > 0) {	//接到返回值
                    System.out.println(logPrefix + "time call webservice url=" + JXYDJYWS_WSDL_URL + " result=" + results[0].toString());
                    return results[0].toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
