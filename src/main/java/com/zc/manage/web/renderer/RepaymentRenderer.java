package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.data.model.Debt.Repayment;

public class RepaymentRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		Repayment pay = (Repayment) data;
		Listcell lc = new Listcell(DateFormatUtils.format(pay.getTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		lc = new Listcell(String.valueOf(pay.getMoney()));
		lc.setParent(item);

		lc = new Listcell(pay.getMemo());
		lc.setParent(item);

		item.setAttribute(Constants.DATA, data);
	}

}
