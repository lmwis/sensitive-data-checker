package com.lmwis.datachecker.init;

import com.lmwis.datachecker.client.DataCenterClient;
import com.lmwis.datachecker.computer.MyComputerInfo;
import com.lmwis.datachecker.pojo.MyComputerInfoDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/23 5:14 下午
 * @Version: 1.0
 */
@Component
@Data
@Slf4j
public class ComputerInfoHolder {

    private MyComputerInfo myComputerInfo;

    public ComputerInfoHolder(DataCenterClient dateCenterClient){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        MyComputerInfoDTO uploadDTO = new MyComputerInfoDTO();
        uploadDTO.setOsName(System.getProperty("os.name"));
        uploadDTO.setOsVersion(System.getProperty("os.version"));
        uploadDTO.setUserName(System.getProperty("user.name"));
        uploadDTO.setScreenHeight(screenHeight);
        uploadDTO.setScreenWidth(screenWidth);
        uploadDTO.setGmtCreate(new Date().getTime());
        uploadDTO.setGmtModified(new Date().getTime());

        this.myComputerInfo = dateCenterClient.uploadComputerInfo(uploadDTO);
        log.info("[ComputerInfoHolder] 上报结果:{}",myComputerInfo);
    }

}
