package com.ichaoj.ycl.client.enums;

public enum SealTypeEnum {

	PERSONAL("PERSONAL", "私章"),
	OFFICIAL("OFFICIAL", "公章");

	private String code;
	private String message;

	/**
	 * 获取全部枚举
	 * 
	 * @return List<SealTypeEnum>
	 */
	public static java.util.List<SealTypeEnum> getAllEnum() {
		java.util.List<SealTypeEnum> list = new java.util.ArrayList<SealTypeEnum>(values().length);
		for (SealTypeEnum _enum : values()) {
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
		for (SealTypeEnum _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private SealTypeEnum(String code, String message) {
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
