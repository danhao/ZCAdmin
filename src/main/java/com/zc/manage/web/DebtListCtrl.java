package com.zc.manage.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ZulEvents;

import com.google.gson.Gson;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.OrderByComparator;
import com.zc.common.web.util.WebUtils;
import com.zc.manage.common.Cmds;
import com.zc.manage.web.renderer.DebtRenderer;
import com.zc.web.data.model.Debt;

public class DebtListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;
	final static Logger logger = LoggerFactory.getLogger(DebtListCtrl.class);

	protected transient Window debtListWindow;
	protected transient Listbox listBoxDebt;
	protected transient Listheader lh_id;
	protected transient Paging paging_debtList;
	protected transient Button btn_search;
	protected transient Datebox db_st;
	protected transient Datebox db_et;
	protected transient Listbox ls_search_state;
	protected transient Textbox tb_search_value;

	public DebtListCtrl() {
		super();
	}

	@Override
	public void init() {
		lh_id.setSortAscending(new OrderByComparator("id"));
		lh_id.setSortDescending(lh_id.getSortAscending());
		
		// 0未审核；1已通过；2未通过；3已成交；4已完成
		ls_search_state.appendItem("全部", "-1");
		for(Entry<String, String> entry : Constants.DEBT_STATE.entrySet()){
			ls_search_state.appendItem(entry.getValue(), entry.getKey());
		}
		ls_search_state.setSelectedIndex(0);

		btn_search.setImage(Constants.BTN_ICON_SEARCH);
		db_st.setValue(DateUtils.addWeeks(new Date(), -1));
		db_et.setValue(DateUtils.addDays(new Date(), 1));
		paging_debtList.setActivePage(0);
		paging_debtList.setPageSize(Constants.PAGESIZE);
		paging_debtList.addEventListener(ZulEvents.ON_PAGING, new OnPagingEventListener());
	}

	public void refreshModel() throws Exception{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.QUERY_DEBT.getCmd(), "0", 
				String.valueOf(paging_debtList.getActivePage() + 1), 
				String.valueOf(db_st.getValue().getTime() / 1000), String.valueOf(db_et.getValue().getTime() / 1000),
				ls_search_state.getSelectedItem().getValue().toString(), tb_search_value.getValue())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		List<Debt> debts = new ArrayList<Debt>();
		if(jsonData != null){
			if(jsonData.has("data")){
				JSONArray array = jsonData.getJSONArray("data");
				for(int i = 0 ; i < array.length() ; i++){
					JSONObject obj = array.getJSONObject(i);
					Debt debt = new Gson().fromJson(obj.toString(),Debt.class);
					debts.add(debt);
				}
			}
			
			if(jsonData.has("count"))
				paging_debtList.setTotalSize(jsonData.getInt("count"));
		}
		
		listBoxDebt.setItemRenderer(new DebtRenderer());
		listBoxDebt.setModel(new ListModelList(debts));
	}

	public void onCreate$debtListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_search(Event event) throws Exception {
		paging_debtList.setActivePage(0);
		refreshModel();
	}

	public void onDebtListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxDebt.getSelectedItem();
		if (item != null) {
			Debt debt = (Debt) item.getAttribute(Constants.DATA);
			showDetailView(debt);
		}
	}

	private void showDetailView(Debt debt) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("debt", debt);
		map.put("listBoxDebt", listBoxDebt);
		Executions.createComponents("/WEB-INF/pages/zc/manage/debtDetail.zul", null, map);
	}
	
	private final class OnPagingEventListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			refreshModel();
		}
	}	
}
