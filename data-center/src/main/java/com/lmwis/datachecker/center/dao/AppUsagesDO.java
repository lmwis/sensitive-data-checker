package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 6:21 下午
 * @Version: 1.0
 */
@Data
@ToString
@TableName("app_usage_temp")
public class AppUsagesDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    Long appId;

    String packageName;

    Date lastTimeStamp;

    Date lastTimeUsed;

    Date lastTimeVisible;

    long totalTimeInForeground;

    long totalTimeVisible;

    Date lastTimeForegroundServiceUsed;

    long totalTimeForegroundServiceUsed;

    int describeContents;

    Date gmtCreate;

    Date gmtModified;

}
