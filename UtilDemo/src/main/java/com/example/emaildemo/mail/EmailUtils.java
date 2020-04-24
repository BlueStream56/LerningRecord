package com.example.emaildemo.mail;

import sun.misc.BASE64Decoder;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <ul>
 * <li>文件名称：EmailUtils </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/3/30 </li>
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
public class EmailUtils {

    public static void main(String[] args) {
        String sendEmail = "cxx453663721@163.com";
        String secret = "LXXFEOHRKEKLKPID";
        String receiver = "13606083542@163.com";
        String aac003 = "";
        String bae047 = "";
        String aae537 = "";
        String content = "测试";
        sendEmailBy163(sendEmail,secret,receiver,aac003,bae047,aae537,content);
    }

    public static String sendEmailBy163(String sender,String secret,String receiver,String aac003,String bae047, String aae537,String content){
        Properties properties = new Properties();
        properties.put("mail.smtp.localhost","mail.digu.com");
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.163.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        session.setDebug(true);
        try {
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("sender",sender);
            map.put("secret",secret);
            map.put("receiver",receiver);
            map.put("aac003",aac003);
            map.put("bae047",bae047);
            map.put("aae537",aae537);
            map.put("content",content);
            // 获取邮件对象
            Message message = getMimeMessage(session,map);
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(sender, secret);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "success";
    }

    /**
     * 获得创建一封邮件的实例对象
     * @param session
     */
    private static MimeMessage getMimeMessage(Session session, Map<String,Object> map) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //设置发件人地址
        msg.setFrom(new InternetAddress(map.get("sender").toString()));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        InternetAddress[] toAddr = new InternetAddress[2];
        toAddr[0] = new InternetAddress("receiver1<"+map.get("receiver").toString()+">");
        toAddr[1] = new InternetAddress("receiver2<"+map.get("sender").toString()+">");
        msg.setRecipients(MimeMessage.RecipientType.TO,toAddr);
//        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress("receiver<"+map.get("receiver").toString()+">"));
//        msg.setRecipient(MimeMessage.RecipientType.CC,new InternetAddress("sender<"+map.get("sender").toString()+">"));
        //设置邮件主题
        String title = map.get("aac003")+"-异地就医备案申请-"+new SimpleDateFormat("yyyyMMdd").format(new Date()) +"-赣服通";
        msg.setSubject(title,"UTF-8");

        // 6. 创建文本"节点"
        MimeBodyPart text = null;
        if(null != map.get("content")){
            text = new MimeBodyPart();
//        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
            text.setContent(map.get("content"), "text/html;charset=UTF-8");
        }

        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
//        MimeMultipart mm_text_image = new MimeMultipart();
//        mm_text_image.addBodyPart(text);
//        mm_text_image.addBodyPart(image);
//        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
//        MimeBodyPart text_image = new MimeBodyPart();
//        text_image.setContent(mm_text_image);

        // 9. 创建附件"节点"
        MimeBodyPart attachment = null;
        if(null != map.get("bae047") && !"".equals(map.get("bae047").toString())){
            attachment = new MimeBodyPart();
            // 读取本地文件
            InputStream inputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(map.get("bae047").toString()));
            InputStreamDataSource dataSource = new InputStreamDataSource(inputStream);
            switch (map.get("aae537").toString().toUpperCase()){
                case "GIF":
                    dataSource.setContenttype("image/gif");
                    dataSource.setName(title+".gif");
                    break;
                case "JPG":
                    dataSource.setContenttype("image/jpeg");
                    dataSource.setName(title+".jpg");
                    break;
                case "PNG":
                    dataSource.setContenttype("image/png");
                    dataSource.setName(title+".png");
                    break;
                case "PDF":
                    dataSource.setContenttype("application/pdf");
                    dataSource.setName(title+".pdf");
                    break;
                default:
                    dataSource.setContenttype("*/*");
                    dataSource.setName(title);
            }
            DataHandler dh2 = new DataHandler(dataSource);
            // 将附件数据添加到"节点"
            attachment.setDataHandler(dh2);
            // 设置附件的文件名（需要编码）
            attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        }

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
//        mm.addBodyPart(text_image);
        if(null != map.get("content") && !"".equals(map.get("content").toString())){
            mm.addBodyPart(text);
        }
        if(null != map.get("bae047") && !"".equals(map.get("bae047").toString())){
            mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
            mm.setSubType("mixed");         // 混合关系
        }

        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }

}
