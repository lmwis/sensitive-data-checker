package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:26 下午
 * @Version: 1.0
 */
@Data
@ToString
public class ProcessUsageDTO {

    String processName;

    String processFile;

    String processDescription;

    long startTime;

    long endTime;

    long gmtCreate;

    long gmtModified;
}
