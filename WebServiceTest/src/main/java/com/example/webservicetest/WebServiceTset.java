package com.example.webservicetest;

import javax.jws.WebService;

@WebService(name = WebServiceContrant.WEBSERVICE_NAME)
public interface WebServiceTset {

    public String sendMsg(String inputXml);

}
