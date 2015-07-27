package com.zc.common.web.util;

import org.zkoss.zul.Messagebox;

public class MsgBox extends Messagebox {
	public MsgBox() {
	}
	
	public static int info(String message) throws InterruptedException {
		return show(message, "消息提示", Messagebox.OK, Messagebox.INFORMATION);
	}

	public static int alert(String message) throws InterruptedException {
		return show(message, "消息提示", Messagebox.OK, Messagebox.ERROR);
	}

	public static int delAlert(String message) throws InterruptedException {
		return show(message, "删除确认", Messagebox.OK + Messagebox.CANCEL, Messagebox.QUESTION);
	}

	public static int delSelectedAlert(String object) throws InterruptedException {
		return show(String.format("确认删除选中的%s吗？", object), "删除确认", Messagebox.OK + Messagebox.CANCEL, Messagebox.QUESTION);
	}
	
}
