package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.response.FeheadResponse;
import com.lmwis.datachecker.center.app.LocationInfoAppService;
import com.lmwis.datachecker.center.pojo.LocationInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
