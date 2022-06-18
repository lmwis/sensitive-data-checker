package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.NetInfoAppService;
import com.lmwis.datachecker.center.pojo.BatchUploadNetInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/14 10:00 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/net")
@RequiredArgsConstructor
public class NetController extends BaseController {

    final NetInfoAppService netInfoAppService;

    @PostMapping("/batch")
    public CommonReturnType batchUploadNetInfo(@RequestBody BatchUploadNetInfoDTO batchUploadNetInfoDTO) throws BusinessException {

        if (!validateNull(batchUploadNetInfoDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(netInfoAppService.batchUploadNetInfo(batchUploadNetInfoDTO));
    }

    @GetMapping("/query")
    public CommonReturnType batchQueryMouseRecordRangTime(long startTime, long endTime) throws BusinessException {
        if (!validateNull(startTime,endTime)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(netInfoAppService.batchQueryNetInfoRangTime(startTime,endTime));
    }
}
