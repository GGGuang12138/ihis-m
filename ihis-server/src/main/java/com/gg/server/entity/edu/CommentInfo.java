package com.gg.server.entity.edu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gg.server.entity.User;
import com.gg.server.pojo.enums.CommentStatus;
import com.gg.server.pojo.enums.CommentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 作品评论信息表
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_comment_info")
@ApiModel(value="CommentInfo对象", description="作品评论信息表")
public class CommentInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作品Id")
    private Integer zid;

    @ApiModelProperty(value = "用户ID")
    private Integer uid;

    @ApiModelProperty(value = "评论的对象Id（由评论类型决定文章的评论/评论的评论）")
    private Integer cid;

    @ApiModelProperty(value = "评论类型")
    private CommentType commentType;

    @ApiModelProperty(value = "评论状态")
    private CommentStatus commentStatus;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "最后修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "评论被评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "评论被点赞数")
    private Integer likeCount;

    @ApiModelProperty(value = "评论者")
    @TableField(exist = false)
    private User user;

}
