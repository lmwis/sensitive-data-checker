package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.MouseRecord;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface MouseRecordService {

    public void insertRecordByTemp(MouseRecord mouseRecord);

    int saveTempRecord();
}
