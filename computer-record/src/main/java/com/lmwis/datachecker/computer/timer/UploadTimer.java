package com.lmwis.datachecker.computer.timer;

import com.lmwis.datachecker.computer.client.DataStore;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/16 1:12 下午
 * @Version: 1.0
 */
@Component
public class UploadTimer {

    final DataStore dataStore;

    public UploadTimer(DataStore dataStore) {
        this.dataStore = dataStore;
        initTimer();
    }

    private void initTimer() {
        final long timeInterval = 5000;
        new Thread(() -> {
            while (true) {
//                dataStore.flush();
                try {
                    Thread.sleep(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
