package com.example.emaildemo.collection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <ul>
 * <li>文件名称：DataUtil </li>
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
public class DataUtil {

    public static List<SI0053OutListDTO> convert(List<SI0053InDTO> list){
        List<SI0053OutListDTO> resultList = new ArrayList<SI0053OutListDTO>();
        Map<Long,List<SI0053InDTO>> groupList= list.stream().collect(Collectors.groupingBy(SI0053InDTO::getAae001));

        SI0053OutListDTO si0053OutListDTO = new SI0053OutListDTO();
        for (Long aae001: groupList.keySet()) {
            si0053OutListDTO = new SI0053OutListDTO();
            List<SI0053InDTO> aae001List = groupList.get(aae001);
            Map<String,List<SI0053InDTO>> aab069Map = aae001List.stream().collect(Collectors.groupingBy(SI0053InDTO::getAab069));
            for (String aab069: aab069Map.keySet()) {
                List<SI0053InDTO> aab069List = aab069Map.get(aab069);
                String aac999 = aab069List.get(0).getAac999();
                Long aae030 = aab069List.stream().mapToLong(SI0053InDTO::getAae003).min().getAsLong();
                Long aae031 = aab069List.stream().mapToLong(SI0053InDTO::getAae003).max().getAsLong();
                Double aae180 = aab069List.stream().mapToDouble(SI0053InDTO::getAae180).sum();
                if (aae031 - aae030 == aab069List.size()-1){
                    si0053OutListDTO = new SI0053OutListDTO();
                    si0053OutListDTO.setAac999(aac999);
                    si0053OutListDTO.setAae030(aae030);
                    si0053OutListDTO.setAae031(aae031);
                    si0053OutListDTO.setAae180(BigDecimal.valueOf(aae180).divide(BigDecimal.valueOf(aab069List.size()), 2, RoundingMode.HALF_UP).doubleValue());
                    si0053OutListDTO.setAae078("到账");
                    si0053OutListDTO.setAab069(aab069);
                    resultList.add(si0053OutListDTO);
                    aae180 = 0.0;
                }else {
                    for (int i = 1; i < aab069List.size(); i++) {
                        if (aab069List.get(i).getAae003() - aab069List.get(i-1).getAae003() == 1 ){
                            aae031 = aab069List.get(i).getAae003();
                            aae180 = BigDecimal.valueOf(aae180).add(BigDecimal.valueOf(aab069List.get(i).getAae180())).doubleValue();
                            if(aab069List.size() - i == 1){
                                si0053OutListDTO = new SI0053OutListDTO();
                                si0053OutListDTO.setAac999(aac999);
                                si0053OutListDTO.setAae030(aae030);
                                si0053OutListDTO.setAae031(aae031);
                                si0053OutListDTO.setAae180(BigDecimal.valueOf(aae180).divide(BigDecimal.valueOf((aae031 - aae030 + 1)), 2, RoundingMode.HALF_UP).doubleValue());
                                si0053OutListDTO.setAae078("到账");
                                si0053OutListDTO.setAab069(aab069);
                                resultList.add(si0053OutListDTO);
                                aae180 = 0.0;
                            }
                        }else{
                            aae031 = aab069List.get(i-1).getAae003();
                            si0053OutListDTO = new SI0053OutListDTO();
                            si0053OutListDTO.setAac999(aac999);
                            si0053OutListDTO.setAae030(aae030);
                            si0053OutListDTO.setAae031(aae031);
                            si0053OutListDTO.setAae180(BigDecimal.valueOf(aae180).divide(BigDecimal.valueOf(aae031 - aae030 + 1), 2, RoundingMode.HALF_UP).doubleValue());
                            si0053OutListDTO.setAae078("到账");
                            si0053OutListDTO.setAab069(aab069);
                            resultList.add(si0053OutListDTO);
                            aae180 = BigDecimal.valueOf(aae180).add(BigDecimal.valueOf(aab069List.get(i).getAae180())).doubleValue();
                            aae030 = aab069List.get(i).getAae003();
                        }
                    }
                }
            }
        }
        if (resultList != null){
            Collections.sort(resultList, new Comparator<SI0053OutListDTO>() {
                @Override
                public int compare(SI0053OutListDTO o1, SI0053OutListDTO o2) {
                    return o2.getAae030().compareTo(o1.getAae030());
                }
            });
            int maxCount = 18;
            if (resultList.size() > maxCount){
                for (int i = 0; i < resultList.size()-maxCount;) {
                    resultList.remove(resultList.get(0));//按升序查询后如果数据量多于18条那么删除多出来的老数据
                }
            }
        }

        return resultList;
    }

}