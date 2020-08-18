package com.example.emaildemo.util.escard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.emaildemo.util.escard.dto.Mspp9010001InDTO;
import com.example.emaildemo.util.escard.dto.ReqInDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class RSADemo {

    public static void main(String[] args) throws Exception {
        String reqStr = getReqStr();
        System.out.println("部平台请求，加密报文："+reqStr);
        JSONObject map = JSONObject.parseObject(reqStr);
        String sign = map.get("sign").toString().replace("/[\r\n]/g","");
        String encryptData = map.get("encrypt").toString().replace("/[\r\n]/g","");;
        String decryptData = JSONObject.toJSONString(JSONObject.parse(AESUtils.decrypt(encryptData, null	)));
        map.put("encrypt", decryptData);
        map.remove("sign");
        String signOrg = VerifySignUtil.build(JSON.parseObject(decryptData, Map.class));
        String pubKey = VerifySignUtil.getPublicKeyFromCer("D:\\huahua_user_public.cer");
        byte[] myDataByte = signOrg.getBytes();
//        byte[] myDataByte = decryptData.getBytes();
        boolean verifySign = VerifySignUtil.verifySign(myDataByte,pubKey,sign);
//        boolean verifySign = Sha1withRSAUtil.verify(sign, decryptData);
        if (!verifySign) {
            System.out.println("验签失败");
        }
        map.put("sign", sign);
        System.out.println("部平台请求，解密报文："+JSON.toJSONString(map));
    }

    private static String getReqStr(){
        ReqInDTO reqInDTO = new ReqInDTO();
        reqInDTO.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        reqInDTO.setReqSnum(System.currentTimeMillis()+"01");
        Mspp9010001InDTO mspp9010001InDTO = new Mspp9010001InDTO();
        mspp9010001InDTO.setUniformOrderId("UniformOrderId");
        mspp9010001InDTO.setAkb020("akb020");
        mspp9010001InDTO.setEsscNo("EsscNo");
        mspp9010001InDTO.setAac002("Aac002");
        mspp9010001InDTO.setPreBalanceId("PreBalanceId");
        mspp9010001InDTO.setAkc264(120.0);
        mspp9010001InDTO.setAke149(0.0);
        mspp9010001InDTO.setAke039(0.0);
        mspp9010001InDTO.setAke173(0.0);
        mspp9010001InDTO.setAkb066(0.0);
        mspp9010001InDTO.setAke201(0.0);
        mspp9010001InDTO.setMerchantReserve(null);
        String mspp9010001InDTOStr = VerifySignUtil.build(JSONObject.parseObject(JSONObject.toJSONString(mspp9010001InDTO, SerializerFeature.PrettyFormat), Map.class));
        System.out.println(mspp9010001InDTOStr);
        reqInDTO.setEncrypt(mspp9010001InDTOStr);
        String signData = JSONObject.toJSONString(reqInDTO, true).replace("/[\r\n]/g","");
        //签名
        String sign = Sha1withRSAUtil.sign(mspp9010001InDTOStr).replace("/[\r\n]/g","");
        reqInDTO.setSign(sign);
        reqInDTO.setEncrypt(AESUtils.encrypt(JSONObject.toJSONString(mspp9010001InDTO, SerializerFeature.PrettyFormat), AESUtils.KEY));
        return JSONObject.toJSONString(reqInDTO).replace("/[\r\n]/g","");
    }

}
