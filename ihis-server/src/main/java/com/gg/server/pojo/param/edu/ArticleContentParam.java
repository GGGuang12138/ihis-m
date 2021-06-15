package com.gg.server.pojo.param.edu;

import com.gg.server.pojo.enums.ArticleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author: GG
 * @date: 2021/5/7 10:49 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "资讯内容参数", description = "")
public class ArticleContentParam {

    @ApiModelProperty(value = "标题", required = true)
    private String title;
    @ApiModelProperty(value = "内容", required = true)
    private String content;
    @ApiModelProperty(value = "封面", required = true)
    private CoverParam cover;
    @ApiModelProperty(value = "频道ID", required = true)
    private Integer channelId;
    @ApiModelProperty(value = "咨询类型", required = true)
    private ArticleType articleType;

}
