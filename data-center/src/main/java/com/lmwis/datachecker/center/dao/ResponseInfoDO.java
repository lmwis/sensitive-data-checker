package com.lmwis.datachecker.center.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 9:53 下午
 * @Version: 1.0
 */
@TableName("response_info")
@Data
@Getter
public class ResponseInfoDO {

    @TableId(type = IdType.AUTO)
    Long id;

    Long requestInfoId;

    String httpVersion;

    String responseCode;

    String headers;

    String content;

    Date gmtCreate;

    Date gmtModify;
}
