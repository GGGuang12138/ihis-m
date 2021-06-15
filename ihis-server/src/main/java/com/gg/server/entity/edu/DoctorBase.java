package com.gg.server.entity.edu;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gg.server.pojo.enums.DoctorStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author gg
 * @since 2021-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("edu_doctor_base")
@ApiModel(value="DoctorBase对象", description="")
public class DoctorBase implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "医生Id")
    private Integer id;

    @ApiModelProperty(value = "医生姓名")
    private String name;

    @ApiModelProperty(value = "医生头像")
    private String coverUrl;

    @ApiModelProperty(value = "医生性别")
    private Boolean sex;

    @ApiModelProperty(value = "归属医院Id")
    private Integer hspId;

    @ApiModelProperty(value = "归属医院Name")
    private String hspName;

    @ApiModelProperty(value = "线下工号")
    private String offLineId;

    @ApiModelProperty(value = "职称Id")
    private Integer titleId;

    @ApiModelProperty(value = "职称名")
    private String titleName;

    @ApiModelProperty(value = "科室Id")
    private Integer deptId;

    @ApiModelProperty(value = "科室名")
    private String deptName;

    @ApiModelProperty(value = "简介")
    private String des;

    @ApiModelProperty(value = "擅长")
    private String better;

    @ApiModelProperty(value = "状态")
    private DoctorStatus state;


}
