package com.gg.server.service.file.impl;

import com.gg.server.config.Exception.ErrorCodeException;
import com.gg.server.config.FileServiceConfig;
import com.gg.server.pojo.RespBean;
import com.gg.server.pojo.dto.UploadFileDto;
import com.gg.server.service.file.FileService;
import com.gg.server.utils.CryptUtil;
import com.gg.server.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: GG
 * @date: 2021/5/22 8:37 下午
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileServiceConfig fileServiceConfig;

    /**
     * 签名
     * @param partnerId 合作方Id
     * @param time 时间戳
     * @param executeId 标识Id（在这里为前端传的fileId）
     * @param partnerKey 合作方Key
     * @return
     */
    private String sign(String partnerId,long time,String executeId,String partnerKey){
        StringBuffer sb = new StringBuffer();
        sb.append(partnerId);
        sb.append(time);
        sb.append(executeId);
        sb.append(partnerKey);
        String sign = "";
        try {
            sign = CryptUtil.md5(sb.toString());
        } catch (Exception e) {
            throw ErrorCodeException.valueOf("签名失败");
        }
        return sign;
    }


    public RespBean getUploadParam(String fileId,String type){
        String fsUrl = "";
        String partnerId = fileServiceConfig.getPartnerId();
        String partnerKey = fileServiceConfig.getPartnerKey();
        String classId = type;
        if("video".equals(type)){
            classId = fileServiceConfig.getVodId();
        }
        if ("img".equals(type)){
            classId = fileServiceConfig.getImgId();
        }
        String executeId = fileId;

        long time = new Date().getTime();
        String sign = sign(partnerId,time,fileId,partnerKey);
        UploadFileDto uploadFileDto = new UploadFileDto();
        uploadFileDto.setFsUrl(fsUrl);
        uploadFileDto.setPartnerId(partnerId);
        uploadFileDto.setTime(String.valueOf(time));
        uploadFileDto.setExecuteId(executeId);
        uploadFileDto.setSign(sign);
        uploadFileDto.setClassId(classId);
        return RespBean.success("",uploadFileDto);
    }

    @Override
    public String getDownloadUrl(String fileId) {
//        String fsUrl = sysKeyValueService.get(KeyConstant.DOWNLOAD_FS_URL);
//        String partnerId = sysKeyValueService.get(KeyConstant.UPLOAD_PARTNER_ID);
//        String partnerKey = sysKeyValueService.get(KeyConstant.UPLOAD_PARTNER_KEY);
        String fsUrl = "http://hlwyy.gzsums.net/fileServer-test/download";
        String partnerId = fileServiceConfig.getPartnerId();
        String partnerKey = fileServiceConfig.getPartnerKey();
        long time = new Date().getTime();
        String sign = sign(partnerId,time,fileId,partnerKey);
        return FormatUtils.format("{}?partnerId={}&time={}&sign={}&fileId={}", fsUrl,partnerId, String.valueOf(time), sign,fileId);
    }

}
