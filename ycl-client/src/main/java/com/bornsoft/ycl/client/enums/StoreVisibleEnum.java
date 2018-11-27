package com.bornsoft.ycl.client.enums;

public enum StoreVisibleEnum {

	PRIVATE("PRIVATE", "私人"),
	PUBLIC("PUBLIC", "开放");

	private String code;
	private String message;

	/**
	 * 获取全部枚举
	 * 
	 * @return List<StoreVisibleEnum>
	 */
	public static java.util.List<StoreVisibleEnum> getAllEnum() {
		java.util.List<StoreVisibleEnum> list = new java.util.ArrayList<StoreVisibleEnum>(values().length);
		for (StoreVisibleEnum _enum : values()) {
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
		for (StoreVisibleEnum _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private StoreVisibleEnum(String code, String message) {
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
