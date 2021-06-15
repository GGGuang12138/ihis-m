package com.gg.server.config.security;

import com.gg.server.entity.Doctor;
import com.gg.server.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security配置类
 * @author: GG
 * @date: 2021/2/23 9:40 下午
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RBACAccessDecisionManager rbacAccessDecisionManager;
    @Autowired
    private RBACSecurityMetadataSource rbacSecurityMetadataSource;


    /**
     * 登陆验证配置，传入userDetails和passwordEncoder()
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 请求配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // 使用JWT，关闭CSRF跨站请求伪造
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 使用JWT，关闭session
                .and()
                // 授权管理
                .authorizeRequests()
                // 允许以下 (所有请求都需要认证，登陆放行交由webFilter处理)
                // .antMatchers("/login","/logout").permitAll()
                // 其他都需要授权
                .anyRequest().authenticated()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(rbacAccessDecisionManager);
                        object.setSecurityMetadataSource(rbacSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // 添加 JWT登陆授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加 自定义未授权和未登陆结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /**
     * 不拦截链接
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/m/**",
                "/**"
                // 放开全部进行测试
        );
    }

    /**
     * 重写 userDetailsService 的 loadByUsername(String username) 方法
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Doctor doctor = doctorService.getDoctorByUsername(username);
            if (doctor!=null) {
                doctor.setRoles(doctorService.getRoles(doctor.getId()));
                return doctor;
            }
            throw new UsernameNotFoundException("用户名错误");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
