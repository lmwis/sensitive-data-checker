package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.pojo.BatchUploadNetInfoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/14 10:00 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/net")
public class NetController extends BaseController {

    @PostMapping("/batch")
    public CommonReturnType batchUploadNetInfo(@RequestBody BatchUploadNetInfoDTO batchUploadNetInfoDTO){

        return CommonReturnType.create("");
    }
}
