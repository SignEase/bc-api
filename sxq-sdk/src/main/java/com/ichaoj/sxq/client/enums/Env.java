package com.ichaoj.sxq.client.enums;

public enum Env {

	ONLINE("https://sxqian.com", "线上环境"),
	TEST("https://mock.sxqian.com", "测试环境"),
	LOCAL_TEST("http://192.169.2.207:8086", "本地联调环境"),
	LOCAL("http://127.0.0.1:7878", "本地环境"),
	LOCAL1("http://192.168.31.216:7878", "本地环境");

	private String code;
	private String message;

	/**
	 * 获取全部枚举
	 * 
	 * @return List<Env>
	 */
	public static java.util.List<Env> getAllEnum() {
		java.util.List<Env> list = new java.util.ArrayList<Env>(values().length);
		for (Env _enum : values()) {
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
		for (Env _enum : values()) {
			list.add(_enum.getCode());
		}
		return list;
	}

	private Env(String code, String message) {
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
