package com.lmwis.datachecker.computer;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: current pc info. exp: width,height,os,name
 * @Author: lmwis
 * @Data: 2021/12/23 5:06 下午
 * @Version: 1.0
 */
@Data
@ToString
public class MyComputerInfo {

    String userName;

    String osName;

    int screenWidth;

    int screenHeight;


}
