package com.lmwis.datachecker.service;

import com.lmwis.datachecker.pojo.KeyboardRecordDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:16 下午
 * @Version: 1.0
 */
public interface KeyboardRecordService {

    void insertRecordByTemp(KeyboardRecordDTO keyboardRecord);

    int saveTempRecord();
}
