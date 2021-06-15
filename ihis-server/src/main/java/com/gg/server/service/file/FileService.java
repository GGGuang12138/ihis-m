package com.gg.server.service.file;

import com.gg.server.pojo.RespBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: GG
 * @date: 2021/5/22 8:37 下午
 */
@Service
public interface FileService {


    /**
     * 获取上传参数
     * @param fileId
     * @param type
     * @return
     */
    RespBean getUploadParam(String fileId, String type);

    /**
     * 获取下载链接
     * @param fileId
     * @return
     */
    String getDownloadUrl(String fileId);
}
