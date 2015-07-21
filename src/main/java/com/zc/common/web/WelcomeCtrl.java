package com.zc.common.web;

import java.io.Serializable;

import org.zkoss.zk.ui.event.Event;

import com.zc.common.util.Config;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;

public class WelcomeCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -5559198515711412366L;
	private transient String env;
//	private transient ItemService itemService;

	public WelcomeCtrl() {
		super();
	}

	@Override
	public void init() {
		env = Config.getConfig().get(Constants.ENV);
	}

	public void onCreate$welcomeWindow(Event event) throws Exception {
		init();
	}

//	public void onClick$btn_reload_data() throws Exception {
//		itemService.loadItems();
//		MsgBox.info("缓存重新加载完毕！");
//	}
//
//	public ItemService getItemService() {
//		return itemService;
//	}
//
//	public void setItemService(ItemService itemService) {
//		this.itemService = itemService;
//	}
}
