package com.zc.common.web.menu.renderer;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import com.zc.common.model.Menu;
import com.zc.common.web.util.MenuTreecell;

public class MenuTreeitemRenderer implements TreeitemRenderer, Serializable {
	private static final long serialVersionUID = -4912152337538385301L;

	public void render(final Treeitem item, Object data) throws Exception {
		SimpleTreeNode treeNode = (SimpleTreeNode) data;
		Menu menu = (Menu) treeNode.getData();

		MenuTreecell tcMenu = new MenuTreecell();
		tcMenu.setLabel(menu.getName());
		tcMenu.setUrl(menu.getUrl());
		if (StringUtils.isNotBlank(menu.getIcon())) {
			tcMenu.setImage("/images/icons/16/" + menu.getIcon());
		}

		Treerow tr = null;
		if (item.getTreerow() == null) {
			tr = new Treerow();
			tr.setParent(item);
		} else {
			tr = item.getTreerow();
			tr.getChildren().clear();
		}

		tcMenu.setParent(tr);
		tcMenu.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				item.setOpen(!item.isOpen());
			}
		});
	}
}
