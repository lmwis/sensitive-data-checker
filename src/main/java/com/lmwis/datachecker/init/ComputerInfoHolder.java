package com.lmwis.datachecker.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmwis.datachecker.computer.MyComputerInfo;
import com.lmwis.datachecker.mapper.ComputerInfoMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/23 5:14 下午
 * @Version: 1.0
 */
@Component
@Data
public class ComputerInfoHolder {

    final ComputerInfoMapper computerInfoMapper;

    private MyComputerInfo myComputerInfo;

    public ComputerInfoHolder(ComputerInfoMapper computerInfoMapper){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        myComputerInfo = new MyComputerInfo();
        myComputerInfo.setOsName(System.getProperty("os.name"));
        myComputerInfo.setOsVersion(System.getProperty("os.version"));
        myComputerInfo.setUserName(System.getProperty("user.name"));
        myComputerInfo.setScreenHeight(screenHeight);
        myComputerInfo.setScreenWidth(screenWidth);
        myComputerInfo.setGmtCreate(new Date(new java.util.Date().getTime()));
        myComputerInfo.setGmtModified(new Date(new java.util.Date().getTime()));

        this.computerInfoMapper = computerInfoMapper;

        QueryWrapper<MyComputerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",myComputerInfo.getUserName());
        queryWrapper.eq("os_name",myComputerInfo.getOsName());
        queryWrapper.eq("os_version",myComputerInfo.getOsVersion());
        List<MyComputerInfo> computerInfoDOS = computerInfoMapper.selectList(queryWrapper);
        if (computerInfoDOS==null||computerInfoDOS.size()==0){
            computerInfoMapper.insert(myComputerInfo);
        }else if(computerInfoDOS.size()==1){
            // 已经存在，获取id
            myComputerInfo.setId(computerInfoDOS.get(0).getId());
        }else{ // 用户重复 TODO: user repeat need to clean it.

        }
    }

}
