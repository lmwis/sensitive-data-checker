package com.lmwis.datachecker.center.pojo;

import com.lmwis.datachecker.center.dao.AppBaseDO;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 3:11 下午
 * @Version: 1.0
 */
@Data
@ToString
public class AppUsageResult {

    AppBaseDO appInfo;

    /**
     * 持续时间中文描述
     */
    String continueTimeText;

    /**
     * 使用的时间 ms
     */
    long continueTime;

    /**
     * 开始时间
     */
    long startTime;

}
