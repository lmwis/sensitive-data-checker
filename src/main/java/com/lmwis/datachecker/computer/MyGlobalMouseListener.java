package com.lmwis.datachecker.computer;

import com.lmwis.datachecker.init.ComputerInfoHolder;
import com.lmwis.datachecker.mapper.MouseRecord;
import lombok.extern.slf4j.Slf4j;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.Date;

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
    public static final int MOUSE_RECORD_BUTTON_LEFT=1;
    public static final int MOUSE_RECORD_BUTTON_RIGHT=2;

    final ComputerInfoHolder computerInfoHolder;

    public MyGlobalMouseListener(ComputerInfoHolder computerInfoHolder) {
        this.computerInfoHolder = computerInfoHolder;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
//        System.out.println("Mouse Clicked: " + e.getClickCount());
    }

    public void nativeMousePressed(NativeMouseEvent e) {
//        System.out.println("Mouse Pressed: " + e.getButton());

        MouseRecord mouseRecord = new MouseRecord();
        mouseRecord.setAction(1);
        mouseRecord.setMode(1);
        mouseRecord.setGmtCreate(new Date(new java.util.Date().getTime()));
        mouseRecord.setGmtModified(new Date(new java.util.Date().getTime()));

        System.out.println(computerInfoHolder.getMyComputerInfo());

    }

    public void nativeMouseReleased(NativeMouseEvent e) {
//        System.out.println("Mouse Released: " + e.getButton());

        MouseRecord mouseRecord = new MouseRecord();
        mouseRecord.setAction(2);
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
//        System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());

    }

    public void nativeMouseDragged(NativeMouseEvent e) {
//        System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
}
