package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.KeyboardRecord;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface KeyboardRecordService {

    public void insertRecordByTemp(KeyboardRecord keyboardRecord);

    public void doInsertRecord(List<KeyboardRecord> keyboardRecords);
}
