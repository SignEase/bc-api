package com.ichaoj.ycl.client.compoment;

import org.apache.commons.lang3.StringUtils;

public class ResultBase {

	private boolean success = false;

	private String message = StringUtils.EMPTY;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 存储编号
	 */
	private String storeNo;

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	@Override
	public String toString() {
		return "{" +
				"success:" + success +
				", message:'" + message + '\'' +
				", storeNo:'" + storeNo + '\'' +
				'}';
	}
}
