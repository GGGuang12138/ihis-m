package com.gg.server.service.impl;

import com.gg.server.entity.Doctor;
import com.gg.server.entity.Menu;
import com.gg.server.entity.MenuRole;
import com.gg.server.mapper.MenuMapper;
import com.gg.server.mapper.MenuRoleMapper;
import com.gg.server.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-02-27
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

}
