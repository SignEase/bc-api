package com.ichaoj.ycl.client.enums;

/**
 * API list enum
 */
public enum YclServiceEnum {
	/***/
	PING("/api/ping.json", "连接测试服务"),
	STORE("/api/filePreservation.json", "保全服务"),
	/** OCSV: on-chain storage & validation **/
	OCSV("/api/ocsv.json", "上链存证服务"),
	SIGNATORY("/api/signatory.json", "签章服务"),
	FILE_DOWNLOAD("/api/fileNotary.json", "文件下载");

	private String code;
	private String message;

	/**
	 * Get all enum
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
	 * Get All Enum Value
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
