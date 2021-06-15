package com.gg.server.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gg.server.config.Exception.ErrorCodeException;
import com.gg.server.entity.edu.UserOperate;
import com.gg.server.pojo.RespBean;
import com.gg.server.service.edu.UserFollowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gg
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/m/user")
public class UserController {

    @Autowired
    private UserFollowService userFollowService;

    @ApiOperation(value = "添加关注医生")
    @PostMapping("/addFollowDoctor")
    public RespBean addFollowDoctor(@RequestBody String target) {
        Integer doctorId;
        try {
            JSONObject object = JSON.parseObject(target);
            doctorId = object.getInteger("target");
        } catch (Exception e) {
            throw ErrorCodeException.valueOf("错误的医生ID");
        }
        return userFollowService.addFollowDoctor(doctorId);
    }

    @ApiOperation(value = "取消关注医生")
    @PostMapping("/deleteFollowDoctor")
    public RespBean deleteFollowDoctor(@RequestBody String target) {
        Integer doctorId;
        try {
            JSONObject object = JSON.parseObject(target);
            doctorId = object.getInteger("target");
        } catch (Exception e) {
            throw ErrorCodeException.valueOf("错误的医生ID");
        }
        return userFollowService.deleteFollowDoctor(doctorId);
    }

}