package com.gg.server.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医生登陆类
 * @author: GG
 * @date: 2021/2/18 12:15 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "医生登陆对象参数", description = "")
public class MUser {
    @ApiModelProperty(value = "用户名", required = true)
    private String mobile;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
