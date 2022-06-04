package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/29 5:06 下午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class BatchQueryMouseRecordResult {

    List<MouseRecordDTO> list;

    int width;

    int height;
}
