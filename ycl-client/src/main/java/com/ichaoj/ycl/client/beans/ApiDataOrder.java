package com.ichaoj.ycl.client.beans;

import java.util.ArrayList;
import java.util.List;

import com.ichaoj.ycl.client.util.Assert;

public class ApiDataOrder{
	

	private static final long serialVersionUID = -4070695620476033241L;

	private List<ApiData> apiDatas = new ArrayList<ApiData>();
	
	private String clientAccount;
	
	public void check(){
		Assert.notNull(apiDatas, "上传数据不能为空！");
	}
	

	public List<ApiData> getApiDatas() {
		return apiDatas;
	}


	public void setApiDatas(List<ApiData> apiDatas) {
		this.apiDatas = apiDatas;
	}


	public String getClientAccount() {
		return clientAccount;
	}


	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}


	@Override
	public String toString() {
		return "ApiDataOrder [apiDatas=" + apiDatas.toString() + ", clientAccount=" + clientAccount + "]";
	}
	
	
}