package com.lmwis.datachecker.service;

import com.lmwis.datachecker.mapper.KeyboardRecord;
import com.lmwis.datachecker.mapper.KeyboardRecordMapper;
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
public class KeyboardRecordServiceImpl implements KeyboardRecordService {

    private final KeyboardRecordMapper keyboardRecordMapper;

    private final List<KeyboardRecord> lists = new ArrayList<>();

    private final double threshold = 20;

    public KeyboardRecordServiceImpl(KeyboardRecordMapper keyboardRecordMapper){
        this.keyboardRecordMapper = keyboardRecordMapper;
    }

    public void insertRecordByTemp(KeyboardRecord keyboardRecord) {
        if (lists.size()> threshold){
            List<KeyboardRecord> o = new ArrayList<>(lists);
            // 删掉写入了的
            try {
                doInsertRecord(o);
                lists.removeAll(o);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            lists.add(keyboardRecord);
        }
    }

    public void doInsertRecord(List<KeyboardRecord> o) {
        for (KeyboardRecord k : o) {
            System.out.println(k);
            keyboardRecordMapper.insert(k);
        }
    }
}
