package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/17 4:08 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class BatchQueryNetInfoResult {
    List<NetInfoResult> list;

    int count;

    long startTime;

    long endTime;
}
