package com.gg.server.entity.edu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户关注表
 * </p>
 *
 * @author gg
 * @since 2021-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_user_follow")
@ApiModel(value="UserFollow对象", description="用户关注表")
public class UserFollow implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联用户Id")
    private Integer uid;

    @ApiModelProperty(value = "关注栏目列表，用英文,隔开")
    private String channelsId;

    @ApiModelProperty(value = "关注医生ID用英文逗号隔开")
    private String doctorsId;

    @ApiModelProperty(value = "关注医院")
    private String hspsId;

    @ApiModelProperty(value = "最后一次修改时间")
    private LocalDateTime updateTime;


}
