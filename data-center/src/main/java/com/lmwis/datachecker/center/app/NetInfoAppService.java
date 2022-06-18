package com.lmwis.datachecker.center.app;

import com.lmwis.datachecker.center.pojo.BatchQueryNetInfoResult;
import com.lmwis.datachecker.center.pojo.BatchUploadNetInfoDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/17 3:38 下午
 * @Version: 1.0
 */
public interface NetInfoAppService {
    boolean batchUploadNetInfo(BatchUploadNetInfoDTO batchUploadNetInfoDTO);

    BatchQueryNetInfoResult batchQueryNetInfoRangTime(long startTime, long endTime);

    int queryCountADay(String day);
}
