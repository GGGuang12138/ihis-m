package com.gg.server.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author: GG
 * @date: 2021/6/1 12:22 上午
 */
public enum CommentStatus implements IEnum<Integer> {

    OFF(0,"未展示"),
    SHOW(1,"展示");

    private int value;
    private String desc;

    CommentStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
