package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.center.app.IphoneActionAppService;
import com.lmwis.datachecker.center.app.IphonePostureAppService;
import com.lmwis.datachecker.center.pojo.BatchUploadIphoneActionDTO;
import com.lmwis.datachecker.center.pojo.BatchUploadIphonePostureDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/6 8:48 下午
 * @Version: 1.0
 */

@RestController
@RequestMapping("/iphone")
@AllArgsConstructor
public class IphoneInfoController extends BaseController {

    final IphonePostureAppService iphonePostureAppService;

    final IphoneActionAppService iphoneActionAppService;

    @PostMapping(value = "/posture",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadIphonePosture(@RequestBody BatchUploadIphonePostureDTO batchUploadIphonePostureDTO) throws BusinessException {

        if (!validateNull(batchUploadIphonePostureDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchUploadIphonePosture] receive data:{}", GsonUtil.toString(batchUploadIphonePostureDTO));
        return CommonReturnType.create(iphonePostureAppService.batchUploadIphonePosture(batchUploadIphonePostureDTO));

    }

    @GetMapping(value = "/posture")
    public CommonReturnType batchQueryIphonePostureRangTime(long startTime, long endTime) throws BusinessException {

        return CommonReturnType.create(iphonePostureAppService.batchQueryIphonePostureRangeTime(startTime, endTime));
    }

    @PostMapping(value = "/action",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadIphoneActions(@RequestBody BatchUploadIphoneActionDTO batchUploadIphoneActionDTO) throws BusinessException {

        if (!validateNull(batchUploadIphoneActionDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchUploadIphoneActions] receive data:{}", GsonUtil.toString(batchUploadIphoneActionDTO));
        return CommonReturnType.create(iphoneActionAppService.batchUploadIphoneActions(batchUploadIphoneActionDTO));
    }
    @GetMapping(value = "/action")
    public CommonReturnType batchQueryIphoneActionRangTime(long startTime, long endTime) throws BusinessException {

        return CommonReturnType.create(iphoneActionAppService.batchQueryIphoneActionRangeTime(startTime, endTime));
    }
}
