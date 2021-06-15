package com.gg.server.entity.edu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gg.server.pojo.enums.ArticleStatus;
import com.gg.server.pojo.enums.ArticleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资讯文章内容表
 * </p>
 *
 * @author gg
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_article_content")
@ApiModel(value="ArticleContent对象", description="资讯文章内容表")
public class ArticleContent implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "频道ID")
    private Integer cid;

    @ApiModelProperty(value = "创建者ID")
    private Integer creatorId;

    @ApiModelProperty(value = "创建者名字")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creatorTime;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面图片数量")
    private Integer coverCount;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "文章状态")
    private ArticleStatus articleState;

    @ApiModelProperty(value = "内容概述")
    private String contentSummary;

    @ApiModelProperty(value = "详细内容ID")
    private String contentId;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "文章类型")
    private ArticleType articleType;

    @ApiModelProperty(value = "视频URL")
    private String contentUrl;

    @ApiModelProperty(value = "频道")
    @TableField(exist = false)
    private Channel channel;

    @ApiModelProperty(value = "作者医生信息")
    @TableField(exist = false)
    private DoctorBase doctorBase;

}
