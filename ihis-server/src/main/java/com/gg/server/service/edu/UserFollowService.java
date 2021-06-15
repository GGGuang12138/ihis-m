package com.gg.server.service.edu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.edu.UserFollow;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.param.edu.ArticleContentParam;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author gg
 * @since 2021-05-30
 */
@Service
public interface UserFollowService extends IService<UserFollow> {


    /**
     * 关注医生
     * @param doctorId
     * @return
     */
    RespBean addFollowDoctor(Integer doctorId);

    /**
     * 取消关注
     * @param doctorId
     * @return
     */
    RespBean deleteFollowDoctor(Integer doctorId);

}
