package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:07 下午
 * @Version: 1.0
 */
@TableName("process_usage")
@Data
public class ProcessUsageDO {
    @TableId(type = IdType.AUTO)
    Long id;

    @TableField("uid")
    Long uid;

    Long processId;

    Date startTime;

    Date endTime;

    Date gmtCreate;

    Date gmtModified;
}
