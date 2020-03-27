package com.ichaoj.sxq.client.beans;

import lombok.Data;

import java.util.List;

@Data
public class OcsvRequst {
    /**
     * 请求公钥
     */
    private String appKey;
    /**
     * 请求密钥
     */
    private String appSecret;
    /**
     * 请求环境
     */
    private String env;
    /**
     * 存证编号
     */
    private Long storeId;
    /**
     * 存证数据
     */
    private String data;
    /**
     * 数据验证
     */
    private String sign;
    /**
     * 是否公开
     */
    private String isPublic;
    /**
     * 回调的接口
     */
    private String callBackUrl;
    /**
     * 存证的名称
     */
    private String storeName;

    public OcsvRequst() { }

    public OcsvRequst(String appKey, String appSecret, String env, Long storeId, String data, String isPublic, String callBackUrl, String storeName) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.env = env;
        this.storeId = storeId;
        this.data = data;
        this.isPublic = isPublic;
        this.callBackUrl = callBackUrl;
        this.storeName = storeName;
    }
}
