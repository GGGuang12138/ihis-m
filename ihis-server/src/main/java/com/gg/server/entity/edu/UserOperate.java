package com.gg.server.entity.edu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户操作信息表
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_user_operate")
@ApiModel(value="UserOperate对象", description="用户操作信息表")
public class UserOperate implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作品ID")
    private Integer zid;

    @ApiModelProperty(value = "用户Id")
    private Integer uid;

    @ApiModelProperty(value = "0默认不收藏")
    private Integer collect;

    @ApiModelProperty(value = "0默认不点赞")
    private Integer isLike;

    @ApiModelProperty(value = "0默认不评论")
    private Integer isComment;

    @ApiModelProperty(value = "0默认不关注医生")
    @TableField(exist = false)
    private Integer doctor;

    public UserOperate(Integer zid, Integer uid, Integer collect, Integer isLike, Integer isComment, Integer doctor) {
        this.zid = zid;
        this.uid = uid;
        this.collect = collect;
        this.isLike = isLike;
        this.isComment = isComment;
        this.doctor = doctor;
    }
}
