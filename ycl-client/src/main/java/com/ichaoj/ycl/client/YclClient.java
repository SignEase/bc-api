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
	
	private YclClient(){}
	
	private String appKey;
	private String appSecret;
	private Env env;
	private String[] group = new String[]{"a","b","c","d","e","f","g","h","i","j","k"};
	private String[] groupName = new String[]{"甲方","乙方","丙方","丁方","戊方","己方","庚方","辛方","壬方","癸方","子方"};
	
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
	
	public ResultBase filePreservation(StoreApiOrder storeApiOrder){
		ResultBase resultBase = new ResultBase();
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
	
	public ResultBase signatory(SignatoryApiOrder order){
		ResultBase resultBase = new ResultBase();
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
			params.put("yclSignatoryList["+i+"].email", order.getYclSignatoryList().get(i).getEmail());
			params.put("yclSignatoryList["+i+"].groupChar", group[i]);
			params.put("yclSignatoryList["+i+"].groupName", groupName[i]);
			params.put("yclSignatoryList["+i+"].phone", order.getYclSignatoryList().get(i).getPhone());
			params.put("yclSignatoryList["+i+"].realName", order.getYclSignatoryList().get(i).getRealName());
			params.put("yclSignatoryList["+i+"].sealType", order.getYclSignatoryList().get(i).getSealType());
			params.put("yclSignatoryList["+i+"].signatoryAuto", order.getYclSignatoryList().get(i).getSignatoryAuto());
			params.put("yclSignatoryList["+i+"].signatoryUserType", order.getYclSignatoryList().get(i).getSignatoryUserType());
			params.put("yclSignatoryList["+i+"].signatoryTime", order.getYclSignatoryList().get(i).getSignatoryTime());
			params.put("yclSignatoryList["+i+"].signatureX", String.valueOf(order.getYclSignatoryList().get(i).getSignatureX()));
			params.put("yclSignatoryList["+i+"].signatureY", String.valueOf(order.getYclSignatoryList().get(i).getSignatureY()));
			params.put("yclSignatoryList["+i+"].signaturePage", String.valueOf(order.getYclSignatoryList().get(i).getSignaturePage()));
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
	
	public ApiDataResult data(ApiDataOrder order) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ApiDataResult resultBase = new ApiDataResult();
		try {
			order.check();
		} catch (Exception e) {
			resultBase.setMessage(e.getMessage());
			return resultBase;
		}
		
		Map<String, String> params = new HashMap<>();
		params.put("appKey", this.appKey);
		params.put("appSecret", this.appSecret);
		
		Method[] methods = ApiData.class.getDeclaredMethods();
		for(int i = 0; i < order.getApiDatas().size(); i++){
			for (Method method : methods) {
				if(method.getName().startsWith("get")) {
					String methodName = method.getName();
					String returnType = method.getGenericReturnType().toString();
					String propertyName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
					if("class java.lang.Long".equals(returnType)) {
						Method  method2 = order.getApiDatas().get(i).getClass().getDeclaredMethod(methodName);
						Object object = method2.invoke(order.getApiDatas().get(i));
						if(object != null) {
							params.put("apiDatas["+i+"]." + propertyName, String.valueOf((Long)object));
						}
					}else if("class java.util.Date".equals(returnType)){
						Method  method2 = order.getApiDatas().get(i).getClass().getDeclaredMethod(methodName);
						Object object = method2.invoke(order.getApiDatas().get(i));
						if(object != null) {
							if(!"null".equalsIgnoreCase(String.valueOf(DateUtil.dtSimpleYmdhmsDate((Date)object)))) {
								params.put("apiDatas["+i+"]." + propertyName, String.valueOf(DateUtil.dtSimpleYmdhmsDate((Date)object)));
							}
						}
					}else if("class java.lang.String".equals(returnType)){
						Method  method2 = order.getApiDatas().get(i).getClass().getDeclaredMethod(methodName);
						Object object = method2.invoke(order.getApiDatas().get(i));
						if(object != null) {
							String str = String.valueOf((String)object);
							if(str != "" || str.length() > 0) {
								params.put("apiDatas["+i+"]." + propertyName, str);
							}
						}
					}
				}
			}
		}
		String signStr = DigestUtil.digest(params, this.appSecret, DigestALGEnum.MD5);
		params.put("sign", signStr);
		try {
			String json = YclNetUtil.doPost(env.getCode()+YclServiceEnum.DATA.getCode(), params, 30000, 30000);
			
			JSONObject jsonObject = JSONObject.parseObject(json);
			resultBase.setSuccess(jsonObject.getBooleanValue("success"));
			resultBase.setMessage(jsonObject.getString("message"));
			resultBase.setHash(jsonObject.getString("hash"));
			return resultBase;
		} catch (IOException e) {
			resultBase.setMessage(e.getMessage());
			return resultBase;
		}
	}
}
