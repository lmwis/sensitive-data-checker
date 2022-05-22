package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 6:42 下午
 * @Version: 1.0
 */
@TableName("usage_event_temp")
@Data
public class UsageEventDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    Long appId;

    String packageName;

    /**
     * 1 - 移至前台
     * 2 - 移至后台
     */
    int eventType;

    Date timeStamp;

    Date gmtCreate;

    Date gmtModified;
}
