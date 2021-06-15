package com.gg.server.pojo.param.edu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author: GG
 * @date: 2021/5/7 11:12 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "封面参数", description = "")
public class CoverParam {

    @ApiModelProperty(value = "封面类型", required = true)
    private Integer type;
    @ApiModelProperty(value = "内容", required = true)
    private List<String> images;

}
