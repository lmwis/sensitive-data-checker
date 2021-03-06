package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.fehead.lang.response.FeheadResponse;
import com.lmwis.datachecker.center.app.KeyboardRecordAppService;
import com.lmwis.datachecker.center.pojo.BatchUploadKeyboardRecordDTO;
import com.lmwis.datachecker.center.pojo.KeyboardRecordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/5 10:08 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/keyboardRecord")
@RequiredArgsConstructor
@Slf4j
public class KeyboardRecordController extends BaseController {

    final KeyboardRecordAppService keyboardRecordAppService;

    @PostMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public FeheadResponse saveKeyboardRecord(@RequestBody KeyboardRecordDTO keyboardRecordDTO) throws BusinessException {

        if (!validateNull(keyboardRecordDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        log.info("[saveKeyboardRecord] receive data:{}", keyboardRecordDTO);
        return CommonReturnType.create(keyboardRecordAppService.insertRecordByTemp(keyboardRecordDTO));
    }

    @PostMapping(value = "/batch",produces = MediaType.APPLICATION_JSON_VALUE)
    public FeheadResponse batchUploadKeyboardRecord(@RequestBody BatchUploadKeyboardRecordDTO batchUploadKeyboardRecordDTO) throws BusinessException {

        if (!validateNull(batchUploadKeyboardRecordDTO)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        log.info("[saveKeyboardRecord] receive data:{}", keyboardRecordDTO);
        return CommonReturnType.create(keyboardRecordAppService.batchUploadKeyboardRecord(batchUploadKeyboardRecordDTO));
    }
    @GetMapping("/{id}")
    public FeheadResponse selectKeyBoardById(@PathVariable Long id) throws BusinessException {
        if (!validateNull(id)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(keyboardRecordAppService.selectKeyboardById(id));
    }
    @GetMapping("/query")
    public FeheadResponse batchQueryKeyboardRecordRangTime(long startTime, long endTime) throws BusinessException {
        if (!validateNull(startTime,endTime)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return CommonReturnType.create(keyboardRecordAppService.batchQueryKeyboardRecordRangTime(startTime,endTime));
    }
}
