package com.gg.server.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gg
 * @since 2021-02-15
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}

