package com.ichaoj.ycl.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ichaoj.ycl.client.beans.ApiData;
import com.ichaoj.ycl.client.beans.ApiDataOrder;
import com.ichaoj.ycl.client.beans.SignatoryApiOrder;
import com.ichaoj.ycl.client.beans.StoreApiOrder;
import com.ichaoj.ycl.client.compoment.*;
import com.ichaoj.ycl.client.enums.Env;
import com.ichaoj.ycl.client.enums.YclServiceEnum;
import com.yiji.openapi.tool.fastjson.JSONObject;
import com.yiji.openapi.tool.util.DigestUtil;
import com.yiji.openapi.tool.util.DigestUtil.DigestALGEnum;

public class YclClient {
	

	private String appKey;
	private String appSecret;
	private Env env;

	
	public YclClient(String appKey,String appSecret,Env env){
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.env = env;
	}

	public ResultBase ping(){
		ResultBase resultBase = new ResultBase();
		try {
			String json = YclNetUtil.doPost(this.env.getCode()+YclServiceEnum.PING.getCode(), null, 30000, 30000);
			JSONObject jsonObject = JSONObject.parseObject(json);
			resultBase.setSuccess(jsonObject.getBooleanValue("success"));
			resultBase.setMessage(jsonObject.getString("message"));
			return resultBase;
		} catch (IOException e) {
			resultBase.setMessage(e.getMessage());
			return resultBase;
		}
	}
	
	public StoreResult filePreservation(StoreApiOrder storeApiOrder){
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
			String json = YclNetUtil.doPost(env.getCode()+YclServiceEnum.STORE.getCode(), params, 30000, 30000);
			
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
	
	public StoreResult signatory(SignatoryApiOrder order){
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
		
		for(int i = 0; i < order.getYclSignatoryList().size(); i++){

			if(order.getYclSignatoryList().get(i).getEmail() != null){
				params.put("yclSignatoryList["+i+"].email", order.getYclSignatoryList().get(i).getEmail());
			}
			if(order.getYclSignatoryList().get(i).getPhone() != null){
				params.put("yclSignatoryList["+i+"].phone", order.getYclSignatoryList().get(i).getPhone());
			}


			params.put("yclSignatoryList["+i+"].groupChar", order.getYclSignatoryList().get(i).getGroupChar());
			params.put("yclSignatoryList["+i+"].groupName", order.getYclSignatoryList().get(i).getGroupName());
			params.put("yclSignatoryList["+i+"].realName", order.getYclSignatoryList().get(i).getRealName());
			params.put("yclSignatoryList["+i+"].sealType", order.getYclSignatoryList().get(i).getSealType());
			params.put("yclSignatoryList["+i+"].signatoryAuto", order.getYclSignatoryList().get(i).getSignatoryAuto());
			params.put("yclSignatoryList["+i+"].signatoryUserType", order.getYclSignatoryList().get(i).getSignatoryUserType());
			params.put("yclSignatoryList["+i+"].signatoryTime", order.getYclSignatoryList().get(i).getSignatoryTime());


			params.put("yclSignatoryList["+i+"].keywords", order.getYclSignatoryList().get(i).getKeywords());
			if (order.getYclSignatoryList().get(i).getSignatureX() != null){
				params.put("yclSignatoryList["+i+"].signatureX", String.valueOf(order.getYclSignatoryList().get(i).getSignatureX()));
			}
			if(order.getYclSignatoryList().get(i).getSignatureY() !=null){
				params.put("yclSignatoryList["+i+"].signatureY", String.valueOf(order.getYclSignatoryList().get(i).getSignatureY()));
			}
			if(order.getYclSignatoryList().get(i).getSignaturePage() != null){
				params.put("yclSignatoryList["+i+"].signaturePage", String.valueOf(order.getYclSignatoryList().get(i).getSignaturePage()));
			}

			if(order.getYclSignatoryList().get(i).getCertNo() != null){
				params.put("yclSignatoryList["+i+"].certNo", order.getYclSignatoryList().get(i).getCertNo());
				params.put("yclSignatoryList["+i+"].certType", order.getYclSignatoryList().get(i).getCertType());
			}

		}
		
		String signStr = DigestUtil.digest(params, this.appSecret, DigestALGEnum.MD5);
		params.put("sign", signStr);
		try {
			String json = YclNetUtil.doPost(env.getCode()+YclServiceEnum.SIGNATORY.getCode(), params, 30000, 30000);
			
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
     * @param storeNo 存储编号
     */
	public byte[] downloadFile(String storeNo){
        Map<String, String> params = new HashMap<>(3);
        params.put("appKey", this.appKey);
        params.put("appSecret", this.appSecret);
        params.put("storeNo",storeNo);

        try {
            return YclNetUtil.doGetDownLoad(env.getCode() +YclServiceEnum.FILE_DOWNLOAD.getCode(), params);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String doGet(YclServiceEnum yclServiceEnum, Map<String, String> params) throws IOException {
	    String reqUrl = env.getCode()+yclServiceEnum.getCode();
	    return YclNetUtil.doGet(reqUrl,params);
    }

    private String  doPost(YclServiceEnum yclServiceEnum,Map<String, String> params, int connectTimeout,
                           int readTimeout) throws IOException {
        String reqUrl = env.getCode()+yclServiceEnum.getCode();
        return YclNetUtil.doPost(reqUrl,params,connectTimeout,readTimeout);
    }


}
