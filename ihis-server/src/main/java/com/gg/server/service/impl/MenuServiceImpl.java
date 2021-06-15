package com.gg.server.service.impl;

import com.gg.server.entity.Doctor;
import com.gg.server.entity.Menu;
import com.gg.server.mapper.MenuMapper;
import com.gg.server.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 通过用户ID获取用户信息
     * @return
     */
    @Override
    public List<Menu> getMenuByDoctorId() {
        Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer doctorId = doctor.getId();
        // 从redis中获取菜单数据
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<Menu> menus = (List<Menu>)valueOperations.get("menu_" + doctorId);
        // 没有就查库,并设置到混存
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenuByDoctorId(doctorId);
            valueOperations.set("menu_"+doctorId, menus);
        }
        return menus;
    }

    /**
     * 查询菜单可访问角色列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

}
