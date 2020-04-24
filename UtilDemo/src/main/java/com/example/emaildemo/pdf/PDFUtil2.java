package com.example.emaildemo.pdf;

import com.alibaba.fastjson.JSONObject;
import com.example.emaildemo.util.MapUtils;
import com.example.emaildemo.util.RSAUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * <ul>
 * <li>文件名称：PDFUtil2 </li>
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
public class PDFUtil2 {

    public Map getNoticeOfReceipt(String aac002, String aac003, String contriNoticeId) {
//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("aac002",aac002);
//        paramMap.put("aac003",aac003);
//        paramMap.put("contriNoticeId",contriNoticeId);

        //首先向省集中系统（即机关事业养老系统）获取征集单对应的缴费到账单信息
//        Map<String, Object> si0020Map = this.getPersonInfo(aac002,aac003);
//        String returnSi0020Code = si0020Map.get("returnCode") != null ? si0020Map.get("returnCode").toString() : "";
//        if (!"0000".equalsIgnoreCase(returnSi0020Code) || si0020Map == null || si0020Map.isEmpty()) {
//            return si0020Map;
//        }
        String resultStr = RSAUtils.getJSONString("SI0062.json");
        Map result = JSONObject.parseObject(resultStr);
        //拼装PDF文件
        ByteArrayOutputStream bo = createPdf((Map<String, Object>) result.get("result_data"));
        // 7.将PDF转为BASE64编码
        byte[] pdfbyte = bo.toByteArray();
        try {
            byte[] b1 = bo.toByteArray();
            FileOutputStream fos = new FileOutputStream(
                    new File("d:\\缴费证明\\到账单——" + System.currentTimeMillis() + ".pdf"));
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

    private ByteArrayOutputStream createPdf(Map<String, Object> resultMap) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();

        Document document = new Document(new Rectangle(842, 595), 150, 150, 150, 150);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, ba);
            // 设置字体
            BaseFont bfChinese = BaseFont.createFont("simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            document.open();

            this.createPdfNoticeOfReceipt(document, resultMap,bfChinese);

            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        return ba;
    }

    private void setPdfCellValue(PdfPTable pdfPTable, String text, Font font, Integer horizontalalignment, Integer verticalAlignment, Float height, Integer colspan, Integer rowspan, Integer hideBoder, Integer direction) throws IOException {
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
        if (hideBoder != null && hideBoder > 0){
            pdfPCell.disableBorderSide(hideBoder);
        }
        if(direction != null){
            pdfPCell.setRunDirection(direction);
        }
        pdfPTable.addCell(pdfPCell);
    }

    private void createPdfNoticeOfReceipt(Document document, Map resultParam, BaseFont bfChinese) throws DocumentException, IOException {
        resultParam = MapUtils.changeKeyToLowerCase(resultParam);
        Font fontChinese1 = new Font(bfChinese, 16, Font.BOLD);
        Font fontChinese3 = new Font(bfChinese, 10, Font.BOLD);
        Font fontChinese4 = new Font(bfChinese, 10, Font.NORMAL);
        Font fontChinese5 = new Font(bfChinese, 8, Font.NORMAL);
        Font fontSpace = new Font(bfChinese, 6, Font.NORMAL);

        // 正文
        // 表头1
        Paragraph chapter2 = new Paragraph("江西省社会保险费到账通知单", fontChinese1);
        chapter2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(chapter2);
        // 空一行
        Paragraph space = new Paragraph("\n", fontSpace);
        space.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(space);

        Paragraph chapter3 = new Paragraph(new SimpleDateFormat("yyyy年MM月dd日       ").format(new Date())+"  NO "+resultParam.get("aae281"), fontChinese4);
        chapter3.setIndentationLeft(PageSize.A4.getWidth() * 0.40f);
        chapter3.setIndentationRight(PageSize.A4.getWidth() * 0.06f);
        document.add(chapter3);
        document.add(space);

        // 表格打印开始
        // 设置列宽
        float[] widths = { 0.04f, 0.12f, 0.01f, 0.15f, 0.17f, 0.04f, 0.16f, 0.04f, 0.30f,0.03f,0.03f };
        PdfPTable tab = new PdfPTable(widths);
        tab.setWidthPercentage(80);// 默认表格宽度80%

        this.setPdfCellValue(tab,"收款人",fontChinese3,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f,null,4, null, null);
        this.setPdfCellValue(tab,"全称",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2,null, null, null);
        this.setPdfCellValue(tab, resultParam.get("aae009")!=null?resultParam.get("aae009").toString():"",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2,null, null, null);
        this.setPdfCellValue(tab,"缴费单位或个人",fontChinese3,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,100f,null,4, null, PdfWriter.RUN_DIRECTION_DEFAULT);
        this.setPdfCellValue(tab,"单位名称",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aab069")!=null?resultParam.get("aab069").toString():"",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2, null, null, null);
        this.setPdfCellValue(tab, "此单据作为参保单位或个人缴费到账凭证",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,null, 8, 15, null);
        this.setPdfCellValue(tab, "打印黑白章和红章有同等法律效力",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,null, 8, 15, null);

        this.setPdfCellValue(tab,"账户",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aae010")!=null?resultParam.get("aae010").toString():"",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f, 2, null, null, null);
        this.setPdfCellValue(tab,"单位编号",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aab999")!=null?resultParam.get("aab999").toString():"",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2, null, null, null);

        this.setPdfCellValue(tab,"开户银行",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2,2, null, null);
        this.setPdfCellValue(tab,resultParam.get("bab007")!=null?resultParam.get("bab007").toString():"",fontChinese5,Element.ALIGN_LEFT,Element.ALIGN_TOP,23f,2, null, 2, PdfWriter.RUN_DIRECTION_LTR);
        this.setPdfCellValue(tab,"姓名",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aac003")!=null?resultParam.get("aac003").toString():"",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2, null, null, null);

        this.setPdfCellValue(tab,"",fontChinese3,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, 9, null);
        this.setPdfCellValue(tab,"",fontChinese3,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, 5, null);
        this.setPdfCellValue(tab,"身份证号码",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aac002")!=null?resultParam.get("aac002").toString():"",fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,2, null, null, null);

        this.setPdfCellValue(tab,"金额",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f, null, null, null, null);
        this.setPdfCellValue(tab,"人民币\r(大写)",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f,2, null, 8, null);
        this.setPdfCellValue(tab, MoneyToCN2.toCN(resultParam.get("aae019")!=null?resultParam.get("aae019").toString():"0"),fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f,5, null, 4, null);
        this.setPdfCellValue(tab,"￥："+(resultParam.get("aae019")!=null?resultParam.get("aae019").toString():"0"),fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f, null, null, null, null);

        this.setPdfCellValue(tab,"缴费通知单号",fontChinese5,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f,2, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aae281")!=null?resultParam.get("aae281").toString():"",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f,4, null, null, null);
        this.setPdfCellValue(tab,"费款所属期",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,23f,2, null, null, null);
        String aae030 = resultParam.get("aae030").toString().substring(0,4)+"年"+resultParam.get("aae030").toString().substring(4)+"月";
        String aae031 = resultParam.get("aae031").toString().substring(0,4)+"年"+resultParam.get("aae031").toString().substring(4)+"月";
        this.setPdfCellValue(tab,aae030+" 至"+aae031,fontChinese4,Element.ALIGN_LEFT,Element.ALIGN_MIDDLE,23f, null, null, null, null);

        this.setPdfCellValue(tab,"款项内容",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,30f,4, null, null, null);
        this.setPdfCellValue(tab,"本金",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,30f,2, null, null, null);
        this.setPdfCellValue(tab,"滞纳金",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_MIDDLE,30f,2, null, null, null);
        this.setPdfCellValue(tab, "利息", fontChinese4, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 30f, 0, 0, null, null);

        this.setPdfCellValue(tab,"养老",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_TOP,30f,4, null, null, null);
        this.setPdfCellValue(tab,resultParam.get("aae019")!=null?resultParam.get("aae019").toString():"0",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_TOP,30f,2, null, null, null);
        this.setPdfCellValue(tab,"0",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_TOP,30f,2, null, null, null);
        this.setPdfCellValue(tab,"0",fontChinese4,Element.ALIGN_CENTER,Element.ALIGN_TOP,30f, null, null, null, null);

        document.add(tab);

        Paragraph gzc000 = new Paragraph("盖    章    处", fontChinese4);
        gzc000.setIndentationLeft(PageSize.A4.getWidth() * 0.10f);
        gzc000.setIndentationRight(PageSize.A4.getWidth() * 0.06f);
        document.add(gzc000);

        Paragraph bsc000 = new Paragraph(resultParam.get("aae009")!=null?resultParam.get("aae009").toString():"", fontChinese5);
        bsc000.setIndentationLeft(PageSize.A4.getWidth() * 0.55f);
        bsc000.setIndentationRight(PageSize.A4.getWidth() * 0.06f);
        document.add(bsc000);
    }

}
