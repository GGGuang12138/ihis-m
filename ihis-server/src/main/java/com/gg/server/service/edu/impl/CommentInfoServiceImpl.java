package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.entity.edu.ArticleContent;
import com.gg.server.entity.edu.ArticleStatistics;
import com.gg.server.entity.edu.CommentInfo;
import com.gg.server.mapper.edu.ArticleStatisticsMapper;
import com.gg.server.mapper.edu.CommentInfoMapper;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.RespPageBean;
import com.gg.server.pojo.enums.CommentType;
import com.gg.server.service.edu.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 作品评论信息表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {


    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Autowired
    private ArticleStatisticsMapper articleStatisticsMapper;
    /**
     * 获取评论信息
     *
     * @param type
     * @param source
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public RespPageBean getComments(String type, String source, String offset, String limit) {
        Page<CommentInfo> page = new Page<>(Integer.parseInt(offset),Integer.parseInt(limit));
        CommentType commentType = CommentType.valueOf(type);
        IPage<CommentInfo> articleByPage = commentInfoMapper.getCommentByPage(page, source,commentType);
        return new RespPageBean(articleByPage.getTotal(),articleByPage.getRecords());
    }

    @Override
    @Transactional
    public RespBean addComment(CommentInfo commentInfo) {
        if(commentInfo.getZid()!=null){
            ArticleStatistics articleStatistics = articleStatisticsMapper.selectById(commentInfo.getZid());
            if (articleStatistics == null){
                articleStatistics = new ArticleStatistics();
                articleStatistics.setId(commentInfo.getZid());
                articleStatistics.setCommentCount(1);
                articleStatisticsMapper.insert(articleStatistics);
            }else {
                articleStatistics.setCommentCount(articleStatistics.getCommentCount() + 1);
                articleStatisticsMapper.updateById(articleStatistics);
            }
            commentInfo.setCreateTime(LocalDateTime.now());
            commentInfoMapper.insert(commentInfo);
            return RespBean.success("评论成功");
        }

        return RespBean.error("评论异常");

    }
}
