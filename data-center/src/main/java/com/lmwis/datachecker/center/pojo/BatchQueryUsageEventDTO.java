package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 2:43 下午
 * @Version: 1.0
 */

@Data
@ToString
public class BatchQueryUsageEventDTO {

    /**
     * 1 - 最近一个小时
     * 2 - 今天凌晨到现在
     * 3 - 自定义
     * {@link com.lmwis.datachecker.center.constant.BatchQueryAppTimeMode}
     */
    int mode;

    long startTime;

    long endTime;

}
