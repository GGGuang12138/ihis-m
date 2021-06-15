package com.gg.server.mapper.edu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.server.entity.edu.ArticleContent;
import com.gg.server.pojo.RespPageBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * <p>
 * 资讯文章内容表 Mapper 接口
 * </p>
 *
 * @author gg
 * @since 2021-05-07
 */
@Component
public interface ArticleContentMapper extends BaseMapper<ArticleContent> {


    /**
     * 查询文章
     * @param page
     * @param articleContent
     * @param createDateScope
     * @return
     */
    IPage<ArticleContent> getArticleByPage(Page<ArticleContent> page,
                                           @Param("articleContent") ArticleContent articleContent,
                                           @Param("createDateScope") String[] createDateScope
    );

    /**
     * 查询栏目对应文章
     * @param page
     * @param cid
     * @return
     */
    IPage<ArticleContent> getArticleByCid(Page<ArticleContent> page,
                                          @Param("channelId") Integer cid);

    /**
     * 查询文章信息(包括作者信息)
     * @param id
     * @return
     */
    ArticleContent getArticleDoctor(@Param("id") Integer id);

    /**
     * 分页查询
     * @param q
     * @return
     */
    IPage<ArticleContent> getArticleByq(Page<ArticleContent> page, @Param("q") String q);


}
