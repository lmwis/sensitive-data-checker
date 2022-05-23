package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.AppBaseAppService;
import com.lmwis.datachecker.center.app.AppUsageService;
import com.lmwis.datachecker.center.pojo.BatchInitAppBaseDTO;
import com.lmwis.datachecker.center.pojo.BatchQueryUsageEventDTO;
import com.lmwis.datachecker.center.pojo.BatchUploadAppUsagesDTO;
import com.lmwis.datachecker.center.pojo.BatchUploadUsageEventDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    final AppUsageService appUsageService;

    @PostMapping(value = "/init",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchInitAppBase(@RequestBody BatchInitAppBaseDTO batchInitAppBaseDTO) throws BusinessException {
        if (!validateNull(batchInitAppBaseDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchInitAppBase] receive data size:{}",batchInitAppBaseDTO.getList().size());
        return CommonReturnType.create(appBaseAppService.batchInitAppBase(batchInitAppBaseDTO));
    }
    @PostMapping(value = "/usage",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadAppUsages(@RequestBody  BatchUploadAppUsagesDTO batchUploadAppUsagesDTO) throws BusinessException {

        if (!validateNull(batchUploadAppUsagesDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        logger.info("[batchUploadAppUsages] receive data:{}", GsonUtil.toString(batchUploadAppUsagesDTO));
        return CommonReturnType.create(appUsageService.updateAppUsage(batchUploadAppUsagesDTO));

    }

    @PostMapping(value = "/event",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadUsagesEvent(@RequestBody BatchUploadUsageEventDTO batchUploadUsageEventDTO) throws BusinessException {

        if (!validateNull(batchUploadUsageEventDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchUploadUsagesEvent] receive data size:{}", batchUploadUsageEventDTO.getList().size());
        return CommonReturnType.create(appUsageService.updateUsageEvent(batchUploadUsageEventDTO));

    }

    @GetMapping(value = "/event")
    public CommonReturnType batchQueryAppUsageEventRangTime(@RequestBody BatchQueryUsageEventDTO batchQueryUsageEventDTO) throws BusinessException {
        if (!validateNull(batchQueryUsageEventDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchQueryAppUsageEventRangTime] batch query param:{}", batchQueryUsageEventDTO);

        return CommonReturnType.create(appUsageService.batchQueryUsageEventRangeTime(batchQueryUsageEventDTO));
    }
}
