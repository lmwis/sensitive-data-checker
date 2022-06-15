package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 6:40 下午
 * @Version: 1.0
 */
@Data
@ToString
public class BatchUploadProcessUsageDTO {
    List<ProcessUsageDTO> list;

}
