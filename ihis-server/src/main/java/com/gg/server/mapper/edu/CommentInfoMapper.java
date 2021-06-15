package com.gg.server.mapper.edu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gg.server.entity.edu.ArticleContent;
import com.gg.server.entity.edu.CommentInfo;
import com.gg.server.pojo.enums.CommentType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 作品评论信息表 Mapper 接口
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Component
public interface CommentInfoMapper extends BaseMapper<CommentInfo> {

    /**
     * 查询文章评论
     * @param page
     * @param id
     * @param type
     * @return
     */
    IPage<CommentInfo> getCommentByPage(Page<CommentInfo> page,
                                           @Param("id") String id,
                                           @Param("type") CommentType type
    );

}
