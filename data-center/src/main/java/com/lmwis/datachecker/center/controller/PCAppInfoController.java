package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.ProcessUsageAppService;
import com.lmwis.datachecker.center.pojo.BatchUploadProcessUsageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 12:54 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/pc")
@RequiredArgsConstructor
public class PCAppInfoController extends BaseController {

    final ProcessUsageAppService processUsageAppService;

    @PostMapping(value = "/usage",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadProcessUsage(@RequestBody BatchUploadProcessUsageDTO batchUploadProcessUsageDTO) throws BusinessException {

        if (!validateNull(batchUploadProcessUsageDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchUploadProcessUsage] receive data size:{}", batchUploadProcessUsageDTO.getList().size());
        return CommonReturnType.create(processUsageAppService.batchUploadProcessUsage(batchUploadProcessUsageDTO));

    }

    @GetMapping(value = "/usage")
    public CommonReturnType batchQueryIphoneActionRangTime(long startTime, long endTime) throws BusinessException {

        return CommonReturnType.create(processUsageAppService.batchQueryProcessUsageRangeTime(startTime, endTime));
    }

}
