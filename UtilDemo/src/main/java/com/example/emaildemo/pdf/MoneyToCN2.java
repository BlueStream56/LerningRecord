package com.example.emaildemo.pdf;

/**
 * <ul>
 * <li>文件名称：MoneyToCN2 </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/14 </li>
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
public class MoneyToCN2 {

    private static final String CNSUM[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
    private static final String SMALLSUM[] = {"分","角"};
    private static final String MIDSUM[] = {"","拾","佰","仟"};
    private static final String BIGSUM[] = {"","万","亿"};

    private static final String YUAN = "圆";
    private static final String YUANZHENG = "圆整";
    private static final String LINGYUANZHENG = "零圆整";

    /**
     * 默认精确到小数点后两位
     */
    private static final int PRECISION = 2;

    /**
     * 阿拉伯数字类型的金额 转换为 汉字大写金额类型
     * @param strMoney 输入后经过四舍五入的金额（保留两位小数，保留位数可更改）
     * @return 汉字大写金额
     */
    public static String toCN(String strMoney){
        //用于接收转换了的汉字大写金额
        StringBuffer sb = new StringBuffer();

        String str[] = strMoney.split("\\.");  //根据小数点将金额分成两部分
        String strLeft = str[0];               //金额的整数部分
        String strRight = str[1];              //金额的小数部分

        /**
         * 小数部分是否为零的情况
         */
        if(Integer.parseInt(strRight)!=0){
            //小数部分不为零,先读小数部分的角、分情况

            int fen = Integer.parseInt(strRight.length()>1?strRight.substring(1):"0");
            int jiao = Integer.parseInt(strRight.substring(0,1));
            if(fen != 0){
                sb.insert(0,  SMALLSUM[0]);
                sb.insert(0, toUpperNumber(fen));
            }
            if(jiao != 0){
                sb.insert(0,  SMALLSUM[1]);
                sb.insert(0, toUpperNumber(jiao));
            }

            //如果整数部分不为零，先将“圆”添加上去
            if(Long.parseLong(strLeft)!=0){
                sb.insert(0, YUAN);
            }

        }else{
            //小数部分为零,小数后面的部分忽略不读，即均为   “圆整” 情况
            sb.append(YUANZHENG);
            //如果整数部分也为零，那就是“零圆整”  0.00
            if(Long.parseLong(strLeft)==0){
                return LINGYUANZHENG;
            }
        }

        int leftLength = strLeft.length();    //整数部分的位数

        if(leftLength <= 4){
            //金额不超过上千的话

            MidToUpper(strLeft, sb);
            return sb.toString();

        }else{
            //金额上万起步的话

            //将整数部分的金额拆成4个长度为一组的数，第一组单位“”，第二组单位“万”，第三组单位“亿”
            //到底要分成几组？fenzu<=3
            int fenzu = 0;
            if(leftLength%4!=0){
                fenzu = leftLength/4+1;
            }else{
                fenzu = leftLength/4;
            }

            //整数部分拆分出来的小组都装进leftMoney数组中
            String leftMoney[] = new String[fenzu];

            //将左边的整数拆分成最多四位的数组
            for(int i=0; i<fenzu; i++){
                int indexBegin = leftLength-4*(i+1);    //开始切割的点
                //从后往前拆分
                if(indexBegin>0){
                    leftMoney[i] = strLeft.substring(indexBegin, leftLength-4*i);
                }else{
                    //indexBegin≤0，即将要切分的字符串已经不足4位
                    leftMoney[i] = strLeft.substring(0, leftLength-4*i);
                }
            }

            //j<=2 进行小组阿拉伯金额转换成大写汉字金额
            for(int j=0; j<leftMoney.length; j++){
                if(!leftMoney[j].equals("0000")){
                    sb.insert(0, BIGSUM[j]);
                }

                MidToUpper(leftMoney[j],sb);
            }

            return sb.toString();
        }

    }


    /**
     * 将最多四位的金额转换成十百千的大写金额     如4181 -->肆仟壹佰捌拾壹
     * @param str 整数金额的字符串形式（最高四位数）
     * @param sb 转换后的大写金额
     * @return 返回大写金额
     */
    public static String MidToUpper(String str, StringBuffer sb){

        if(str == null || str.trim().length() == 0){
            return "";
        }

        int strLength = str.length();

        for(int i=0; i<strLength; i++){
            int sum = Integer.parseInt(str.substring(strLength-i-1,strLength-i));
            if(sum!=0){
                sb.insert(0, MIDSUM[i]);
                sb.insert(0, toUpperNumber(sum));
            }
        }

        return sb.toString();
    }

    /**
     * 阿拉伯数字转换成汉字大写
     * @param num 阿拉伯数字 1~9
     * @return 返回汉字大写 壹~玖
     */
    public static String toUpperNumber(int num){
        if(num>=0 && num<=9){
            return CNSUM[num];
        }
        return "";
    }

}
