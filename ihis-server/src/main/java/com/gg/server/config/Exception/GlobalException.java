package com.gg.server.config.Exception;

import com.gg.server.pojo.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * @author: GG
 * @date: 2021/4/11 1:10 下午
 */
@RestControllerAdvice
public class GlobalException {

    private Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e) {
        logger.error(e.toString());
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("数据异常，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
    @ExceptionHandler(ErrorCodeException.class)
    public RespBean errorCodeException(ErrorCodeException e) {
        logger.error(e.toString());
        return  RespBean.error(e.getErrorMsg());
    }


}
