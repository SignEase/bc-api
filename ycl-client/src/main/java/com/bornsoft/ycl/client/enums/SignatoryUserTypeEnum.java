package com.bornsoft.ycl.client.enums;

public enum SignatoryUserTypeEnum {

	ENTERPRISE("ENTERPRISE", "企业用户"),
	PERSONAL("PERSONAL", "个人用户");

	private String code;
	private String message;

	/**
	 * 获取全部枚举
	 * 
	 * @return List<SignatoryUserTypeEnum>
	 */
	public static java.util.List<SignatoryUserTypeEnum> getAllEnum() {
		java.util.List<SignatoryUserTypeEnum> list = new java.util.ArrayList<SignatoryUserTypeEnum>(values().length);
		for (SignatoryUserTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static java.util.List<String> getAllEnumCode() {
		java.util.List<String> list = new java.util.ArrayList<String>(values().length);
		for (SignatoryUserTypeEnum _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private SignatoryUserTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
