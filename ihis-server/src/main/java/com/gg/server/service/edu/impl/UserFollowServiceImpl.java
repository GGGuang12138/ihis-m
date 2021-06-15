package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.config.Exception.ErrorCodeException;
import com.gg.server.entity.edu.UserFollow;
import com.gg.server.mapper.edu.UserFollowMapper;
import com.gg.server.pojo.RespBean;
import com.gg.server.service.edu.UserFollowService;
import com.gg.server.utils.FormatUtils;
import com.gg.server.utils.UserUtils;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户关注表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-30
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {


    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    @Transactional
    public RespBean addFollowDoctor(Integer doctorId) {
//        Integer uid = UserUtils.getCurrentUser().getId();
        Integer followId = 1;
        Integer uid = 1;
        UserFollow userFollow = userFollowMapper.selectById(followId);
        // 验证医生是否存在
        // 已存在关注医生
        String doctorIds = String.valueOf(doctorId);
        String doctors;
        if (!StringUtils.isEmpty(userFollow.getDoctorsId())){
            HashSet<String> set = new HashSet<>(Arrays.asList(userFollow.getDoctorsId().split(",")));
            // 医生重复
            if (set.contains(doctorIds)){
                return RespBean.success("已关注");
            }
            doctors = userFollow.getDoctorsId() + ',' + doctorId;
        }else {
            doctors = doctorId+"";
        }
        userFollow.setDoctorsId(doctors);
        userFollowMapper.updateById(userFollow);
        return RespBean.success("关注成功");

    }

    @Override
    @Transactional
    public RespBean deleteFollowDoctor(Integer doctorId) {
        //        Integer uid = UserUtils.getCurrentUser().getId();
        Integer uid = 1;
        Integer followId = 1;
        UserFollow userFollow = userFollowMapper.selectById(followId);
        String doctorIds = String.valueOf(doctorId);

        if (!StringUtils.isEmpty(userFollow.getDoctorsId())){
            // 存在即遍历后删除
            List<String> list = Arrays.asList(userFollow.getDoctorsId().split(","));
            List<String> newList = list.stream().filter(e -> !doctorIds.equals(e)).collect(Collectors.toList());
            String doctors = Joiner.on(",").join(newList);
            userFollow.setDoctorsId(doctors);
            userFollowMapper.updateById(userFollow);
            return RespBean.success("取消");
        }else {
            // 不存在也默认成功
            return RespBean.success("取消成功");
        }
    }
}
