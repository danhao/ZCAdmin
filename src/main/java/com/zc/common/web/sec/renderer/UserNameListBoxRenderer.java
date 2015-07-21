package com.zc.common.web.sec.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.model.SecUser;
import com.zc.common.util.Constants;

public class UserNameListBoxRenderer implements ListitemRenderer {
	public void render(Listitem item, Object data) throws Exception {
		SecUser user = (SecUser) data;
		Listcell listCell = new Listcell(user.getName());
		listCell.setParent(item);
		
		item.setValue(user.getId());
		item.setAttribute(Constants.DATA, data);
	}

}
