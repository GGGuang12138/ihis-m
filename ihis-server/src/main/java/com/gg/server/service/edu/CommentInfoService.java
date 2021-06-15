package com.gg.server.service.edu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.edu.CommentInfo;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.RespPageBean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作品评论信息表 服务类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public interface CommentInfoService extends IService<CommentInfo> {

    /**
     * 获取评论信息
     * @param type
     * @param source
     * @param offset
     * @param limit
     * @return
     */
    RespPageBean getComments(String type,String source,String offset,String limit);

    /**
     * 添加评论
     * @param commentInfo
     * @return
     */
    RespBean addComment(CommentInfo commentInfo);
}
