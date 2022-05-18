package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 10:31 下午
 * @Version: 1.0
 */

@Data
@ToString
public class MyComputerInfoDTO {

    String userName;

    String osName;

    String osVersion;

    int screenWidth;

    int screenHeight;

    long gmtCreate;

    long gmtModified;
}