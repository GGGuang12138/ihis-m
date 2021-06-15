package com.gg.server.controller;


import com.gg.server.entity.Menu;
import com.gg.server.service.DoctorService;
import com.gg.server.service.MenuService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gg
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/system/function")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查询用户菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByDoctorId(){
        return menuService.getMenuByDoctorId();
    }

}

