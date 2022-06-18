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
 * @Data: 2022/4/12 9:52 下午
 * @Version: 1.0
 */
@TableName("request_info")
@Data
@ToString
public class RequestInfoDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long uid;

    String url;

    String hostname;

    Integer port;

    String protocol;

    String method;

    String headers;

    String content;

    String sourceContent;

    Date gmtCreate;

    Date gmtModified;

}
