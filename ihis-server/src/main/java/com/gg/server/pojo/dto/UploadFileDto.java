package com.gg.server.pojo.dto;

/**
 * @author: GG
 * @date: 2021/4/21 11:30 上午
 */
public class UploadFileDto {
    /**
     * 服务器URL
     */
    private String fsUrl;

    /**
     * 合作方ID
     */
    private String partnerId;

    /**
     * 时间戳
     */
    private String time;

    /**
     * 任务标识ID
     */
    private String executeId;

    /**
     * 签名
     */
    private String sign;

    /**
     * 上传文件类型ID(可空)
     */
    private String classId;

    /**
     * 上传用户名(可空)
     */
    private String username;

    public String getFsUrl() {
        return fsUrl;
    }

    public void setFsUrl(String fsUrl) {
        this.fsUrl = fsUrl;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExecuteId() {
        return executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
