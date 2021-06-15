package com.gg.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gg.server.config.security.JwtTokenUtil;
import com.gg.server.entity.Doctor;
import com.gg.server.entity.Role;
import com.gg.server.mapper.DoctorMapper;
import com.gg.server.mapper.RoleMapper;
import com.gg.server.pojo.RespBean;
import com.gg.server.service.DoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gg
 * @since 2021-02-15
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private RoleMapper roleMapper;
    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // 登陆判断
        if (StringUtils.isEmpty(code)){
            return RespBean.error("验证码不能为空");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String header = request.getHeader("captcha");
        String captcha = null;
        try {
            byte[] bytes = decoder.decodeBuffer(request.getHeader("captcha"));
            captcha = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(captcha)){
            return RespBean.error("验证码验证错误,重新加载");
        }
        if (!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码错误");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails){
            return RespBean.error("用户名错误");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("密码错误");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        // 更新security登陆对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功",tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Doctor getDoctorByUsername(String username) {
        return doctorMapper.selectOne(new QueryWrapper<Doctor>().eq("username", username)
                .eq("enabled", true));
    }

    /**
     * 根据用户 id 查询对应角色列表
     * @param doctorId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer doctorId) {
        return roleMapper.getRoles(doctorId);
    }
}
