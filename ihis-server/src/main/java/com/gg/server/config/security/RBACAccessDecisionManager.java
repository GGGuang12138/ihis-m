package com.gg.server.config.security;

import com.gg.server.entity.Role;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限控制
 * 获取当前角色列表 与 菜单所需角色列表 进行匹配
 * @author: GG
 * @date: 2021/4/4 11:58 上午
 */
@Component
public class RBACAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // 遍历 菜单所需角色列表
        for (ConfigAttribute configAttribute : collection){
            // 在MetadataSource中获取
            String needRole = configAttribute.getAttribute();
            // 登陆即可情况
            if ("ROLE_LOGIN".equals(needRole)){
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录");
                }else {
                    return;
                }
            }
            // 当前角色是否匹配
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> currentRole = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (currentRole.contains(needRole)){
                return;
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
