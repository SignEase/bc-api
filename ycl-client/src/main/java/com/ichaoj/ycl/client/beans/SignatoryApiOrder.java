package com.ichaoj.ycl.client.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.ichaoj.ycl.client.util.Assert;


public class SignatoryApiOrder {

	private Pattern pattern = Pattern.compile("^\\S*?(?=@{1})");
	
	private YclDataStore yclDataStore;
	
	private List<YclSignatory> yclSignatoryList = new ArrayList<YclSignatory>();
	
	private String pdfFileBase64;
	
	
	public String getFileName(){
		return pattern.matcher(this.pdfFileBase64).group(0);
	}
	
	
	public void check(){
		//检查合同
		Assert.notNull(yclDataStore.getUserBizNumber(), "UserBizNumber不能为空");
		Assert.notNull(yclDataStore.getStoreName(), "StoreName不能为空");
		Assert.notNull(yclDataStore.getIsPublic(), "IsPublic不能为空");
		Assert.notNull(yclSignatoryList, "yclSignatoryList");
		Assert.isTrue(pattern.matcher(this.pdfFileBase64).find(),"pdfFileBase64格式错误，需要格式:文件名@base64编码字符串");
		//检查签约数据
		for(YclSignatory ys:yclSignatoryList){
			Assert.notNull(ys, "YclSignatory");
			Assert.notNull(ys.getRealName(), "RealName不能为空");
			Assert.notNull(ys.getSignatoryTime(), "SignatoryTime不能为空");
			Assert.notNull(ys.getSignatoryAuto(), "SignatoryAuto不能为空");
			Assert.notNull(ys.getSignatoryUserType(), "SignatoryUserType不能为空");
			if(!ys.getSignatoryUserType().equalsIgnoreCase("ENTERPRISE")&&!ys.getSignatoryUserType().equalsIgnoreCase("PERSONAL")){
				Assert.notNull(null, "signatory_user_type参数有误！");
			}

			if(ys.getPhone() == null && ys.getEmail() == null){
				throw new IllegalArgumentException("phone 与 email 必选一个");
			}
			Assert.notNull(ys.getSealType(), "sealType不能为空");
		}
		
		//检查签约信息
	}

	public List<YclSignatory> getYclSignatoryList() {
		return yclSignatoryList;
	}

	public void setYclSignatoryList(List<YclSignatory> yclSignatoryList) {
		this.yclSignatoryList = yclSignatoryList;
	}


	public YclDataStore getYclDataStore() {
		return yclDataStore;
	}

	public void setYclDataStore(YclDataStore yclDataStore) {
		this.yclDataStore = yclDataStore;
	}

	public String getPdfFileBase64() {
		return pdfFileBase64;
	}

	public void setPdfFileBase64(String pdfFileBase64) {
		this.pdfFileBase64 = pdfFileBase64;
	}

}
