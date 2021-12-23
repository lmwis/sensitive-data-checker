package com.lmwis.datachecker.scheduling;


import com.lmwis.datachecker.service.KeyboardRecordService;
import com.lmwis.datachecker.service.MouseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/21 3:24 下午
 * @Version: 1.0
 */
@Component
public class KeyboardRecordScheduling {

    final KeyboardRecordService keyboardRecordService;
    final MouseRecordService mouseRecordService;

    public KeyboardRecordScheduling(KeyboardRecordService keyboardRecordService,MouseRecordService mouseRecordService){
        this.keyboardRecordService = keyboardRecordService;
        this.mouseRecordService = mouseRecordService;
        new Thread(new KeyboardRecordSchedulingTask(keyboardRecordService,mouseRecordService)).run();
    }

    @Slf4j
    static class KeyboardRecordSchedulingTask implements Runnable{


        final KeyboardRecordService keyboardRecordService;
        final MouseRecordService mouseRecordService;

        public KeyboardRecordSchedulingTask(KeyboardRecordService keyboardRecordService,MouseRecordService mouseRecordService){
            this.keyboardRecordService = keyboardRecordService;
            this.mouseRecordService = mouseRecordService;
        }

        @Override
        public void run() {

            while(true){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = keyboardRecordService.saveTempRecord();
                mouseRecordService.saveTempRecord();
//                log.info("定时任务写入缓存，count="+count);
            }
        }

    }

}
