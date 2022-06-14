package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/13 5:13 下午
 * @Version: 1.0
 */
@Data
@ToString
public class BatchUploadLocationDTO {
    List<LocationInfoDTO> list;
}