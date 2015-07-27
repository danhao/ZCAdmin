package com.zc.common.web.sec.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.model.SecUser;
import com.zc.common.util.Constants;
import com.zc.common.util.Utils;

public class UserListBoxRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		SecUser user = (SecUser) data;
		Listcell lc = new Listcell("");
		lc.setParent(item);

		lc = new Listcell(user.getName());
		lc.setParent(item);

		lc = new Listcell(user.getEnabled() ? "启用" : "禁用");
		lc.setImage("/images/" + (user.getEnabled() ? "checked" : "delete") + ".gif");
		lc.setStyle(Utils.getListCellStyle(user.getEnabled()));
		lc.setParent(item);

		lc = new Listcell(user.getDescn());
		lc.setParent(item);

		lc = new Listcell(DateFormatUtils.format(user.getCreatedAt(), Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		item.setValue(user.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onUserListItemDoubleClicked");
	}

}
