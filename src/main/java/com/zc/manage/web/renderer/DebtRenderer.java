package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.data.model.Debt;

public class DebtRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		Debt debt = (Debt) data;
		Listcell lc = new Listcell(String.valueOf(debt.getId()));
		lc.setParent(item);

		lc = new Listcell(String.valueOf(debt.getMoney()));
		lc.setParent(item);

		lc = new Listcell(debt.getType() == 1 ? "代理" : "拍卖");
		lc.setParent(item);

		lc = new Listcell(debt.getType() == 1 ? debt.getPrice() + "": debt.getRate() + "");
		lc.setParent(item);

		lc = new Listcell(debt.getDuration() + "天");
		lc.setParent(item);

		lc = new Listcell(debt.getExpireDays() + "天");
		lc.setParent(item);

		lc = new Listcell(DateFormatUtils.format(debt.getCreateTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		lc = new Listcell(Constants.DEBT_STATE.get(String.valueOf(debt.getState())));
		lc.setParent(item);

		item.setValue(debt.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onDebtListItemDoubleClicked");
	}

}
