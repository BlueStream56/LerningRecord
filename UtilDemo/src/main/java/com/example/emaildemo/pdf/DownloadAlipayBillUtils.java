package com.example.emaildemo.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Base64;

/**
 * <ul>
 * <li>文件名称: DownloadAlipayBillUtils</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/8/20</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author xieyy
 * @version 1.0.0
 */

public class DownloadAlipayBillUtils {

	private static Logger billlog = LoggerFactory.getLogger(DownloadAlipayBillUtils.class);
	private static Logger logger = LoggerFactory.getLogger(DownloadAlipayBillUtils.class);
 
	/**
	 * 从网络Url中下载文件
	 *
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static void downLoadFromUrl(String urlStr, String fileName, String savePath) {
		InputStream inputStream = null;
		FileOutputStream fos = null;
		ByteArrayOutputStream bos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
            conn.setReadTimeout(60 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] buffer = new byte[1024];
			int len;
			bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			byte[] getData = bos.toByteArray();
			// 文件保存位置
			File saveDir = new File(savePath);
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);

			logger.info("******************下载" + fileName + "完毕******************");
		} catch (SocketTimeoutException e) {
			logger.error("连接超时", e);
		} catch (IOException e) {
            throw new RuntimeException(e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] downLoadFromUrl(String urlStr) {
		InputStream inputStream = null;
		FileOutputStream fos = null;
		ByteArrayOutputStream bos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(3 * 1000);
			conn.setReadTimeout(60 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			// 得到输入流
			inputStream = conn.getInputStream();
			// 获取自己数组
			byte[] buffer = new byte[1024];
			int len;
			bos = new ByteArrayOutputStream();
			while ((len = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			byte[] getData = bos.toByteArray();
			return getData;
		} catch (SocketTimeoutException e) {
			logger.error("连接超时", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
//		downLoadFromUrl("http://v3rbrp.natappfree.cc/pdf/show?clientId=202004101114&t=1587020492148&appKey=null&token=256ec90b2e1cd8b5e60004611c2f8fd0&fileName=30a4ca93-700b-4f0f-81e9-4ed66dd7cd79&logId=56f43e6523e84ca9a5da13afb97dcfd3", "show.pdf", "d:/");
		byte[] bytes = downLoadFromUrl("http://v3rbrp.natappfree.cc/pdf/show?clientId=202004101114&t=1587020492148&appKey=null&token=256ec90b2e1cd8b5e60004611c2f8fd0&fileName=30a4ca93-700b-4f0f-81e9-4ed66dd7cd79&logId=56f43e6523e84ca9a5da13afb97dcfd3");
		String base64PDF = Base64.getEncoder().encodeToString(bytes);
		System.out.println(base64PDF);
	}

}
