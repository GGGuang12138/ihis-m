package com.gg.server.pojo.enums;


import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author: GG
 * @date: 2021/5/7 1:36 下午
 */
public enum ArticleStatus implements IEnum<Integer> {

    PASS(0,"审核通过,并发布"),
    WAIT_REVIEW(1,"审核中"),
    FAIL(2,"审核未通过"),
    DRAFT(3,"草稿"),
    OFF(4,"已下架");

    ArticleStatus(int value,String desc){
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
