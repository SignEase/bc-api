package com.ichaoj.ycl.client;

import com.ichaoj.ycl.client.beans.Ocsv;
import com.ichaoj.ycl.client.beans.OcsvRequst;
import com.ichaoj.ycl.client.beans.SignatoryApiOrder;
import com.ichaoj.ycl.client.beans.StoreApiOrder;
import com.ichaoj.ycl.client.compoment.ResultBase;
import com.ichaoj.ycl.client.compoment.ResultInfo;
import com.ichaoj.ycl.client.compoment.StoreResult;
import com.ichaoj.ycl.client.compoment.YclNetUtil;
import com.ichaoj.ycl.client.enums.Env;
import com.ichaoj.ycl.client.enums.YclServiceEnum;
import com.yiji.openapi.tool.fastjson.JSONObject;
import com.yiji.openapi.tool.util.DigestUtil;
import com.yiji.openapi.tool.util.DigestUtil.DigestALGEnum;
import com.yiji.openapi.tool.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YclClient {
    private String appKey;
    private String appSecret;
    private Env env;

    public YclClient(String appKey, String appSecret, Env env) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.env = env;
    }

    public ResultBase ping() {
        ResultBase resultBase = new ResultBase();
        try {
            String json = YclNetUtil.doPost(this.env.getCode() + YclServiceEnum.PING.getCode(), null, 30000, 30000);
            JSONObject jsonObject = JSONObject.parseObject(json);
            resultBase.setSuccess(jsonObject.getBooleanValue("success"));
            resultBase.setMessage(jsonObject.getString("message"));
            return resultBase;
        } catch (IOException e) {
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    public StoreResult filePreservation(StoreApiOrder storeApiOrder) {
        StoreResult resultBase = new StoreResult();
        Map<String, String> params = new HashMap<>();
        storeApiOrder.check();
        //设置参数=================================================================
        params.put("appKey", this.appKey);
        params.put("appSecret", this.appSecret);
        params.put("fileName", storeApiOrder.getFileName());
        params.put("storeName", storeApiOrder.getStoreName());
        params.put("fileBase64", storeApiOrder.getFileBase64());
        params.put("isPublic", storeApiOrder.getIsPublic());
        //设置参数=================================================================
        String signStr = DigestUtil.digest(params, this.appSecret, DigestALGEnum.MD5);
        params.put("sign", signStr);
        try {
            String json = YclNetUtil.doPost(env.getCode() + YclServiceEnum.STORE.getCode(), params, 30000, 30000);

            JSONObject jsonObject = JSONObject.parseObject(json);
            resultBase.setSuccess(jsonObject.getBooleanValue("success"));
            resultBase.setMessage(jsonObject.getString("message"));
            resultBase.setStoreNo(jsonObject.getString("storeNo"));
            return resultBase;
        } catch (IOException e) {
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    public StoreResult signatory(SignatoryApiOrder order) {
        StoreResult resultBase = new StoreResult();
        try {
            order.check();
        } catch (Exception e) {
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }

        Map<String, String> params = new HashMap<>();
        params.put("yclDataStore.appKey", this.appKey);
        params.put("yclDataStore.appSecret", this.appSecret);
        params.put("pdfFileBase64", order.getPdfFileBase64());

        params.put("yclDataStore.userBizNumber", order.getYclDataStore().getUserBizNumber());
        params.put("yclDataStore.storeName", order.getYclDataStore().getStoreName());
        params.put("yclDataStore.isPublic", order.getYclDataStore().getIsPublic());

        for (int i = 0; i < order.getYclSignatoryList().size(); i++) {

            if (order.getRealNameMask() != null) {
                order.getYclSignatoryList().get(i).setRealNameMask(order.getRealNameMask());
            }
            if (order.getCertNoMask() != null) {
                order.getYclSignatoryList().get(i).setCertNoMask(order.getCertNoMask());
            }
            // 设置必填参数
            params.put("yclSignatoryList[" + i + "].realName", order.getYclSignatoryList().get(i).getRealName());
            params.put("yclSignatoryList[" + i + "].sealType", order.getYclSignatoryList().get(i).getSealType());
            params.put("yclSignatoryList[" + i + "].signatoryAuto", order.getYclSignatoryList().get(i).getSignatoryAuto());
            params.put("yclSignatoryList[" + i + "].signatoryUserType", order.getYclSignatoryList().get(i).getSignatoryUserType());
            params.put("yclSignatoryList[" + i + "].signatoryTime", order.getYclSignatoryList().get(i).getSignatoryTime());
            params.put("yclSignatoryList[" + i + "].groupName", order.getYclSignatoryList().get(i).getGroupName());
            params.put("yclSignatoryList[" + i + "].groupChar", order.getYclSignatoryList().get(i).getGroupChar());
            // 设置可选参数


            if (order.getYclSignatoryList().get(i).getEmail() != null) {
                params.put("yclSignatoryList[" + i + "].email", order.getYclSignatoryList().get(i).getEmail());
            }
            if (order.getYclSignatoryList().get(i).getPhone() != null) {
                params.put("yclSignatoryList[" + i + "].phone", order.getYclSignatoryList().get(i).getPhone());
            }
            if (StringUtils.isNotEmpty(order.getYclSignatoryList().get(i).getKeywords())) {
                params.put("yclSignatoryList[" + i + "].keywords", order.getYclSignatoryList().get(i).getKeywords());
            }
            if (order.getYclSignatoryList().get(i).getSignatureX() != null) {
                params.put("yclSignatoryList[" + i + "].signatureX", String.valueOf(order.getYclSignatoryList().get(i).getSignatureX()));
            }
            if (order.getYclSignatoryList().get(i).getSignatureY() != null) {
                params.put("yclSignatoryList[" + i + "].signatureY", String.valueOf(order.getYclSignatoryList().get(i).getSignatureY()));
            }
            if (order.getYclSignatoryList().get(i).getSignaturePage() != null) {
                params.put("yclSignatoryList[" + i + "].signaturePage", String.valueOf(order.getYclSignatoryList().get(i).getSignaturePage()));
            }

            if (order.getYclSignatoryList().get(i).getCertNo() != null) {
                params.put("yclSignatoryList[" + i + "].certNo", order.getYclSignatoryList().get(i).getCertNo());
                params.put("yclSignatoryList[" + i + "].certType", order.getYclSignatoryList().get(i).getCertType());
            }

            if (order.getYclSignatoryList().get(i).getSealPurpose() != null) {
                params.put("yclSignatoryList[" + i + "].sealPurpose", order.getYclSignatoryList().get(i).getSealPurpose());
            }

            if (order.getYclSignatoryList().get(i).getRealNameMask() != null) {
                params.put("yclSignatoryList[" + i + "].realNameMask", order.getYclSignatoryList().get(i).getRealNameMask() + "");
            }

            if (order.getYclSignatoryList().get(i).getCertNoMask() != null) {
                params.put("yclSignatoryList[" + i + "].certNoMask", order.getYclSignatoryList().get(i).getCertNoMask() + "");
            }

            if (order.getYclSignatoryList().get(i).getSealSn() != null) {
                params.put("yclSignatoryList[" + i + "].sealSn", order.getYclSignatoryList().get(i).getSealSn());
            }
        }

        String signStr = DigestUtil.digest(params, this.appSecret, DigestALGEnum.MD5);
        params.put("sign", signStr);
        try {
            String json = YclNetUtil.doPost(env.getCode() + YclServiceEnum.SIGNATORY.getCode(), params, 30000, 30000);

            JSONObject jsonObject = JSONObject.parseObject(json);
            resultBase.setSuccess(jsonObject.getBooleanValue("success"));
            resultBase.setMessage(jsonObject.getString("message"));
            resultBase.setStoreNo(jsonObject.getString("storeNo"));
            return resultBase;
        } catch (IOException e) {
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    /**
     * 云存文件取回
     *
     * @param storeNo 存储编号
     */
    public byte[] downloadFile(String storeNo) {
        Map<String, String> params = new HashMap<>(3);
        params.put("appKey", this.appKey);
        params.put("appSecret", this.appSecret);
        params.put("storeNo", storeNo);

        try {
            return YclNetUtil.doGetDownLoad(env.getCode() + YclServiceEnum.FILE_DOWNLOAD.getCode(), params);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author SOFAS
     * @description     数据存证
     * @date  2019/9/19
     * @param
     * @return com.ichaoj.ycl.client.compoment.ResultInfo
     **/
    /**
     * @author SOFAS
     * @description         数据存证
     * @date  2019/9/19
      * @param l            待存证的数据
     * @param storeId       最佳存证信息的存证编号（有存证编号时会对已有的存证主体进行追加操作，没有则会创建一个新的存证主体）
     * @param p             是否公开（选择公开则被查询时会直接显示所有信息，否则将会对关键信息脱敏）
     * @param callback      追加存证成功后的回调接口（没有时存证成功后不会自动回调，需要手动到查询小插件查看是否已存证上链成功）
     * @return com.ichaoj.ycl.client.compoment.ResultInfo
     **/
    public ResultInfo ocsv(List<Ocsv> l, Long storeId, String p, String callback){
        if (l == null || l.size() == 0){
            return ResultInfo.error("需要存证的数据不能为空");
        }
        if (this.appKey == null || this.appKey.length() == 0){
            return ResultInfo.error("appKey不能为空");
        }
        if (this.appSecret == null || this.appSecret.length() == 0){
            return ResultInfo.error("appSecret不能为空");
        }
        String s = ocsvDeal(l);
        return YclNetUtil.ocsv(new OcsvRequst(this.appKey, this.appSecret, env.getCode(), storeId, s, p, callback));
    }

    private String ocsvDeal(List<Ocsv> l){
        for (Ocsv o : l){
            List<Ocsv> sb = o.getSubOcsv();
            if (sb != null && sb.size() != 0){
                String s = ocsvDeal(sb);
                o.setSubOcsvStr(s);
            }
        }
        return JSONObject.toJSONString(l);
    }

    private String doGet(YclServiceEnum yclServiceEnum, Map<String, String> params) throws IOException {
        String reqUrl = env.getCode() + yclServiceEnum.getCode();
        return YclNetUtil.doGet(reqUrl, params);
    }

    private String doPost(YclServiceEnum yclServiceEnum, Map<String, String> params, int connectTimeout,
                          int readTimeout) throws IOException {
        String reqUrl = env.getCode() + yclServiceEnum.getCode();
        return YclNetUtil.doPost(reqUrl, params, connectTimeout, readTimeout);
    }
}
