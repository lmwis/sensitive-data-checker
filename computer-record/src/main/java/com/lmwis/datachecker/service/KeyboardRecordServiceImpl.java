package com.lmwis.datachecker.service;

import com.lmwis.datachecker.client.DataCenterClient;
import com.lmwis.datachecker.pojo.KeyboardRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 7:17 下午
 * @Version: 1.0
 */
@Service
@Slf4j
public class KeyboardRecordServiceImpl implements KeyboardRecordService {

    private final DataCenterClient dataCenterClient;

    private final List<KeyboardRecordDTO> lists = new ArrayList<>();

    private final double threshold = 20;

    public KeyboardRecordServiceImpl(DataCenterClient dataCenterClient) {
        this.dataCenterClient = dataCenterClient;
    }

    public void insertRecordByTemp(KeyboardRecordDTO keyboardRecord) {
        if (lists.size() > threshold) {
            doInsertRecord();
        } else {
            lists.add(keyboardRecord);
        }
    }

    private int doInsertRecord() {
        List<KeyboardRecordDTO> o = new ArrayList<>(lists);
        lists.removeAll(o);
        int insert = 0;
        try {
            for (KeyboardRecordDTO k : o) {
                insert += dataCenterClient.saveKeyboardRecord(k);
            }
        } catch (Exception e) {
            log.error("数据库写入失败：" + e.getMessage());
        }
        return insert;
    }


    @Override
    public int saveTempRecord() {
        if (!lists.isEmpty()) {
            return doInsertRecord();
        }
        return 0;
    }
}
