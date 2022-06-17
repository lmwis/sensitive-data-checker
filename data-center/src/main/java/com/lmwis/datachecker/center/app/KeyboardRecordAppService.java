package com.lmwis.datachecker.center.app;


import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.KeyboardRecordDO;
import com.lmwis.datachecker.center.pojo.BatchUploadKeyboardRecordDTO;
import com.lmwis.datachecker.center.pojo.KeyboardRecordDTO;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface KeyboardRecordAppService {

    int insertRecordByTemp(KeyboardRecordDTO keyboardRecord) throws BusinessException;

    int saveTempRecord();

    KeyboardRecordDO selectKeyboardById(Long id);

    KeyboardRecordDO selectLastDO();

    int queryCountADay(String day);

    List<KeyboardRecordDTO> batchQueryKeyboardRecordRangTime(long startTime, long endTime);

    boolean batchUploadKeyboardRecord(BatchUploadKeyboardRecordDTO batchUploadKeyboardRecordDTO) throws BusinessException;
}
