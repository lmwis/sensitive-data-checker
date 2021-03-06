package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 2:49 下午
 * @Version: 1.0
 */

@Data
@ToString
public class BatchQueryProcessUsageResult {

    List<ProcessUsageResult> list;

    int count;

    long startTime;

    long endTime;
}
