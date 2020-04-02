package com.ichaoj.sxq.client.enums;

/**
 * API list enum
 */
public enum SxqServiceEnum {
	/***/
	PING("/api/ping.json", "连接测试服务"),
	STORE("/api/filePreservation.json", "保全服务"),
	/** OCSV: on-chain storage & validation **/
	OCSV("/api/ocsv.json", "上链存证服务"),
	SIGNATORY("/api/draftContract.json", "签章服务"),
	FILE_DOWNLOAD("/api/checkAndDownload.json", "文件下载"),
	QUERY_CUSTOMIZED_LOGO("/api/queryCustomizedLogo.json", "查询自定义logo"),
	SET_CUSTOMIZED_LOGO("/api/setCustomizedLogo.json", "设置自定义logo"),;

	private String code;
	private String message;

	/**
	 * Get all enum
	 * @return List<YclServiceEnum>
	 */
	public static java.util.List<SxqServiceEnum> getAllEnum() {
		java.util.List<SxqServiceEnum> list = new java.util.ArrayList<SxqServiceEnum>(values().length);
		for (SxqServiceEnum _enum : values()) {
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
		for (SxqServiceEnum _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private SxqServiceEnum(String code, String message) {
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
