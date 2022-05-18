package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.UserBaseAppService;
import com.lmwis.datachecker.center.app.UserInfoAppService;
import com.lmwis.datachecker.center.pojo.MyComputerInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:08 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor
@Slf4j
public class UserInfoController extends BaseController {

    final UserInfoAppService userInfoAppService;

    final UserBaseAppService userBaseAppService;

    @PostMapping("/register")
    public CommonReturnType registerUserBase(String username) throws BusinessException {
        if (!validateNull(username)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        log.info("[registerUserBase] receive username:{}", username);
        return CommonReturnType.create(userBaseAppService.newUser(username));
    }

    @PostMapping("/computerInfo")
    public CommonReturnType uploadComputerInfo(@RequestBody MyComputerInfoDTO computerInfo) throws BusinessException {

        if (!validateNull(computerInfo)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        log.info("[registerComputerInfo] receive data:{}", computerInfo);
        return CommonReturnType.create(userInfoAppService.uploadComputer(computerInfo));
    }

}
