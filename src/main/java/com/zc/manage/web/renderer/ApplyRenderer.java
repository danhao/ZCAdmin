package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.data.model.DebtEndApply;

public class ApplyRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		DebtEndApply apply = (DebtEndApply) data;
		Listcell lc = new Listcell(String.valueOf(apply.getId()));
		lc.setParent(item);

		lc = new Listcell(String.valueOf(apply.getPlayerId()));
		lc.setParent(item);

		lc = new Listcell(apply.getStatus() == 0 ? "未处理":"已处理");
		lc.setParent(item);

		lc = new Listcell(DateFormatUtils.format(apply.getCreateAt() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		item.setValue(apply.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onApplyListItemDoubleClicked");
	}

}
