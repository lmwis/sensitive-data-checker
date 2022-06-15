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
 * @Data: 2022/6/15 1:08 下午
 * @Version: 1.0
 */
@TableName("process_base")
@Data
public class ProcessBaseDO {
    @TableId(type = IdType.AUTO)
    Long id;

    @TableField("uid")
    Long uid;

    String processName;

    String processFile;

    String processDescription;

    Date gmtCreate;

    Date gmtModified;
}
