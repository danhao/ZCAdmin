package com.zc.manage.common;

public enum Cmds {
	QUERY_PLAYER("1000004", "查询用户"),
	QUERY_DEBT("1000006", "查询债务"),
	UPDATE_DEBT("1000007", "更新债务"),
	LIST_USER("1000008", "审核列表"),
	VALIDATE_USER("1000005", "审核用户"),
	LIST_CASH("1000011", "提现申请"),
	UPDATE_CASH("1000012", "提现处理"),
	BID_WIN("1000013", "自动中标"),
	DEBT_CLOSE("1000014", "结单"),
	;
	private String cmd;
	private String name;
	public String getCmd() {
		return cmd;
	}
	public String getName() {
		return name;
	}
	private Cmds(String cmd, String name) {
		this.cmd = cmd;
		this.name = name;
	}
	
}
