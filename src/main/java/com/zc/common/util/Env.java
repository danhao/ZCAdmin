package com.zc.common.util;

public enum Env {
	ENV_DEV("dev", "统计管理平台"), ENV_STAT("stat", "统计管理平台"), ENV_ONLINE_160("online_160", "在线管理平台"), ENV_ONLINE_49("online_49", "在线管理平台"), ENV_ONLINE_183("online_183", "在线管理平台"), ENV_ZHSH_UC("zhsh_uc", "在线管理平台"), ENV_ZHSH_SK("zhsh_sk", "在线管理平台");

	private String code;
	private String name;

	private Env(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
