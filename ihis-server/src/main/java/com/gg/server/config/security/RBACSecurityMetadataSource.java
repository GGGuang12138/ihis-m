package com.gg.server.config.security;

import com.gg.server.entity.Menu;
import com.gg.server.entity.Role;
import com.gg.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.security.access.SecurityConfig;

import java.util.Collection;
import java.util.List;

/**
 * RBAC权限控制
 * 根据返回URL查询该菜单需要的角色
 * @author: GG
 * @date: 2021/4/4 11:57 上午
 */
@Component
public class RBACSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的 url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 查询菜单（带有对应所需要的角色）
        List<Menu> menus = menuService.getMenusWithRole();
        // 判断请求 url 与菜单匹配（返回需要角色）
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(),requestUrl)) {
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }

        }
        // url 没有匹配到菜单 即默认登录即可访问
        // ROLE_LOGIN 登录即可拥有的角色
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
