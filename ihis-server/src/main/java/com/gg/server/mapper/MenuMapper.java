package com.gg.server.mapper;

import com.gg.server.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gg
 * @since 2021-02-27
 */
@Component
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户ID获取用户信息
     * @param id
     * @return
     */
    List<Menu> getMenuByDoctorId(Integer id);

    /**
     * 查询菜单可访问角色列表
     * @return
     */
    List<Menu> getMenusWithRole();
}
