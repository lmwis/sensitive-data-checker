package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 6:21 下午
 * @Version: 1.0
 */
@Data
@ToString
public class AppUsagesDTO {

    String packageName;

    long lastTimeStamp;

    long lastTimeUsed;

    long lastTimeVisible;

    long totalTimeInForeground;

    long totalTimeVisible;

    long lastTimeForegroundServiceUsed;

    long totalTimeForegroundServiceUsed;

    int describeContents;

}
