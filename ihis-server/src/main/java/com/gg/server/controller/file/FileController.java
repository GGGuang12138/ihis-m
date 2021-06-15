package com.gg.server.controller.file;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gg.server.config.Exception.ErrorCodeException;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.dto.UploadFileDto;
import com.gg.server.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传
 * @author: GG
 * @date: 2021/5/22 8:24 下午
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "获取上传文件参数")
    @RequestMapping(value = "/getUploadParam", method = RequestMethod.GET)
    public RespBean getUploadParam(@RequestParam String fileId, @RequestParam String type){
        if (StringUtils.isBlank(fileId)){
            return RespBean.error("文件ID不能为空");
        }
        if (StringUtils.isBlank(type)){
            return RespBean.error("文件类型不能为空");
        }
        return RespBean.success(fileService.getUploadParam(fileId,type));
    }

    @ApiOperation(value = "获取下载文件链接")
    @RequestMapping(value = "/getDownloadUrl", method = RequestMethod.GET)
    public String getDownloadUrl(@RequestParam String fileId){
        if (StringUtils.isBlank(fileId)){
            throw ErrorCodeException.valueOf("缺少上传参数");
        }
        return fileService.getDownloadUrl(fileId);
    }
}
