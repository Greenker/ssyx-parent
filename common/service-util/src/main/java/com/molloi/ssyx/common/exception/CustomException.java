package com.molloi.ssyx.common.exception;

import com.molloi.ssyx.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义异常类
 * @author Molloi
 * @date 2023/6/9 10:00
 */

@Data
public class CustomException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误信息创建异常对象
     * @param message
     * @param code
     */
    public CustomException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

}
