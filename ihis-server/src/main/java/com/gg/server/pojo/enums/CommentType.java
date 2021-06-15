package com.gg.server.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author: GG
 * @date: 2021/6/1 12:18 上午
 */
public enum CommentType implements IEnum<Integer> {
    ARTICLE(0,"文章评论"),
    COMMENT(1,"评论的评论");

    private int value;
    private String desc;

    CommentType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
