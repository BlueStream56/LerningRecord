/**
 * @(#)Config.java 2018年4月26日
 *
 * Copyright (c) 1995-2100 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */
package com.example.emaildemo.util.ecard;


/**
 * API 配置
 * 以下参数可以根据放在配置文件
 * @author xiabobin
 * 2018年4月26日
 */
public abstract class Config {
	/**
	 * Api网关地址
	 * 来源:
	 *    电子社保卡平台提供
	 */
//	/正式环境
	public static final String SERVER_URL = "http://ydwywx.nat100.top/ecard-api-gateway/gateway.do";
	/**
	 * 签名私钥
	 * 来源：
	 *    电子社保卡平台提供
	 */
	public static final String SIGN_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDU/oh8lFbB/MJqghIwYDNAAudysCHhmjiclT/uM0/rC8sBO4hvWLwQaqKGCwX/t14jY0mllxtiugQ2GT2ilqmdAAy/8vKQFEJLd47nk2B7Dh6vPxvqKZPDvlCxl5Hga1Gpwej5+b6gPjiiVk5gvIbvClkXn43R4KTSY5EjrfmGw9B0W1zT5pGRFrD1pn5rHhb7rx4vAoQ1r21iYE9HxTjtgL0Lr1bmFxPKE+9ndUe+w3Gcvjj9K/kdvT4qbhO/SyFIlyfuOOjiTUxP9OVPi+7uiskY0lLWxFgICm3DwYQY4G+Y9Z9RSps2ET5VtmHyJ/oGsjYN7IWLV2/DGzF7i9OXAgMBAAECggEAGEIE+WtLljxbSBYYTDoonBm4km9M7IzL9clpxo9BgtuM+070cvoAMT5z1CBdRopzxkQAl0NmcxAMerzVMDdIigD/cr0QsygbhMzREb7PNjSdeggN/dVOZvS96/v/OTf/zP2wswemlkl/2b1Kn+nY1FCxZOdfF5AmNW/xj8a4rIhbW1JcrrZg8YZr97+adkDPFoayOM9YtGP41Vln1V7//zn9zrbN8EX8QLedX2U6ZsMQco7zmr6eU2uOnQ/x3RT3+0c38GypTD1HgkKZOWR9/8ztfn6WomasW0iilvdVeTLJE2cYDIFZZKU/hTarrpopkjeJOPUHyg/5c49Ub5spgQKBgQD6fem/5O8/VAx+XOgDCL0WcbkpEJTJ2KANImIwk40J/O6UNQ0tp8+kbAJ6uFHcQvKMkPZT7MlxPVjhyOAHkd01p5hdSBTthMSBbTzl/EJLQ6qy2k9SNTWasSUGVEaJ/WcSkmckWS+C7R9dbXYWOVdn3AaE/OSDateNGouSoKUlXwKBgQDZrYk1unQteW0P0AwOhwi94u/lOLc1+35SfEfAW6IvjKCHegIJLAj+o+dEJvRwSSRVn/f5lai8fQAkHHWqDNFZGXonZyYeLmOfRj9sZKbzXaxSN4MZ1yedK19NqDY6kbl9vUpQlzNQDbl8rDkhj3TrvW2QH6IvtKzDiCgtkjQEyQKBgQCt+fH1073PkEK1KTYDlXgvNIFT4i5VGt3fAXiNII4e18ukqEwk3566JkJ6lbZVczIfKzfLBnTK777h4rCm0UqAz4xljYDrjwEkm4hmfBRXTgIrArGA+W/npw1+Hd54TMXgAksc1czH9IWKUFJqER2CBzGIAFV+aSCY4kz6ZBUPpQKBgQCZJ8dVnUWmxTueF5SUGCBncie2EOyfN78OjFDzn8WCTSCZkZejGm+NsobCZeIwqtW8xRv1aH5beFvEDJ4hMnmNhiZzWNjx/VOsugFf44Wvz6WyB0rfwIknxV99xS+6j9OCC9Ag2fGJjs6GUKq12Ti7sQFImWlExYuU29ThE3A0OQKBgHEbzW7WaPfiVGft8Inx8loPsmik39873VoVt+eYpdAGgJaeSAlMpZQbRO+HfppLrXyK+cnbMFjPP0C5Uz25RRTA1vp27DkUrzcUO6nyd4ELbwIUg4CihfV2bM9KJXVhnJPswLvPATdXDXfKmZXd78WQAa4ruSGqRg37Ls4p3gh3";
	/**
	 * 验签公钥
	 * 来源：
	 *    电子社保卡平台提供
	 */
	public static final String SIGN_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyDg+H0kPVaUzZlRbF2aXOwa4iJkP3CTHtUDsk5NTf0EO6UIRXbaoT+9/E0m/ile4dfOCfZP5izYp52mj680JvI+csm8UR6yrBCNrrs2KhOCvl+XERw0V0/kapllbhHCYfw776h0jbFZnYA4rxPOw90bSzV+8uHi5yeMCPIUdfyNEaXVhlCcpPrcayLqRHFeyOzUHEGoZ072Gl3r5G1jW5/YLVCtubtMfxVRHUhWC0T9uZOWTrA3LySqfstknHVTBzeMdDvX1lvJYibHrTbQQN7UcgHW+hIi3cSD1JaTkEFmGAZxW2PXGYENSqaAcQoDNfWbfrAi+yovUN7GIQdAykwIDAQAB";
	/**
	 * 接入应用ID
	 * 来源：
	 *    电子社保卡平台提供
	 */
	public static final String APP_ID = "20200811100734000521";
	/**
	 * 证书密码
	 */
	public static final String CER_CODE="123456";
	/**
	 * JWT 密钥
	 * 来源：
	 *    电子社保卡平台提供
	 */
	public static final String JWT_KEY = "";
}
