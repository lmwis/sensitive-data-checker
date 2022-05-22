package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 6:41 下午
 * @Version: 1.0
 */
@Data
@ToString
public class UsageEventDTO {
    String packageName;

    int eventType;

    long timeStamp;
}
