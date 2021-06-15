package com.gg.server.service.edu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.edu.ArticleContent;
import com.gg.server.entity.edu.UserOperate;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.RespPageBean;
import com.gg.server.pojo.dto.ArticlePage;
import com.gg.server.pojo.param.edu.ArticleContentParam;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 资讯文章内容表 服务类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public interface ArticleContentService extends IService<ArticleContent> {

    /**
     * 添加文章
     * @param articleContentParam
     * @return
     */
    RespBean addArticle(ArticleContentParam articleContentParam,Boolean draft);

    /**
     * 修改文章
     * @param articleContentParam
     * @param id
     * @param draft
     * @return
     */
    RespBean updateArticle(ArticleContentParam articleContentParam,Integer id, Boolean draft);

    /**
     * 查询文章
     * @param currentPage
     * @param size
     * @param articleContent
     * @param dateScope
     * @return
     */
    RespPageBean getArticles(Integer currentPage, Integer size, ArticleContent articleContent, String[] dateScope);

    /**
     * 获取文章详情(包括医生信息)
     * @param id
     * @return
     */
    ArticleContent getArticleDoctor(Integer id);

    /**
     * 获取文章个人信息
     * @param id
     * @return
     */
    UserOperate getArticleUser(Integer id);

    /**
     * 获取栏目对应文章
     * @param id
     * @return
     */
    ArticlePage getChannelArticle(Integer id, Integer timeStamp, Integer withTop);

    /**
     * 根据q查询文章
     * @param page
     * @param size
     * @param q
     * @return
     */
    RespPageBean selectArticlesByq(Integer page, Integer size, String q);
}
