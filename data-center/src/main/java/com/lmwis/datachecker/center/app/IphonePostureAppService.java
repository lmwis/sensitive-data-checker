package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.pojo.BatchQueryIphonePostureResult;
import com.lmwis.datachecker.center.pojo.BatchUploadIphonePostureDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/7 11:56 上午
 * @Version: 1.0
 */
public interface IphonePostureAppService {
    boolean batchUploadIphonePosture(BatchUploadIphonePostureDTO batchUploadIphonePostureDTO) throws BusinessException;

    BatchQueryIphonePostureResult batchQueryIphonePostureRangeTime(long startTime, long endTime);

    int queryCountADay(String day);
}
