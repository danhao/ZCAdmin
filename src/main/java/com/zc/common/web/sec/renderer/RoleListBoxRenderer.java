package com.zc.common.web.sec.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.model.SecRole;
import com.zc.common.util.Constants;

public class RoleListBoxRenderer implements ListitemRenderer {
	public void render(Listitem item, Object data) throws Exception {
		SecRole role = (SecRole) data;
		Listcell listCell = new Listcell("");
		listCell.setParent(item);

		listCell = new Listcell(role.getName());
		listCell.setParent(item);

		listCell = new Listcell(role.getDescn());
		listCell.setParent(item);

		item.setValue(role.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onRoleListItemDoubleClicked");
	}

}
