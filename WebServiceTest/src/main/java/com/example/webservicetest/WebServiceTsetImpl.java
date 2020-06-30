package com.example.webservicetest;

import javax.jws.WebService;
import java.util.Date;

@WebService(name = WebServiceContrant.WEBSERVICE_NAME)
public class WebServiceTsetImpl implements WebServiceTset{
    @Override
    public String sendMsg(String inputXml) {
        return inputXml+", 现在时间为：" + new Date();
    }
}
