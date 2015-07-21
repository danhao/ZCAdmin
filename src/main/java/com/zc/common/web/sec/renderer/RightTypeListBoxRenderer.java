package com.zc.common.web.sec.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.common.web.util.SecRightType;

public class RightTypeListBoxRenderer implements ListitemRenderer {
	public void render(Listitem item, Object data) throws Exception {
		SecRightType rightType = (SecRightType) data;
		Listcell listCell = new Listcell(rightType.getName());
		listCell.setParent(item);
		item.setAttribute(Constants.DATA, data);
	}

}
