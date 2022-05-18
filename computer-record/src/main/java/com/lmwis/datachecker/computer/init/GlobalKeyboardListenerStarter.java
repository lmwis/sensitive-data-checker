package com.lmwis.datachecker.computer.init;

import com.lmwis.datachecker.computer.listener.MyGlobalKeyListener;
import com.lmwis.datachecker.computer.listener.MyGlobalMouseListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/12 3:45 下午
 * @Version: 1.0
 */
@Component
public class GlobalKeyboardListenerStarter {

    final MyGlobalKeyListener myGlobalKeyListener;
    final MyGlobalMouseListener myGlobalMouseListener;

    public GlobalKeyboardListenerStarter(MyGlobalMouseListener myGlobalMouseListener,MyGlobalKeyListener myGlobalKeyListener){
        this.myGlobalMouseListener = myGlobalMouseListener;
        this.myGlobalKeyListener = myGlobalKeyListener;
        init(myGlobalKeyListener);
    }
    private void init(MyGlobalKeyListener myGlobalKeyListener){
        try {
            GlobalScreen.registerNativeHook();
            // Get the logger for "com.github.kwhat.jnativehook" and set the level to warning.
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            // Don't forget to disable the parent handlers.
            logger.setUseParentHandlers(false);
        } catch (NativeHookException ex) {
            ex.printStackTrace();

            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(myGlobalKeyListener);
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(myGlobalMouseListener);
        GlobalScreen.addNativeMouseMotionListener(myGlobalMouseListener);


    }
}
