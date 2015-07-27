package com.zc.manage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.gson.Gson;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.WebUtils;
import com.zc.manage.common.Cmds;
import com.zc.web.data.model.Debt;
import com.zc.web.data.model.PlayerCash;

public class CashDetailCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;

	private transient PlayerCash cash;
	protected transient Window cashDialogWindow;
	protected transient Listbox listBoxCash;
	protected transient Button btn_succ;
	protected transient Button btn_fail;
	protected transient Button btn_cancel;
	protected transient Caption cp_title;

	protected transient Textbox id; // id
	protected transient Textbox playerId;
	protected transient Textbox amount;
	protected transient Textbox name;
	protected transient Textbox type;
	protected transient Textbox bankName;
	protected transient Textbox bankAddr;
	protected transient Textbox bankBranch;
	protected transient Textbox cardNo;
	protected transient Textbox status;
	protected transient Textbox createAt;
	protected transient Textbox finishAt;
	protected transient Textbox description;

	public CashDetailCtrl() {
		super();
	}

	public void init() {
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
		btn_succ.setImage(Constants.BTN_ICON_OK);
		btn_fail.setImage(Constants.BTN_ICON_NO);

		if (!this.cash.getStatus().equals("0")) {
			btn_succ.setVisible(false);
			btn_fail.setVisible(false);
		}
	}

	public void onCreate$cashDialogWindow(Event event) throws Exception {
		Map<String, Object> args = getCreationArgsMap(event);
		cash = (args.containsKey("cash")) ? (PlayerCash) args.get("cash")
				: null;
		setCash(cash);
		init();
		listBoxCash = (args.containsKey("listBoxCash")) ? (Listbox) args
				.get("listBoxCash") : null;
		doShowDialog(this.cash);
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			cashDialogWindow.onClose();
		}
	}

	public void onClick$btn_succ(Event event) throws Exception {
		doSave("1");
	}

	public void onClick$btn_fail(Event event) throws Exception {
		doSave("2");
	}

	private void doClose() throws Exception {
		cashDialogWindow.onClose();
	}

	public void doSave(String status) throws Exception {

		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(
				Cmds.UPDATE_CASH.getCmd(), "0", id.getValue(), status, description.getValue())));

		JSONObject jsonData = WebUtils.postJson(
				WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);

		if (jsonData != null) {
			if (jsonData.has("data")) {
				JSONObject obj = jsonData.getJSONObject("data");
				PlayerCash d = new Gson().fromJson(obj.toString(), PlayerCash.class);
				PropertyUtils.copyProperties(cash, d);
			}
		}

		ListModelList lml = (ListModelList) listBoxCash.getListModel();
		int index = lml.indexOf(cash);
		if (index == -1) {
			lml.add(0, cash);
			index = 0;
		} else {
			lml.set(index, cash);
		}
		listBoxCash.setSelectedIndex(index);
		listBoxCash.invalidate();

		doClose();
	}

	public void doShowDialog(PlayerCash cash) throws Exception {
		try {
			doInitComponents(cash);
			cashDialogWindow.doModal();
		} catch (Exception e) {
			throw e;
		}
	}

	public void doInitComponents(PlayerCash cash) {
		cp_title.setLabel((cash.getId() > 0) ? "编辑" : "新增");
		id.setValue(String.valueOf(cash.getId()));
		playerId.setValue(String.valueOf(cash.getPlayerId()));
		name.setValue(cash.getName());
		type.setValue(cash.getType() + "");
		bankName.setValue(cash.getBankName());
		bankAddr.setValue(cash.getBankAddr());
		bankBranch.setValue(cash.getBankBranch());
		cardNo.setValue(cash.getCardNo());
		status.setValue(Constants.CASH_STATE.get(cash.getStatus()));
		createAt.setValue(cash.getCreateAt());
		finishAt.setValue(cash.getFinishAt());
		description.setValue(cash.getDescription());
	}

	public void setCash(PlayerCash cash) {
		this.cash = cash;
	}
}