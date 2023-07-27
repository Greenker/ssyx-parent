package com.molloi.ssyx.product.controller;

import com.molloi.ssyx.common.result.Result;
import com.molloi.ssyx.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Molloi
 * @date 2023/6/14 11:06
 */

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    @ApiOperation("上传图片")
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) {
        return Result.ok(fileUploadService.fileUpload(file));
    }

}
