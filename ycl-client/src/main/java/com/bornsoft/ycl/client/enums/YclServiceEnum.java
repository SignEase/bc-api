package com.bornsoft.ycl.client.enums;

public enum YclServiceEnum {

	PING("/api/ping.json", "连接测试服务"),
	STORE("/api/filePreservation.json", "保全服务"),
	SIGNATORY("/api/signatory.json", "签章服务"),
	DATA("/api/data.json", "齐聚邦数据保存"),
	;

	private String code;
	private String message;

	/**
	 * 获取全部枚举
	 * 
	 * @return List<YclServiceEnum>
	 */
	public static java.util.List<YclServiceEnum> getAllEnum() {
		java.util.List<YclServiceEnum> list = new java.util.ArrayList<YclServiceEnum>(values().length);
		for (YclServiceEnum _enum : values()) {
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
		for (YclServiceEnum _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private YclServiceEnum(String code, String message) {
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
