package com.example.emaildemo.pdf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.emaildemo.util.RSAUtils;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称：PDFDemo </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/7 </li>
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
public class PDFDemo {

    public static void main(String[] args) {
//        PDFUtil pdfUtil = new PDFUtil();
//        Map map = pdfUtil.queryInsuredCertificate("360122199610012188", "夏姝", "110");
//        System.out.println(JSON.toJSONString(map));
//        PDFUtil2 pdfUtil = new PDFUtil2();
//        pdfUtil.getNoticeOfReceipt(null,null,null);

        String resultStr = RSAUtils.getJSONString("SI0061.json");
        Map result = JSONObject.parseObject(resultStr);
//        System.out.println(new String(Base64.decode(((Map)result.get("resultData")).get("pdfData").toString())));
        base64ConTo(((Map)result.get("resultData")).get("pdfData").toString());

//        String aaa229 = "65000020200306201217";
//        String aaa229Max = "65000020200306201216";
//        double subVale = BigDecimal.valueOf(Double.parseDouble(aaa229)).subtract(BigDecimal.valueOf(Double.parseDouble(aaa229Max))).doubleValue();
//        System.out.println(subVale);
//        System.out.println(Double.parseDouble(aaa229));
//        System.out.println(Double.parseDouble(aaa229Max));
    }

    public static void base64ConTo(String base64Str){
        byte[] bContent = Base64.decode(base64Str);
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File("d:\\缴费证明\\到账单——" + System.currentTimeMillis() + ".pdf"));
            fos.write(bContent);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
