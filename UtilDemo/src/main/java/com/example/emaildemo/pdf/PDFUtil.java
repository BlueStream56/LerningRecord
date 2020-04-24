package com.example.emaildemo.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.emaildemo.util.MapUtils;
import com.example.emaildemo.util.RSAUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.codec.binary.Base64;

/**
 * <ul>
 * <li>文件名称：PDFUtil </li>
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
public class PDFUtil {

    public Map queryInsuredCertificate(String aac002, String aac003, String aae140) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("AAC002",aac002);
        paramMap.put("AAC003",aac003);
        paramMap.put("AAE140",aae140);

        //首先向省集中系统（即机关事业养老系统）请求个人信息
        Map<String, Object> si0020Map = this.getPersonInfo(aac002,aac003);
        String returnSi0020Code = si0020Map.get("returnCode") != null ? si0020Map.get("returnCode").toString() : "";
        if (!"0000".equalsIgnoreCase(returnSi0020Code) || si0020Map == null || si0020Map.isEmpty()) {
            return si0020Map;
        }
        Map resultSi0020 = JSONObject.parseObject(si0020Map.get("resultData").toString());
        resultSi0020.put("aac002",paramMap.get("AAC002"));
        resultSi0020.put("aac003",paramMap.get("AAC003"));
        //查询到个人信息后再查下参保记录
        Map<String, Object> si0022Map = this.getPersonSIInfo(aac002,aac003, aae140);
        String returnSi0022Code = si0022Map.get("returnCode") != null ? si0022Map.get("returnCode").toString() : "";
        if (!"0000".equalsIgnoreCase(returnSi0022Code) || si0022Map == null || si0022Map.isEmpty()) {
            return si0022Map;
        }
        Map resultSi0022 = (Map) si0022Map.get("resultData");
        //向省集中系统（即机关事业养老系统）请求企业职工养老保险缴费信息，暂时只支持该险种
        Map<String, Object> si0053Map = this.getInsuredCertificate(aac002,aac003);
        Map resultSi0053 = new HashMap();
        String returnSi0053Code = si0053Map.get("returnCode") != null ? si0053Map.get("returnCode").toString() : "";
        if (!"0000".equalsIgnoreCase(returnSi0053Code) || si0053Map == null || si0053Map.isEmpty()) {
            resultSi0053.put("resultData",null);
        }else {
            resultSi0053.put("jsonlist",((Map)si0053Map.get("resultData")).get("jsonlist"));
        }

        //拼装PDF文件
        ByteArrayOutputStream bo = createPdf(resultSi0020, resultSi0022, resultSi0053);
        // 7.将PDF转为BASE64编码
        byte[] pdfbyte = bo.toByteArray();
        try {
            byte[] b1 = bo.toByteArray();
            FileOutputStream fos = new FileOutputStream(
                    new File("d:\\缴费证明\\缴费证明——" + System.currentTimeMillis() + ".pdf"));
            fos.write(b1);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer pdfdata = new StringBuffer();
        pdfdata.append(Base64.encodeBase64String(pdfbyte));
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("pdfData",pdfdata.toString());
        return resultData;
    }

    private Map<String, Object> getPersonInfo(String aac002, String aac003){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("aac002",aac002);
        paramMap.put("aac003",aac003);

        //省集中数据，参保地只能为江西省 360000
        String heardAAB301 = "360000";

        paramMap.put("ZZE269", "001");
//        Map result = siJGClient.getPersonInfoByJx(JSONArray.toJSONString(paramMap));
        String resultStr = RSAUtils.getJSONString("SI0020.json");
        Map result = JSONObject.parseObject(resultStr);
        String returnCode = result.get("returnCode") != null ? result.get("returnCode").toString() : "";
        Map resultData = result.get("resultData") != null ? (Map) result.get("resultData") : null;
        if ("0000".equalsIgnoreCase(returnCode) && resultData != null && !resultData.isEmpty()) {
            return result;
        }
        return result;
    }

    private Map<String, Object> getPersonSIInfo(String aac002, String aac003, String aae140){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("aac002",aac002);
        paramMap.put("aac003",aac003);

        //省集中数据，参保地只能为江西省 360000
        String heardAAB301 = "360000";

        //3、查询省集中数据，即机关数据库
        //更新：系统编号
        paramMap.put("ZZE269", "001");
        //更新：参保地信息
        paramMap.put("AAB301", heardAAB301);
//        Map<String, Object> rsMap = siJGClient.getPersonSIInfoByJx(JSONArray.toJSONString(paramMap));
        String resultStr = RSAUtils.getJSONString("SI0022.json");
        Map rsMap = JSONObject.parseObject(resultStr);
        String curReturnCode = rsMap.get("returnCode") != null ? rsMap.get("returnCode").toString() : "";
        Map<String, Object> resultData = rsMap.get("resultData") != null ? (Map) rsMap.get("resultData") : null;
        List<Map> rsList = new ArrayList<Map>();
        if ("0000".equalsIgnoreCase(curReturnCode) && resultData != null && !resultData.isEmpty()) {
            List<Map> jsonlist = (List<Map>) resultData.get("jsonlist");
            for (Map map: jsonlist) {
                map = MapUtils.changeKeyToUpperCase(map);
                if (aae140.indexOf(map.get("AAE140").toString()) >= 0){
                    rsList.add(map);
                }
            }
            Map<String, Object> stringObjectMap = new HashMap<String, Object>();
            String returnMsg = rsMap.get("returnMsg")!=null?rsMap.get("returnMsg").toString():"";
            if(rsList==null||rsList.size()==0){
                stringObjectMap.put("returnCode", "500");
                stringObjectMap.put("returnMsg", "未查询对应的参保记录");
                return stringObjectMap;
            }
            resultData = new HashMap();
            resultData.put("jsonlist", rsList);
            stringObjectMap.put("resultData", resultData);
            stringObjectMap.put("returnCode", curReturnCode);
            stringObjectMap.put("returnMsg", returnMsg);
            return stringObjectMap;
        }
        return rsMap;
    }

    private Map<String, Object> getInsuredCertificate(String aac002, String aac003){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("aac002",aac002);
        paramMap.put("aac003",aac003);
        paramMap.put("aae140","110");//暂时只允许企业职工缴费查询
        //省集中数据，参保地只能为江西省 360000
        String heardAAB301 = "360000";

        //更新：参保地信息
        paramMap.put("AAB301", heardAAB301);
        paramMap.put("ZZE269", "001");
//        Map result = siJGClient.getInsuredCertificate(JSONArray.toJSONString(paramMap));
        String resultStr = RSAUtils.getJSONString("SI0053.json");
        Map result = JSONObject.parseObject(resultStr);
        String returnCode = result.get("returnCode") != null ? result.get("returnCode").toString() : "";
        Map resultData = result.get("resultData") != null ? (Map) result.get("resultData") : null;
        if ("0000".equalsIgnoreCase(returnCode) && resultData != null && !resultData.isEmpty()) {
            return result;
        }
        return result;
    }

    private ByteArrayOutputStream createPdf(Map<String, Object> si0020Map, Map<String, Object> si0022Map, Map<String, Object> si0053Map) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 20, 20, 10, 10);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, ba);
            // 设置字体
//			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "SIMSUN.TCC", false);
            BaseFont bfChinese = BaseFont.createFont("simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // 设置页脚
//            HeaderFooter footer = new HeaderFooter(new Phrase("第", fontChinese2), new Phrase(" 页", fontChinese4));
//            footer.setBorder(Rectangle.NO_BORDER);
//            footer.setAlignment(Element.ALIGN_CENTER);
//            document.setFooter(footer);
            document.open();

            Map<String, Object> si0020MapCov = si0020Map;
            List<Map<String, Object>> si0022List = (List<Map<String, Object>>) si0022Map.get("jsonlist");
            List<Map<String, Object>> si0053List = (List<Map<String, Object>>) si0053Map.get("jsonlist");
            this.createPdfJgAndQy(document, si0020MapCov,si0022List,si0053List,bfChinese);

            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return ba;
    }

    private void createPdfJgAndQy(Document document, Map<String, Object> si0020Map, List<Map<String, Object>> si0022List, List<Map<String, Object>> si0053List,
                                  BaseFont bfChinese) throws DocumentException, IOException {
        Font fontChinese1 = new Font(bfChinese, 16, Font.BOLD);
        Font fontChinese2 = new Font(bfChinese, 12, Font.BOLD);
        Font fontChinese3 = new Font(bfChinese, 10, Font.BOLD);
        Font fontChinese4 = new Font(bfChinese, 10, Font.NORMAL);
        Font fontSpace = new Font(bfChinese, 6, Font.NORMAL);
        // 正文
        // 表头1
//        Paragraph chapter2 = new Paragraph("江西省社会保险个人参保缴费证明", fontChinese1);
//        chapter2.setAlignment(Paragraph.ALIGN_CENTER);
//
//        document.add(chapter2);
        // 空一行
        Paragraph space = new Paragraph("\n", fontSpace);
        space.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(space);
        // 表格打印开始
        // 设置列宽
        float[] widths = { 0.12f, 0.08f, 0.02f, 0.08f, 0.04f, 0.04f, 0.08f, 0.08f, 0.04f, 0.22f };
        PdfPTable tab = new PdfPTable(widths);
        tab.setWidthPercentage(100);// 默认表格宽度80%
        //设置表格标题
        this.setPdfCellValue(tab,"江西省社会保险个人参保缴费证明", fontChinese1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 10, 0);
        // 个人基本信息
        si0020Map = MapUtils.changeKeyToUpperCase(si0020Map);
        this.setPdfCellValue(tab,"姓名", fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, null, null);
        this.setPdfCellValue(tab,si0020Map.get("AAC003").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, null, null);
        this.setPdfCellValue(tab,"性别", fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,si0020Map.get("AAC004").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"身份证号", fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,si0020Map.get("AAC002").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        // 当前参保情况
        this.setPdfCellValue(tab,"参加养老保险基本情况", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 10, null);
        // 个人社保编号 险种名称 参保状态 参保地 参保单位名称
        this.setPdfCellValue(tab,"个人社保编号", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 0, null);
        this.setPdfCellValue(tab,"险种名称", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"参保状态", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"参保地", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 3, null);
        this.setPdfCellValue(tab,"参保单位名称", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        // 当前参保情况明细
        if (si0022List != null && si0022List.size() > 0) {
            int count = 0;//限制最多显示两条
            for (Map<String, Object> si0022Map : si0022List) {
                si0022Map = MapUtils.changeKeyToUpperCase(si0022Map);
                if(count == 0){
                    this.setPdfCellValue(tab,si0022Map.get("AAC999").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 0, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAE140MC").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAC008").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAA129").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 3, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAB069").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                    count ++;
                }else{
                    this.setPdfCellValue(tab,si0022Map.get("AAC999").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 0, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAE140MC").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 2, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAC008").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 2, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAA129").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 3, null);
                    this.setPdfCellValue(tab,si0022Map.get("AAB069").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 2, null);
                    count ++;
                    break;
                }
            }
            if(count == 1){
                //默认添加空白行
                this.setNullPdfCell(tab, 30f, 1, 0);
                this.setNullPdfCell(tab, 30f, 2, 2);
                this.setNullPdfCell(tab, 30f, 1, 3);
                this.setNullPdfCell(tab, 30f, 1, 2);
            }
        } else {
            this.setNullPdfCell(tab, 30f, 1, 0);
            this.setNullPdfCell(tab, 30f, 2, 2);
            this.setNullPdfCell(tab, 30f, 1, 3);
            this.setNullPdfCell(tab, 30f, 1, 2);
        }
        // 基本养老保险个人账户本年缴费明细
        this.setPdfCellValue(tab,"基本养老保险个人账户本年缴费明细", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 60f, 10, null);
        // 个人社保编号 开始年月 终止年月 月缴费基数 是否到账 缴费单位名称
        this.setPdfCellValue(tab,"个人社保编号", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 0, null);
        this.setPdfCellValue(tab,"开始年月", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"终止年月", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"月缴费基数", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"是否到账", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
        this.setPdfCellValue(tab,"缴费单位名称", fontChinese2, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 0, null);
        // 基本养老保险个人账户本年缴费明细记录
        int maxNum = 18;//设置最大行数
        if (si0053List != null && si0053List.size() > 0) {
            int count = 0;
            for (Map<String, Object> si0053Map : si0053List) {
                si0053Map = MapUtils.changeKeyToUpperCase(si0053Map);
                this.setPdfCellValue(tab,si0053Map.get("AAC999").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 0, null);
                this.setPdfCellValue(tab,si0053Map.get("AAE030").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                this.setPdfCellValue(tab,si0053Map.get("AAE031").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                this.setPdfCellValue(tab,si0053Map.get("AAE180").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                this.setPdfCellValue(tab,si0053Map.get("AAE078").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                this.setPdfCellValue(tab,si0053Map.get("AAB069").toString(), fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 20f, 2, null);
                if(++count >= maxNum){
                    break;
                }
            }
            //显示的缴费明细必须是18行如果少于18条那么用空行补充
            if (count < maxNum){
                for (int i = 0; i < maxNum-count; i++) {
                    this.setNullPdfCell(tab, 20f, 1, 0);
                    this.setNullPdfCell(tab, 20f, 5, 2);
                }
            }
        } else {
            for (int i = 0; i < maxNum; i++) {
                this.setNullPdfCell(tab, 20f, 1, 0);
                this.setNullPdfCell(tab, 20f, 5, 2);
            }
        }

        document.add(tab);
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        // 打印时间
        Paragraph dysj00 = new Paragraph(
                "社会保险经办机构（章）",
                fontChinese4);
        dysj00.setAlignment(Paragraph.ALIGN_RIGHT);
        dysj00.setIndentationLeft(PageSize.A4.getWidth() * 0.06f);
        dysj00.setIndentationRight(PageSize.A4.getWidth() * 0.2f);
        document.add(dysj00);
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        document.add(space);// 空一行
        Paragraph pa4 = new Paragraph(
                "1、各级社保经办机构对各自提供的证明材料内容负责。",
                fontChinese4);
        pa4.setIndentationLeft(PageSize.A4.getWidth() * 0.01f);
        pa4.setIndentationRight(PageSize.A4.getWidth() * 0.06f);
        document.add(pa4);
    }


    private void setPdfCellValue(PdfPTable pdfPTable, String text, Font font, Integer horizontalalignment, Integer verticalAlignment, Float height, Integer colspan, Integer rowspan) throws IOException {
        PdfPCell pdfPCell = new PdfPCell(new Paragraph(text, font));
        pdfPCell.setHorizontalAlignment(horizontalalignment);
        pdfPCell.setVerticalAlignment(verticalAlignment);
        pdfPCell.setMinimumHeight(height);
        if (colspan != null && colspan > 0){
            pdfPCell.setColspan(colspan);
        }
        if (rowspan != null && rowspan > 0){
            pdfPCell.setRowspan(rowspan);
        }
        pdfPTable.addCell(pdfPCell);
    }

    private void setNullPdfCell(PdfPTable pdfPTable, Float height, int num, int colspan){
        if (num > 0){
            PdfPCell pdfPCell = new PdfPCell(new Paragraph(""));
            pdfPCell.setMinimumHeight(height);
            if (colspan > 0){
                pdfPCell.setColspan(colspan);
            }
            for (int i = 0; i < num; i++) {
                pdfPTable.addCell(pdfPCell);
            }
        }
    }

}
