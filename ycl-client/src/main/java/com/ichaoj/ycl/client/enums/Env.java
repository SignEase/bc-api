package com.ichaoj.ycl.client.enums;

public enum Env {

	ONLINE("https://yuncunlian.com", "线上环境"),
	TEST("http://mock.beancloud.top", "测试环境"),
	LOCAL_TEST("http://192.169.2.207:8086", "本地测试环境"),
	LOCAL("http://127.0.0.1:8086", "本地环境");

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
