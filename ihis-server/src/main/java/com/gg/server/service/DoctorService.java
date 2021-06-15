package com.gg.server.service;

import com.gg.server.entity.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gg.server.entity.Role;
import com.gg.server.pojo.RespBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gg
 * @since 2021-02-15
 */
@Service
public interface DoctorService extends IService<Doctor> {

    /**
     * 登陆后返回token
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Doctor getDoctorByUsername(String username);

    /**
     * 根据用户 id 查询对应角色列表
     * @param doctorId
     * @return
     */
    List<Role> getRoles(Integer doctorId);
}
