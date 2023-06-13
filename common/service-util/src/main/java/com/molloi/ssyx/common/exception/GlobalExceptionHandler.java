package com.molloi.ssyx.common.exception;

import com.molloi.ssyx.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author Molloi
 * @date 2023/6/9 9:55
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回json数据
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(null);
    }

    // 自定义异常处理
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e) {
        return Result.build(null, e.getCode(), e.getMessage());
    }

}
