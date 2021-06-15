package com.gg.server.service.edu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.edu.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资讯频道信息表 服务类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public interface ChannelService extends IService<Channel> {

    /**
     * 获取全部频道
     * @return
     */
    List<Channel> getAllChannels();

}
