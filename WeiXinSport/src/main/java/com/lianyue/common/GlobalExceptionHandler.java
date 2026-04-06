package com.lianyue.common;

import com.lianyue.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 捕获所有 Exception
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 1. 打印堆栈信息到控制台
        log.error("系统出现未捕获异常：", e);
        // 2. 告诉前端：系统挂了
        return Result.error("系统开小差了，请联系管理员");
    }

    /**
     * 捕获自定义业务异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.warn("⚠️ 业务异常: {}", e.getMessage());
        return Result.error(e.getMessage()); // 直接把异常返回给前端
    }
}
