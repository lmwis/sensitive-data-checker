package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.response.FeheadResponse;
import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.center.app.LocationInfoAppService;
import com.lmwis.datachecker.center.pojo.BatchUploadLocationDTO;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:30 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
public class LocationInfoController extends BaseController {

    final LocationInfoAppService locationInfoAppService;

    @PostMapping("")
    public FeheadResponse saveLocation(LocationInfoDTO locationInfoDTO) throws BusinessException {

        if (!validateNull(locationInfoDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        log.info("[saveLocation] revive data:{}",locationInfoDTO);
        return CommonReturnType.create(locationInfoAppService.saveLocationInfo(locationInfoDTO));
    }

    @PostMapping(value = "/batch",produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonReturnType batchUploadLocation(@RequestBody BatchUploadLocationDTO batchUploadLocationDTO) throws BusinessException {

        if (!validateNull(batchUploadLocationDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info("[batchUploadLocation] receive data:{}", GsonUtil.toString(batchUploadLocationDTO));
        return CommonReturnType.create(locationInfoAppService.batchUploadIphonePosture(batchUploadLocationDTO));

    }

    @GetMapping("")
    public CommonReturnType batchQueryLocationRangeTime(long startTime, long endTime) throws BusinessException {

        return CommonReturnType.create(locationInfoAppService.batchQueryLocationRangTime( startTime, endTime));
    }

}
