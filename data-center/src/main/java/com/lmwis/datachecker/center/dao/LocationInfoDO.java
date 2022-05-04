package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:40 下午
 * @Version: 1.0
 */
@TableName("location_info")
@Data
public class LocationInfoDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    String latitude;

    String longitude;

    Date gmtCreate;

    Date gmtModify;

}
