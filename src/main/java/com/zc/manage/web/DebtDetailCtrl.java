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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Html;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.gson.Gson;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.WebUtils;
import com.zc.manage.common.Cmds;
import com.zc.manage.web.renderer.RepaymentRenderer;
import com.zc.web.core.Constant;
import com.zc.web.data.model.Debt;
import com.zc.web.data.model.File;
import com.zc.web.util.FileUtil;

public class DebtDetailCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;

	private transient Debt debt;
	protected transient Window debtDialogWindow;
	protected transient Listbox listBoxDebt;
	protected transient Button btn_debt_save;
	protected transient Button btn_approve;
	protected transient Button btn_cancel;
	protected transient Button btn_bidwin;
	protected transient Button btn_close;
	protected transient Button btn_admin_close;
	protected transient Button btn_repayment;
	protected transient Caption cp_title;
	
	protected transient Listbox listBoxRepayment;

	protected transient Textbox id; //id
	protected transient Textbox money;
	protected transient Listbox type;	// 2：拍卖；1：代理
	protected transient Textbox price;
	protected transient Textbox rate;
	protected transient Textbox duration;
	protected transient Textbox expireDays;
	protected transient Listbox state;	// 0未审核；1已通过；2未通过；3已成交；4已完成；
	protected transient Textbox bidIncrease;	// 加价幅度
	protected transient Textbox ownerId;	// 发布人
	protected transient Textbox ownerName;
	protected transient Textbox winnerId;	// 获单人
	protected transient Textbox winnerName;
	protected transient Textbox debtorName;
	protected transient Textbox debtorPhone;
	protected transient Textbox debtorId;
	protected transient Textbox debtorLocation;
	protected transient Textbox debtorAddr;
	protected transient Textbox debtExpireTime;
	protected transient Textbox mortgage;
	protected transient Textbox judgementTime;
	protected transient Textbox reason;
	protected transient Textbox descript;
	protected transient Textbox createTime; // 创建时间
	protected transient Textbox publishTime;// 审核时间
	protected transient Textbox endTime; 	// 结束时间
	protected transient Textbox creditorName; 	// 结束时间	
	protected transient Html creditorFileId;
	protected transient Html files;
	
	protected transient Intbox repayMoney;
	protected transient Textbox repayMemo;
	
	private Map<String, String> changeMap = new HashMap<String, String>();
	
	public DebtDetailCtrl() {
		super();
	}

	public void init() {
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
		btn_debt_save.setImage(Constants.BTN_ICON_SAVE);
		btn_approve.setImage(Constants.BTN_ICON_OK);
		btn_bidwin.setImage(Constants.BTN_ICON_OK);
		btn_close.setImage(Constants.BTN_ICON_CLEAR);
		btn_admin_close.setImage(Constants.BTN_ICON_DELETE);
		btn_repayment.setImage(Constants.BTN_ICON_ADD);
		
		type.appendItem("代理", "1");
		type.appendItem("拍卖", "2");

		for(Entry<String, String> entry : Constants.DEBT_STATE.entrySet()){
			state.appendItem(entry.getValue(), entry.getKey());
		}
		
		btn_approve.setVisible(false);
	}
	
	public void onCreate$debtDialogWindow(Event event) throws Exception {
		init();
		Map<String, Object> args = getCreationArgsMap(event);
		debt = (args.containsKey("debt")) ? (Debt) args.get("debt") : null;
		setDebt(debt);
		listBoxDebt = (args.containsKey("listBoxDebt")) ? (Listbox) args.get("listBoxDebt") : null;
		btn_approve.setVisible(this.debt.getState() == Constant.STATE_NEW);
		btn_bidwin.setVisible(this.debt.getState() == Constant.STATE_PUBLISH);
		btn_close.setVisible(this.debt.getState() == Constant.STATE_DEALED && this.debt.getType() == Constant.TYPE_DEPUTY);
		btn_repayment.setVisible(this.debt.getState() == Constant.STATE_DEALED && this.debt.getType() == Constant.TYPE_DEPUTY);
		btn_admin_close.setVisible(this.debt.getState() != Constant.STATE_CLOSED);
		
		listBoxRepayment.setItemRenderer(new RepaymentRenderer());
		listBoxRepayment.setModel(new ListModelList(debt.getRepayments()));
		
		doShowDialog(getDebt());
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			debtDialogWindow.onClose();
		}
	}

	public void onClick$btn_debt_save(Event event) throws Exception {
		doSave();
	}
	
	public void onClick$btn_approve(Event event) throws Exception {
		changeMap.put("state", "1");
		doSave();
	}
	
	public void onClick$btn_bidwin(Event event) throws Exception {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.BID_WIN.getCmd(), "0", String.valueOf(debt.getId()), winnerId.getValue())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		if(jsonData != null){
			if(jsonData.getBoolean("result")){
				MsgBox.alert("操作成功！");
				doClose();
				return;
			}
		}
		
		MsgBox.alert("操作失败！");
	}
	
	public void onClick$btn_close(Event event) throws Exception {
		if(MsgBox.show("确定本单结束，需要结单吗？", "关闭确认", Messagebox.OK + Messagebox.CANCEL, Messagebox.QUESTION) != Messagebox.OK){
			return;
		}
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.DEBT_CLOSE.getCmd(), "0", String.valueOf(debt.getId()))));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		if(jsonData != null){
			if(jsonData.getBoolean("result")){
				MsgBox.info("操作成功！");
				
				debt.setState(Constant.STATE_CLOSED);
				updateList();
				
				doClose();
				return;
			}
		}
		
		MsgBox.alert("操作失败！");
		doClose();
	}
	
	public void onClick$btn_admin_close(Event event) throws Exception {
		if(MsgBox.show("确定要关闭本单吗？", "关闭确认", Messagebox.OK + Messagebox.CANCEL, Messagebox.QUESTION) != Messagebox.OK){
			return;
		}
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.ADMIN_CLOSE_DEBT.getCmd(), "0", String.valueOf(debt.getId()))));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		if(jsonData != null){
			if(jsonData.getBoolean("result")){
				MsgBox.info("操作成功！");
				
				debt.setState(Constant.STATE_CLOSED);
				updateList();
				doClose();
				return;
			}
		}
		
		MsgBox.alert("操作失败！");
		doClose();
	}
	
	public void onClick$btn_repayment(Event event) throws Exception {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.ADD_REPAYMENT.getCmd(), "0", String.valueOf(debt.getId()), String.valueOf((int)(repayMoney.getValue() * 100)), repayMemo.getValue())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		if(jsonData != null){
			if(jsonData.getBoolean("result")){
				Debt debt = new Gson().fromJson(jsonData.getString("data"),Debt.class);
				this.debt.setRepayments(debt.getRepayments());

				listBoxRepayment.setModel(new ListModelList(debt.getRepayments()));
				listBoxRepayment.invalidate();
				
				updateList();

				return;
			}
		}
		
		MsgBox.alert("操作失败！");
	}	
	
	private void doClose() throws Exception {
		debtDialogWindow.onClose();
	}

	public void doSave() throws Exception {
		
		if(changeMap.size() > 0){
			String[] data = new String[changeMap.size() * 2 + 1];
			data[0] = String.valueOf(debt.getId());
			int i = 1;
			for(Entry<String, String> entry : changeMap.entrySet()){
				data[i ++] = entry.getKey();
				data[i ++] = entry.getValue();
			}
			
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.UPDATE_DEBT.getCmd(), "0", data)));
			
			JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
			
			if(jsonData != null){
				if(jsonData.has("data")){
					JSONObject obj = jsonData.getJSONObject("data");
					Debt d = new Gson().fromJson(obj.toString(),Debt.class);
					PropertyUtils.copyProperties(debt, d);
				}
			}			
			
			updateList();
		}
		
		doClose();
	}
	
	private void updateList(){
		if(listBoxDebt == null)
			return;
		
		ListModelList lml = (ListModelList) listBoxDebt.getListModel();
		int index = lml.indexOf(debt);
		if (index == -1) {
			lml.add(0, debt);
			index = 0;
		} else {
			lml.set(index, debt);
		}
		listBoxDebt.setSelectedIndex(index);
		listBoxDebt.invalidate();		
	}

	public void doShowDialog(Debt debt) throws Exception {
		try {
			doInitComponents(debt);
			debtDialogWindow.doModal();
		} catch (Exception e) {
			throw e;
		}
	}

	public void doInitComponents(Debt debt) {
		cp_title.setLabel((debt.getId() > 0) ? "编辑" : "新增");
		id.setValue(String.valueOf(debt.getId()));
		money.setValue(String.valueOf(debt.getMoney()));
		type.setSelectedIndex(debt.getType() - 1);
		price.setValue(String.valueOf(debt.getPrice()));
		rate.setValue(String.valueOf(debt.getRate()));
		duration.setValue(String.valueOf(debt.getDuration()));
		expireDays.setValue(String.valueOf(debt.getExpireDays()));
		state.setSelectedIndex(debt.getState());	// 0未审核；1已通过；2未通过；3已成交；4已完成；
		bidIncrease.setValue(String.valueOf(debt.getBidIncrease()));	// 加价幅度
		ownerId.setValue(String.valueOf(debt.getOwnerId()));	// 发布人
		ownerName.setValue(String.valueOf(debt.getOwnerName()));
		winnerId.setValue(String.valueOf(debt.getWinnerId()));	// 获单人
		winnerName.setValue(String.valueOf(debt.getWinnerName()));
		creditorName.setValue(String.valueOf(debt.getCreditorName()));
		creditorFileId.setContent("<a href='" + FileUtil.genDownloadUrl(debt.getCreditorFileId().getId()) + "'>" + debt.getCreditorFileId().getName() + "</a>");
		debtorName.setValue(String.valueOf(debt.getDebtorName()));
		debtorPhone.setValue(String.valueOf(debt.getDebtorPhone()));
		debtorId.setValue(String.valueOf(debt.getDebtorId()));
		debtorLocation.setValue(String.valueOf(debt.getDebtorLocation()));
		debtorAddr.setValue(String.valueOf(debt.getDebtorAddr()));
		debtExpireTime.setValue(String.valueOf(debt.getDebtExpireTime()));
		mortgage.setValue(String.valueOf(debt.getMortgage()));
		judgementTime.setValue(String.valueOf(debt.getJudgementTime()));
		reason.setValue(debt.getReason());
		descript.setValue(debt.getDescript());
		
		StringBuffer sb = new StringBuffer();
		for(File file : debt.getFiles()){
			sb.append("<a href='" + FileUtil.genDownloadUrl(file.getId()) + "'>" + file.getName() + "</a></br>");
		}
		files.setContent(sb.toString());
		
		createTime.setValue(DateFormatUtils.format(debt.getCreateTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT)); // 创建时间
		publishTime.setValue(DateFormatUtils.format(debt.getPublishTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));// 审核时间
		endTime.setValue(DateFormatUtils.format(debt.getEndTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT)); 	// 结束时间
	}

	public Debt getDebt() {
		return debt;
	}

	public void setDebt(Debt debt) {
		this.debt = debt;
	}

    public void onChanging$money(InputEvent event) {
    	handleChanging(event);
    }
	
    public void onSelect$type(SelectEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$price(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$rate(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$duration(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$expireDays(InputEvent event) {
    	handleChanging(event);
    }
	
    public void onChanging$bidIncrease(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$debtorName(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$debtorPhone(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$debtorId(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$debtorLocation(InputEvent event) {
    	handleChanging(event);
    }
	
    public void onChanging$debtorAddr(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$debtExpireTime(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$mortgage(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$judgementTime(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onChanging$reason(InputEvent event) {
    	handleChanging(event);
    }
	
    public void onChanging$descript(InputEvent event) {
    	handleChanging(event);
    }
    
    public void onSelect$state(SelectEvent event) {
    	handleChanging(event);
    }
	
	private void handleChanging(Event event){
		if(event instanceof InputEvent)
			changeMap.put(event.getTarget().getId(), ((InputEvent)event).getValue());
		else if(event instanceof SelectEvent){
			changeMap.put(event.getTarget().getId(), ((Listbox)event.getTarget()).getSelectedItem().getValue().toString());
		}
	}
}