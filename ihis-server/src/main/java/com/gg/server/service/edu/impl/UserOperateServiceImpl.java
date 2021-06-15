package com.gg.server.service.edu.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gg.server.entity.edu.UserOperate;
import com.gg.server.mapper.edu.UserOperateMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户操作信息表 服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-05-06
 */
@Service
public class UserOperateServiceImpl extends ServiceImpl<UserOperateMapper, UserOperate> implements IService<UserOperate> {

}
