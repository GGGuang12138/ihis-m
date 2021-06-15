package com.gg.server.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章分页(时间戳，请求新的推荐数据传当前的时间戳，请求历史推荐传指定的时间戳)
 * @author: GG
 * @date: 2021/5/29 11:04 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePage {
    /**
     * 时间戳
     */
    private Integer timeStamp;
    /**
     * 数据 list
     */
    private List<?> data;
}
