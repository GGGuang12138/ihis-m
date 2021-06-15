package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.entity.edu.Channel;
import com.gg.server.mapper.edu.ChannelMapper;
import com.gg.server.service.edu.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资讯频道信息表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 获取全部频道
     * @return
     */
    @Override
    public List<Channel> getAllChannels(){
        return channelMapper.selectList(new QueryWrapper<Channel>()
                .eq("enabled",true)
                .orderByAsc("orderNum"));
    }
}
