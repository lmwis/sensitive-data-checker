package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.response.FeheadResponse;
import com.lmwis.datachecker.center.app.MouseRecordAppService;
import com.lmwis.datachecker.center.pojo.MouseRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:08 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/mouseRecord")
@RequiredArgsConstructor
@Slf4j
public class MouseRecordController extends BaseController {

    final MouseRecordAppService mouseRecordAppService;

    @PostMapping("")
    public FeheadResponse saveMouseRecord(@RequestBody MouseRecordDTO mouseRecordDTO) throws BusinessException {

        if (!validateNull(mouseRecordDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        log.info("[saveMouseRecord] revive data:{}", mouseRecordDTO);
        return CommonReturnType.create(mouseRecordAppService.insertRecordByTemp(mouseRecordDTO));
    }

    @GetMapping("/{id}")
    public FeheadResponse selectMouseById(@PathVariable Long id) throws BusinessException {
        if (!validateNull(id)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(mouseRecordAppService.selectMouseById(id));
    }
    @GetMapping("/query")
    public FeheadResponse batchQueryMouseRecordRangTime(long startTime, long endTime) throws BusinessException {
        if (!validateNull(startTime,endTime)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(mouseRecordAppService.batchQueryMouseRecordRangTime(startTime,endTime));
    }
}
