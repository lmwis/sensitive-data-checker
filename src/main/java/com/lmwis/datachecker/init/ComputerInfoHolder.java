package com.lmwis.datachecker.init;

import com.lmwis.datachecker.computer.MyComputerInfo;
import com.lmwis.datachecker.computer.MyGlobalKeyListener;
import com.lmwis.datachecker.computer.MyGlobalMouseListener;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/23 5:14 下午
 * @Version: 1.0
 */
@Component
@Data
public class ComputerInfoHolder {

    private MyComputerInfo myComputerInfo;

    public ComputerInfoHolder(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        myComputerInfo = new MyComputerInfo();
        myComputerInfo.setOsName(System.getProperty("os.name"));
        myComputerInfo.setUserName(System.getProperty("user.name"));
        myComputerInfo.setScreenHeight(screenHeight);
        myComputerInfo.setScreenWidth(screenWidth);

    }

}
