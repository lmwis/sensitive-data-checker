package com.lmwis.datachecker.computer;

import com.lmwis.datachecker.init.ComputerInfoHolder;
import com.lmwis.datachecker.pojo.KeyboardRecordDTO;
import com.lmwis.datachecker.service.KeyboardRecordService;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 3:20 下午
 * @Version: 1.0
 */
@Component
@Slf4j
public class MyGlobalKeyListener implements NativeKeyListener {

    public static final int KEYBOARD_ACTION_PRESSED=1;
    public static final int KEYBOARD_ACTION_RELEASED=2;

    final ComputerInfoHolder computerInfoHolder;

    final KeyboardRecordService keyboardRecordService;

    public MyGlobalKeyListener(KeyboardRecordService keyboardRecordService, ComputerInfoHolder computerInfoHolder) {
        this.keyboardRecordService = keyboardRecordService;
        this.computerInfoHolder = computerInfoHolder;
    }

    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        log.info("Key typed: "+ NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        packAndSaveKeyboardObject(nativeKeyEvent, KEYBOARD_ACTION_PRESSED);
    }

    private void packAndSaveKeyboardObject(NativeKeyEvent nativeKeyEvent, int keyboardActionPressed) {
        KeyboardRecordDTO keyboardRecord = new KeyboardRecordDTO();
        keyboardRecord.setKeyText(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        keyboardRecord.setKeyCode(nativeKeyEvent.getKeyCode());
        keyboardRecord.setAction(keyboardActionPressed);
        Date now = new Date();
        keyboardRecord.setGmtCreate(now.getTime());
        keyboardRecord.setGmtModified(now.getTime());
        keyboardRecordService.insertRecordByTemp(keyboardRecord);
    }

    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        packAndSaveKeyboardObject(nativeKeyEvent, KEYBOARD_ACTION_RELEASED);

    }

    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
}
