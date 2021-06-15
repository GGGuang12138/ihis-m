package com.gg.server.controller.edu;


import com.gg.server.entity.edu.Channel;
import com.gg.server.service.edu.ChannelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 资讯频道信息表 前端控制器
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/m/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @ApiOperation(value = "查询所有频道")
    @GetMapping("/getMyChannels")
    private List<Channel> getAllChannels(){
        return channelService.getAllChannels();
    }


}

