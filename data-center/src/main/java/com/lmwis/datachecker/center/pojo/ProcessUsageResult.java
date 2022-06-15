package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 7:54 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class ProcessUsageResult {

    String processName;

    String processFile;

    String processDescription;

    long startTime;

    long endTime;

    int continueTime;
}
