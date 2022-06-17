package com.lmwis.datachecker.computer.client;

import com.lmwis.datachecker.computer.pojo.KeyboardRecordDTO;
import com.lmwis.datachecker.computer.pojo.MouseRecordDTO;
import com.lmwis.datachecker.computer.pojo.NetInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/16 7:35 下午
 * @Version: 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DataStore {

    final DataCenterClient dataCenterClient;

    volatile static List<KeyboardRecordDTO> keyboardRecordDTOStore = new ArrayList<>();

    volatile static List<MouseRecordDTO> mouseRecordDTOStore = new ArrayList<>();

    volatile static List<NetInfoDTO> netInfoDTOStore = new ArrayList<>();

    public void saveKeyboardRecord(KeyboardRecordDTO keyboardRecordDTO){
        if (keyboardRecordDTO != null){
            keyboardRecordDTOStore.add(keyboardRecordDTO);
        }
    }

    public void saveMouseRecord(MouseRecordDTO mouseRecordDTO){
        if (mouseRecordDTO != null){
            mouseRecordDTOStore.add(mouseRecordDTO);
        }
    }

    public void saveNetInfo(NetInfoDTO netInfoDTO){
        if (netInfoDTO != null){
            netInfoDTOStore.add(netInfoDTO);
        }
    }
    public void saveNetInfo(List<NetInfoDTO> netInfoDTOList){
        if (CollectionUtils.isNotEmpty(netInfoDTOList)){
            netInfoDTOStore.addAll(netInfoDTOList);
        }
    }

    public void flush(){

        List<KeyboardRecordDTO> currentKeyboard = new ArrayList<>(keyboardRecordDTOStore);

        log.info("[flush] 开始保存键盘数据 数量：{}", currentKeyboard.size());
        try{
            if(dataCenterClient.batchUploadKeyboardRecord(currentKeyboard)){
                keyboardRecordDTOStore.removeAll(currentKeyboard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        List<MouseRecordDTO> currentMouse = new ArrayList<>(mouseRecordDTOStore);
        log.info("[flush] 开始保存鼠标数据 数量：{}", currentMouse.size());
        try{
            if (dataCenterClient.batchUploadMouseRecord(currentMouse)){
                mouseRecordDTOStore.removeAll(currentMouse);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        List<NetInfoDTO> currentNet = new ArrayList<>(netInfoDTOStore);
        log.info("[flush] 开始保存网络数据数据 数量：{}", currentMouse.size());
        try{
            if (dataCenterClient.batchUploadMouseRecord(currentMouse)){
                netInfoDTOStore.removeAll(currentNet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
