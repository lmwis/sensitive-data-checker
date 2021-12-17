package com.lmwis.datachecker.computer;

import com.lmwis.datachecker.mapper.KeyboardRecord;
import com.lmwis.datachecker.service.KeyboardRecordService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;


/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 3:20 下午
 * @Version: 1.0
 */
@Component
public class MyGlobalKeyListener implements NativeKeyListener {
    final KeyboardRecordService keyboardRecordService;

    public MyGlobalKeyListener(KeyboardRecordService keyboardRecordService) {
        this.keyboardRecordService = keyboardRecordService;
    }

    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        System.out.println("Key typed: "+ NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));

        KeyboardRecord keyboardRecord = new KeyboardRecord();
        keyboardRecord.setKeyText(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        keyboardRecord.setKeyCode(nativeKeyEvent.getKeyCode());
        keyboardRecord.setPcType(System.getProperty("os.name"));
        keyboardRecord.setAction(1);
        keyboardRecord.setGmtCreate(new Date(new java.util.Date().getTime()));
        keyboardRecord.setGmtModified(new Date(new java.util.Date().getTime()));
        keyboardRecordService.insertRecordByTemp(keyboardRecord);
    }

    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        KeyboardRecord keyboardRecord = new KeyboardRecord();
        keyboardRecord.setKeyText(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        keyboardRecord.setKeyCode(nativeKeyEvent.getKeyCode());
        keyboardRecord.setPcType(System.getProperty("os.name"));
        keyboardRecord.setAction(2);
        keyboardRecord.setGmtCreate(new Date(new java.util.Date().getTime()));
        keyboardRecord.setGmtModified(new Date(new java.util.Date().getTime()));
        keyboardRecordService.insertRecordByTemp(keyboardRecord);

    }

    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
