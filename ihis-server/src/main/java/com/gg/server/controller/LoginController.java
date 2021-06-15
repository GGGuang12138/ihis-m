package com.gg.server.controller;

import com.gg.server.entity.Doctor;
import com.gg.server.entity.User;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.param.DoctorLoginParam;
import com.gg.server.pojo.param.MUser;
import com.gg.server.service.DoctorService;
import com.gg.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登陆
 * @author: GG
 * @date: 2021/2/18 12:26 上午
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;

    @GetMapping("/inqury/hello")
    private String hello2(){
        return "医生接诊";
    }
    @GetMapping("/system/basic/hello")
    private String hello3(){
        return "认证中心";
    }

    @ApiOperation(value = "登陆之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody DoctorLoginParam doctorLoginParam, HttpServletRequest request){
        return doctorService.login(doctorLoginParam.getUsername(), doctorLoginParam.getPassword(),
                doctorLoginParam.getCode(),request);
    }
//    @ApiOperation(value = "登陆")
//    @PostMapping("/m/login")
//    public User login(@RequestBody MUser user){
//        User login = userService.login(user);
//        return login;
//    }

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/user/info")
    public Doctor getDoctorInfo(Principal principal){
        if (null == principal){
            return null;
        }
        String username = principal.getName();
        Doctor doctor = doctorService.getDoctorByUsername(username);
        doctor.setPassword(null);
        doctor.setRoles(doctorService.getRoles(doctor.getId()));
        return doctor;

    }

    @ApiOperation(value = "退出登陆")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！"); // 由前端删除token实现退出
    }
}
