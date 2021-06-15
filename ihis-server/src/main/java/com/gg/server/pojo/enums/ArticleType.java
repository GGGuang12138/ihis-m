package com.gg.server.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author: GG
 * @date: 2021/5/28 11:18 上午
 */
public enum ArticleType implements IEnum<Integer> {

    IMG(0,"健康资讯"),
    VOD(1,"健康课程");

    private int value;
    private String desc;

    ArticleType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
