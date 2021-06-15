package com.gg.server.mapper;

import com.gg.server.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户 id 查询对应角色列表
     * @param doctorId
     * @return
     */
    List<Role> getRoles(Integer doctorId);
}
