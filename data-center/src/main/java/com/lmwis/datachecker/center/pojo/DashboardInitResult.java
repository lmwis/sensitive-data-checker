package com.lmwis.datachecker.center.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/29 7:37 上午
 * @Version: 1.0
 */
@Data
@ToString
@Builder
public class DashboardInitResult {

    String keyboardLast;
    String locationLast;
    String appLast;
    long uploadLast;

}
