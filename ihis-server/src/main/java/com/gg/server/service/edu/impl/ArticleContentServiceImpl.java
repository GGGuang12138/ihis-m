package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.config.Exception.ErrorCodeException;
import com.gg.server.entity.User;
import com.gg.server.entity.edu.ArticleContent;
import com.gg.server.entity.edu.ArticleDetail;
import com.gg.server.entity.edu.UserFollow;
import com.gg.server.entity.edu.UserOperate;
import com.gg.server.mapper.edu.ArticleContentMapper;
import com.gg.server.mapper.edu.ArticleDetailMapper;
import com.gg.server.mapper.edu.UserFollowMapper;
import com.gg.server.mapper.edu.UserOperateMapper;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.RespPageBean;
import com.gg.server.pojo.dto.ArticlePage;
import com.gg.server.pojo.enums.ArticleStatus;
import com.gg.server.pojo.enums.ArticleType;
import com.gg.server.pojo.param.edu.ArticleContentParam;
import com.gg.server.pojo.param.edu.CoverParam;
import com.gg.server.service.edu.ArticleContentService;
import com.gg.server.service.file.FileService;
import com.gg.server.utils.RandomUtil;
import com.gg.server.utils.UserUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 资讯文章内容表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {

    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleDetailMapper articleDetailMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserOperateMapper userOperateMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;


    @Override
    public ArticleContent getArticleDoctor(Integer id){
        ArticleContent articleContent = articleContentMapper.getArticleDoctor(id);
        if (articleContent == null) {
            throw ErrorCodeException.valueOf("文章已删除");
        }
        return articleContent;
    }

    @Override
    public UserOperate getArticleUser(Integer id) {
//        Integer uid = UserUtils.getCurrentUser().getId();
        Integer followId = 1;
        Integer uid = 1;
        // 1。默认个人操作信息
        UserOperate operate = new UserOperate(id, uid, 0, 0, 0, 0);
        UserOperate userOperate = userOperateMapper.selectOne(new QueryWrapper<UserOperate>().eq("zid", id).eq("uid", uid));
        if (userOperate == null){
            return operate;
        }
        // 2。有进行操作过
        operate = userOperate;

        // 是否关注医生
        UserFollow userFollow = userFollowMapper.selectById(uid);
        if (userFollow != null && !StringUtils.isEmpty(userFollow.getDoctorsId())){
            String creatorId = String.valueOf(articleContentMapper.selectById(id).getCreatorId());
            String doctorsId = userFollow.getDoctorsId();
            // 存在关注医生，进行匹配
            if (!StringUtils.isEmpty(doctorsId)){
                String[] list = userFollow.getDoctorsId().split(",");
                for (String l:list){
                    if (creatorId.equals(l)){
                        operate.setDoctor(1);
                    }
                }
            }
        }
        return operate;

    }

    @Override
    public ArticlePage getChannelArticle(Integer cid, Integer timeStamp, Integer withTop) {
        Page<ArticleContent> page = new Page<>(timeStamp,2);
        IPage<ArticleContent> articleByPage = articleContentMapper.getArticleByCid(page, cid);
        long currentPage = articleByPage.getCurrent() == articleByPage.getSize()? -1:articleByPage.getCurrent();
        List<ArticleContent> records = articleByPage.getRecords();
        for (ArticleContent record : records){
            if (record.getCoverCount() == 1){
                String downloadUrl = fileService.getDownloadUrl(record.getCoverUrl());
                record.setCoverUrl(downloadUrl);
            }
        }

        return new ArticlePage((int)articleByPage.getCurrent(),articleByPage.getRecords());
    }

    @Override
    public RespPageBean selectArticlesByq(Integer currentPage, Integer size, String q) {
        Page<ArticleContent> page = new Page<>(currentPage,size);
        IPage<ArticleContent> articleByq = articleContentMapper.getArticleByq(page, q);
        return new RespPageBean(articleByq.getTotal(),articleByq.getRecords());

    }


    /**
     * 添加文章
     * @param articleContentParam
     * @return
     */
    @Override
    @Transactional
    public RespBean addArticle(ArticleContentParam articleContentParam,Boolean draft) {
        // 内容，可替换为非关系数据库保存
        ArticleDetail articleDetail = new ArticleDetail();
        int rndInt = RandomUtil.rndInt(0, 100000);
        articleDetail.setId(rndInt);
        articleDetail.setArticleDetail(articleContentParam.getContent());
        articleDetailMapper.insert(articleDetail);
        // 其他信息
        ArticleContent articleContent = new ArticleContent();
        articleContent.setCid(articleContentParam.getChannelId());
        articleContent.setCreatorId(UserUtils.getCurrentUser().getId());
        articleContent.setCreatorName(UserUtils.getCurrentUser().getName());
        articleContent.setCreatorTime(LocalDateTime.now());
        articleContent.setUpdateTime(LocalDateTime.now());
        articleContent.setTitle(articleContentParam.getTitle());
        articleContent.setContentSummary(articleContentParam.getContent());
        articleContent.setContentId(String.valueOf(rndInt));
        articleContent.setArticleType(articleContentParam.getArticleType());
        CoverParam cover = articleContentParam.getCover();
        articleContent.setCoverCount(cover.getType());
        if (cover.getType() == 1){
            if(cover.getImages().size() == 0){

            }
            articleContent.setCoverUrl(cover.getImages().get(0));
        }
        if (draft) {
            articleContent.setArticleState(ArticleStatus.WAIT_REVIEW);
        }else {
            articleContent.setArticleState(ArticleStatus.DRAFT);
        }
        articleContentMapper.insert(articleContent);
        return RespBean.success("新增成功");
    }

    @Override
    public RespBean updateArticle(ArticleContentParam articleContentParam, Integer id, Boolean draft) {
        // 详细内容
        ArticleContent articleContent = articleContentMapper.selectById(id);
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setId(articleContent.getId());
        articleDetail.setArticleDetail(articleContentParam.getContent());
        articleDetailMapper.updateById(articleDetail);
        // 其他信息
        articleContent.setCid(articleContentParam.getChannelId());
        articleContent.setTitle(articleContentParam.getTitle());
        articleContent.setContentSummary(articleContentParam.getContent());
        articleContent.setUpdateTime(LocalDateTime.now());
        CoverParam cover = articleContentParam.getCover();
        articleContent.setCoverCount(cover.getType()); // 为0
        if (cover.getType() == 1){ // 为1
            if(cover.getImages().size() == 0){ // 无文件改为0
                articleContent.setCoverCount(0);
                articleContent.setCoverUrl(null);
            }else{ // 有文件保存为1
                articleContent.setCoverCount(cover.getType());
                articleContent.setCoverUrl(cover.getImages().get(0));
            }
        }
        if (draft) {
            articleContent.setArticleState(ArticleStatus.WAIT_REVIEW);
        }else {
            articleContent.setArticleState(ArticleStatus.DRAFT);
        }
        articleContentMapper.updateById(articleContent);
        return RespBean.success("修改成功");

    }

    /**
     * 查询文章
     * @param currentPage
     * @param size
     * @param articleContent
     * @param dateScope
     * @return
     */
    @Override
    public RespPageBean getArticles(Integer currentPage, Integer size, ArticleContent articleContent, String[] dateScope) {
        Page<ArticleContent> page = new Page<>(currentPage,size);
        IPage<ArticleContent> articleByPage = articleContentMapper.getArticleByPage(page, articleContent, dateScope);
        return new RespPageBean(articleByPage.getTotal(),articleByPage.getRecords());
    }


}
