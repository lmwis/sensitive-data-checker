package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 9:45 下午
 * @Version: 1.0
 */
@TableName("user_computer")
public class UserComputerDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    String latitude;

    String longitude;

}
