package com.zc.manage.web.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.data.model.PlayerCash;

public class CashRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		PlayerCash cash = (PlayerCash) data;
		Listcell lc = new Listcell(String.valueOf(cash.getId()));
		lc.setParent(item);

		lc = new Listcell(String.valueOf(cash.getPlayerId()));
		lc.setParent(item);

		lc = new Listcell(String.valueOf(cash.getAmount()));
		lc.setParent(item);

		lc = new Listcell(Constants.CASH_STATE.get(cash.getStatus()));
		lc.setParent(item);

		lc = new Listcell(cash.getCreateAt());
		lc.setParent(item);

		lc = new Listcell(cash.getFinishAt());
		lc.setParent(item);
		
		lc = new Listcell(cash.getName());
		lc.setParent(item);

		lc = new Listcell(cash.getType() + "");
		lc.setParent(item);

		lc = new Listcell(cash.getBankName());
		lc.setParent(item);
		lc = new Listcell(cash.getBankAddr());
		lc.setParent(item);
		lc = new Listcell(cash.getBankBranch());
		lc.setParent(item);
		lc = new Listcell(cash.getCardNo());
		lc.setParent(item);

		item.setValue(cash.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onCashListItemDoubleClicked");
	}

}
