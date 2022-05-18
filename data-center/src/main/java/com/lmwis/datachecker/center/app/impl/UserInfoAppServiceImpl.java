package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.UserInfoAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.MyComputerInfoConvert;
import com.lmwis.datachecker.center.dao.MyComputerInfo;
import com.lmwis.datachecker.center.dao.mapper.ComputerInfoMapper;
import com.lmwis.datachecker.center.pojo.MyComputerInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/14 5:55 下午
 * @Version: 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoAppServiceImpl implements UserInfoAppService {

    final ComputerInfoMapper computerInfoMapper;

    final UserContextHolder userContextHolder;
    @Override
    public MyComputerInfo uploadComputer(MyComputerInfoDTO myComputerInfoDTO) throws BusinessException {
        if (myComputerInfoDTO == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        MyComputerInfo computerInfo = MyComputerInfoConvert.CONVERT.convertToDO(myComputerInfoDTO);

        QueryWrapper<MyComputerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",computerInfo.getUserName());
        queryWrapper.eq("os_name",computerInfo.getOsName());
        queryWrapper.eq("os_version",computerInfo.getOsVersion());
        List<MyComputerInfo> computerInfoDOS = computerInfoMapper.selectList(queryWrapper);
        if (computerInfoDOS==null||computerInfoDOS.size()==0){
            computerInfoMapper.insert(computerInfo);
        }else if(computerInfoDOS.size()==1){
            // 已经存在，获取id
            computerInfo.setId(computerInfoDOS.get(0).getId());
        }else{ // 用户重复 TODO: user repeat need to clean it.

        }
        computerInfo.setUid(userContextHolder.getCurrentUid());
        return computerInfo;
    }
}
