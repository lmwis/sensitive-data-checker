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
 * @Data: 2022/6/6 8:43 下午
 * @Version: 1.0
 */
@Data
@ToString
@TableName("iphone_action")
public class IphoneActionDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    int actionCode;

    String actionName;

    Date gmtCreate;

    Date gmtModified;

}
