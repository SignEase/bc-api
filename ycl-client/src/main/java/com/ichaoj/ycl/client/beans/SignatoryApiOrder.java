package com.ichaoj.ycl.client.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.ichaoj.ycl.client.util.Assert;
import com.yiji.openapi.tool.util.StringUtils;


public class SignatoryApiOrder {

	private Pattern pattern = Pattern.compile("^\\S*?(?=@{1})");
	
	private YclDataStore yclDataStore;
	
	private List<YclSignatory> yclSignatoryList = new ArrayList<>();
	
	private String pdfFileBase64;
	
	
	public String getFileName(){
		return pattern.matcher(this.pdfFileBase64).group(0);
	}


	public void check(){
		//检查合同
		Assert.notNull(yclDataStore.getUserBizNumber(), "UserBizNumber不能为空");
		Assert.notNull(yclDataStore.getStoreName(), "StoreName不能为空");
		Assert.notNull(yclDataStore.getIsPublic(), "IsPublic不能为空");
		Assert.notNull(yclSignatoryList, "签约人不能为空");

		checkSignatoryGroupNum();

		Assert.isTrue(pattern.matcher(this.pdfFileBase64).find(),"pdfFileBase64格式错误，需要格式:文件名@base64编码字符串");

		for(YclSignatory ys:yclSignatoryList){
			Assert.notNull(ys, "签约人不能为空");
			Assert.notNull(ys.getRealName(), "RealName不能为空");
			Assert.notNull(ys.getSignatoryTime(), "SignatoryTime不能为空");
			Assert.notNull(ys.getSignatoryAuto(), "SignatoryAuto不能为空");
			Assert.notNull(ys.getSignatoryUserType(), "SignatoryUserType不能为空");

			Assert.isTrue(isEnterpriseOrPersonal(ys.getSignatoryUserType()),"signatory_user_type参数有误！");

			checkContactWay(ys.getPhone(),ys.getEmail());
			if(StringUtils.isNotEmpty(ys.getCertNo())){
				Assert.notNull(ys.getCertType(),"CertType不能为空");

			}
			Assert.isTrue(checkSealPosition(ys),"签章定位参数错误");
			Assert.notNull(ys.getGroupChar(), "groupChar不能为空");
			Assert.notNull(ys.getGroupName(), "groupName不能为空");
			Assert.notNull(ys.getSealType(), "sealType不能为空");

		}

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





	/**
	 * 验证签章位置 关键字 与 坐标（x,y,pageNo） 必须有一个不能为空
	 */
	private boolean checkSealPosition(YclSignatory ys){
		if(ys == null){
			return false;
		}

		if(StringUtils.isNotEmpty(ys.getKeywords())){
			return true;
		}else if(ys.getSignatureX() != null && ys.getSignatureY() != null && ys.getSignaturePage() != null){
			return true;
		}else {
			return false;
		}
	}
	/**
	 *  验证联系方式
	 */
	private void checkContactWay(String phone, String email){
		if(StringUtils.isEmpty(phone) && StringUtils.isEmpty(email)){
			throw new IllegalArgumentException("phone 与 email 必选一个");
		}

	}

	private boolean isEnterpriseOrPersonal(String signatoryUserType){
		if (signatoryUserType == null){
			return false;
		}
		return "ENTERPRISE".equalsIgnoreCase(signatoryUserType) || "PERSONAL".equalsIgnoreCase(signatoryUserType);
	}
	/**
	 * 检查签约方
	 */
	private void checkSignatoryGroupNum(){
		int minGroupNum = 2;
		if(yclSignatoryList == null || yclSignatoryList.isEmpty()){
			throw new IllegalArgumentException("缺少签约方");
		}

		Set<String> groupChar = new HashSet<>();
		for (YclSignatory yclSignatory: yclSignatoryList) {
			groupChar.add(yclSignatory.getGroupChar());
			if(groupChar.size() >= minGroupNum){
				return;
			}
		}
		throw new IllegalArgumentException("签约方至少需要" + minGroupNum + "方");

	}
}
