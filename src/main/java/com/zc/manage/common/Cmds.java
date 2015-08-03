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
	UPDATE_VIP("1000015", "VIP等级"),
	UPDATE_EMAIL("1000017", "修改Email"),
	UPDATE_MOBILE("1000018", "修改手机号"),
	ADD_REPAYMENT("1000019", "回款记录"),
	ADMIN_CLOSE_DEBT("1000021", "撤单"),
	UPDATE_USER_FILE("1000022", "更新状态"),
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
