package com.example.emaildemo.collection;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <ul>
 * <li>文件名称：DataDemo </li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2016</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/4/20 </li>
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
public class DataDemo {

    public static void main(String[] args) {
        SI0053InDTO si0053InDTO = new SI0053InDTO();
        Long bec013 = 1L;
        if (si0053InDTO.getAae140() == null || "".equals(si0053InDTO.getAae140())){
            si0053InDTO.setAae140("110");//默认企业职工养老险种
        }

//        List<SI0053InDTO> ac01List = commonsBS.getListData(requestJsonMsg, si0053InDTO, SI0053InDTO.class, Long.valueOf(bec013));
        List<SI0053InDTO> ac01List = ReadData.getDataUser();
        int aac008_zc = 0;//正常参保数量
        String aac001_zc = "";//正常参保个人ID
        int aac008_zt = 0;//非正常参保数量
        String aac001_zt = "";//非正常参保个人ID
        String aab001 = "";//单位编号
        if(null != ac01List && ac01List.size() > 0) {
            for (SI0053InDTO inDTO : ac01List) {
                if (inDTO.getAac008() == "1" || "1".equals(inDTO.getAac008())) {
                    aac008_zc++;
                    aac001_zc = inDTO.getAac001();
                    aab001 = inDTO.getAab001();
                } else {
                    aac008_zt++;
                    aac001_zt = inDTO.getAac001();
                    aab001 = inDTO.getAab001();
                }
            }
            //由于有前置判断必定有至少一条参保记录，所以不需要进行为空判断
            if (aac008_zc == 1) {
                //如果只有一个正常参保的参保记录那么取这个个人ID
                si0053InDTO.setAac001(aac001_zc);
                si0053InDTO.setAab001(aab001);
            } else if (aac008_zc > 1) {
                //如果有多个正常参保的参保记录那么报错，多份档案信息
                 System.out.println("");
            } else {
                if (aac008_zt == 1) {
                    //如果没有正常参保，并且只有一条非正常参保记录，那么取这个个人ID
                    si0053InDTO.setAac001(aac001_zt);
                    si0053InDTO.setAab001(aab001);
                } else if (aac008_zt > 1) {
                    //如果没有正常参保，并且多条非正常参保记录那么报错，多份档案信息
                    System.out.println("");
                }
            }
//            List<SI0053InDTO> si0053InDTOS = commonsBS.getListData(requestJsonMsg, si0053InDTO, SI0053InDTO.class, Long.valueOf(bec013+1));
             List<SI0053InDTO> si0053InDTOS = ReadData.getData();
            if (null != si0053InDTOS && si0053InDTOS.size() > 0){
                SI0053OutDTO si0053OutDTO = new SI0053OutDTO();
                List<SI0053OutListDTO> si0053OutListDTOList = DataUtil.convert(si0053InDTOS);//将缴费明细转换成按年统计的缴费信息
                si0053OutDTO.getJsonlist().addAll(si0053OutListDTOList);
                System.out.println("");
            }else {
                System.out.println("");
            }

        }else{
            System.out.println("");
        }
    }

}
