package com.gg.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.User;
import com.gg.server.pojo.param.MUser;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gg
 * @since 2021-05-31
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 登陆
     * @param user
     * @return
     */
    User login(MUser user);

}
