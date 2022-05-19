package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.pojo.AppBaseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:25 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class AppUsageController extends BaseController {

    final AppBaseAppService appBaseAppService;

    @PostMapping
    public CommonReturnType uploadAppBase(@RequestBody AppBaseDTO appBaseDTO) throws BusinessException {
        if (validateNull(appBaseDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        return CommonReturnType.create(appBaseAppService.uploadAppBase(appBaseDTO));
    }
}
