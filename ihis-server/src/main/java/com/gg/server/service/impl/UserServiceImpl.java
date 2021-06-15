package com.gg.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.entity.User;
import com.gg.server.mapper.UserMapper;
import com.gg.server.pojo.param.MUser;
import com.gg.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(MUser user) {
        User username = userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getMobile()));
        if (username == null){
            username.setUsername("新用户");
            username.setPassword("$2a$10$ogvUqZZAxrBwrmVI/e7.SuFYyx8my8d.9zJ6bs9lPKWvbD9eefyCe");
            username.setUserFace("https://img.yzcdn.cn/vant/cat.jpeg");
            userMapper.insert(username);
            username.setPassword("");
            User user1 = userMapper.selectById(username);
            return user1;
        }else{
            username.setPassword("");
            return username;
        }

    }
}
