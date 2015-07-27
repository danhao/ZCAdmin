package com.zc.common.web;

import java.io.Serializable;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Panel;

import com.zc.common.util.Config;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;

public class LoginCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = 2091887899877482239L;
	private transient Panel login_panel;

	public LoginCtrl() {
		super();
	}

	@Override
	public void init() {
		SecurityContextHolder.clearContext();
		desktop.getPage("login_page").setTitle(Config.getConfig().get(Constants.APP_TITLE) + "-管理员登陆");
		login_panel.setTitle(Config.getConfig().get(Constants.LOGIN_PANEL_TITLE));
	}

	public void onCreate$loginwin(Event event) throws Exception {
		init();
	}
}
