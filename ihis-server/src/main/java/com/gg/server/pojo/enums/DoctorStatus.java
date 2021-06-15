package com.gg.server.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 医生状态
 * @author: GG
 * @date: 2021/5/31 11:23 上午
 */
public enum DoctorStatus implements IEnum<Integer> {

    OFF(0,"未审核"),
    WAIT_REVIEW(1,"待审核"),
    FAIL(2,"审核未通过"),
    PASS(3,"审核通过");

    private int value;
    private String desc;

    DoctorStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
