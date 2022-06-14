package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.pojo.BatchQueryIphoneActionResult;
import com.lmwis.datachecker.center.pojo.BatchUploadIphoneActionDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/6 8:47 下午
 * @Version: 1.0
 */
public interface IphoneActionAppService {
    boolean batchUploadIphoneActions(BatchUploadIphoneActionDTO batchUploadIphoneActionDTO) throws BusinessException;

    BatchQueryIphoneActionResult batchQueryIphoneActionRangeTime(long startTime, long endTime);

    int queryCountADay(String day);
}
