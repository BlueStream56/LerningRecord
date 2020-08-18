package com.example.emaildemo;

import java.util.regex.Pattern;

public class Demo {

    public static void main(String[] args) {
        String url = "https://API.eshimin.com/api/oauth/authorize?response_type=code&scope=read&client_id=iRwff9Dm0g&redirect_uri=https%3A%2F%2Fytyx.cross.echosite.cn%2Fh5%2Foauth%2Fredirect";
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-zA-Z_!~*'()-]+\\.)*" // 域名- www.
                + "[a-zA-Z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern pattern = Pattern.compile(regex);
        url = url.trim();
        System.out.println(pattern.matcher(url).matches());
        int index = url.indexOf("/",url.indexOf("//")+2);
        String baseUrl = url.substring(0,index<0?url.length():index);
        System.out.println(baseUrl);
    }

}
