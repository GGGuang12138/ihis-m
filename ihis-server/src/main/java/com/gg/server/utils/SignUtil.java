package com.gg.server.utils;

import com.gg.server.config.Exception.ErrorCodeException;

/**
 * 签名工具
 * @author: GG
 * @date: 2021/5/30 12:10 下午
 */
public class SignUtil {


    /**
     * 签名工具类
     * @param partnerId
     * @param time
     * @param executeId
     * @param partnerKey
     * @return
     */
    public static String sign(String partnerId,long time,String executeId,String partnerKey){
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
}
