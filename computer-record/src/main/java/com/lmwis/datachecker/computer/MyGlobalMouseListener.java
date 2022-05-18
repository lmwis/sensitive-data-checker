package com.lmwis.datachecker.computer;

import com.lmwis.datachecker.init.ComputerInfoHolder;
import com.lmwis.datachecker.pojo.MouseRecordDTO;
import com.lmwis.datachecker.service.MouseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/21 4:18 下午
 * @Version: 1.0
 */
@Component
@Slf4j
public class MyGlobalMouseListener implements NativeMouseInputListener {

    public static final int MOUSE_RECORD_ACTION_PRESSED=1;
    public static final int MOUSE_RECORD_ACTION_RELEASED=2;
    public static final int MOUSE_RECORD_MODE_MOVE=1;
    public static final int MOUSE_RECORD_MODE_CLICK=2;
    public static final int MOUSE_RECORD_MODE_DRAG=3;
//    public static final int MOUSE_RECORD_BUTTON_LEFT=1;
//    public static final int MOUSE_RECORD_BUTTON_RIGHT=2;

    final ComputerInfoHolder computerInfoHolder;
    final MouseRecordService mouseRecordService;

    public MyGlobalMouseListener(ComputerInfoHolder computerInfoHolder, MouseRecordService mouseRecordService) {
        this.computerInfoHolder = computerInfoHolder;
        this.mouseRecordService = mouseRecordService;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
        MouseRecordDTO mouseRecord = new MouseRecordDTO();
        mouseRecord.setAction(MOUSE_RECORD_ACTION_PRESSED);
        mouseRecord.setMode(MOUSE_RECORD_MODE_CLICK);
        mouseRecord.setButton(e.getButton());
        long now = new Date().getTime();
        mouseRecord.setGmtCreate(now);
        mouseRecord.setGmtModified(now);
        mouseRecordService.insertRecordByTemp(mouseRecord);

    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        MouseRecordDTO mouseRecord = new MouseRecordDTO();
        mouseRecord.setAction(MOUSE_RECORD_ACTION_RELEASED);
        mouseRecord.setMode(MOUSE_RECORD_MODE_CLICK);
        mouseRecord.setButton(e.getButton());
        mouseRecordService.insertRecordByTemp(mouseRecord);
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        packMouseMoveOrDrag(e, MOUSE_RECORD_MODE_MOVE);
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        packMouseMoveOrDrag(e, MOUSE_RECORD_MODE_DRAG);
    }

    private void packMouseMoveOrDrag(NativeMouseEvent e, int mouseRecordModeDrag) {
        MouseRecordDTO mouseRecord = new MouseRecordDTO();
        mouseRecord.setMode(mouseRecordModeDrag);
        mouseRecord.setButton(e.getButton());
        mouseRecord.setX(e.getX());
        mouseRecord.setY(e.getY());
        long now = new Date().getTime();
        mouseRecord.setGmtCreate(now);
        mouseRecord.setGmtModified(now);
        mouseRecordService.insertRecordByTemp(mouseRecord);
    }
}
