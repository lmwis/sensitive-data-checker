package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/24 8:19 下午
 * @Version: 1.0
 */
@ToString
@Data
public class BatchQueryIphonePostureResult {
    long startTime;
    long endTime;
    int count;
    List<IphonePostureResult> list;
}
