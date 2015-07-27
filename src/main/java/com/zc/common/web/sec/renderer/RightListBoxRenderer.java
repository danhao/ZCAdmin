package com.zc.common.web.sec.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.model.SecRight;
import com.zc.common.service.ModuleService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.SecRightType;

public class RightListBoxRenderer implements ListitemRenderer {
	public void render(Listitem item, Object data) throws Exception {
		SecRight right = (SecRight) data;
		Listcell listCell = new Listcell("");
		listCell.setParent(item);

		listCell = new Listcell(right.getName());
		listCell.setParent(item);

		listCell = new Listcell(ModuleService.getModule(right.getModule()).getName());
		listCell.setParent(item);

		listCell = new Listcell(SecRightType.getSecRightType(right.getType()).getName());
		listCell.setParent(item);

		listCell = new Listcell(right.getDescn());
		listCell.setParent(item);

		item.setValue(right.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onRightListItemDoubleClicked");
	}

}
