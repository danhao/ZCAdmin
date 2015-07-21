package com.zc.manage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import com.zc.manage.web.renderer.CashRenderer;
import com.zc.web.data.model.PlayerCash;

public class CashListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;
	final static Logger logger = LoggerFactory.getLogger(CashListCtrl.class);

	protected transient Window cashListWindow;
	protected transient Listbox listBoxCash;
	protected transient Listheader lh_id;
	protected transient Paging paging_cashList;
	protected transient Button btn_search;
	protected transient Listbox ls_search_status;
	protected transient Textbox tb_search_id;
	protected transient Textbox tb_search_playerid;

	public CashListCtrl() {
		super();
	}

	@Override
	public void init() {
		lh_id.setSortAscending(new OrderByComparator("id"));
		lh_id.setSortDescending(lh_id.getSortAscending());
		
		// 0未审核；1已通过；2未通过；3已成交；4已完成
		for(Entry<String, String> entry : Constants.CASH_STATE.entrySet()){
			ls_search_status.appendItem(entry.getValue(), entry.getKey());
		}
		ls_search_status.setSelectedIndex(0);

		btn_search.setImage(Constants.BTN_ICON_SEARCH);
		paging_cashList.setActivePage(0);
		paging_cashList.setPageSize(Constants.PAGESIZE);
		paging_cashList.addEventListener(ZulEvents.ON_PAGING, new OnPagingEventListener());
	}

	public void refreshModel() throws Exception{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.LIST_CASH.getCmd(), "0", 
				String.valueOf(paging_cashList.getActivePage() + 1), 
				tb_search_id.getValue(), tb_search_playerid.getValue(),
				ls_search_status.getSelectedItem().getValue().toString())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		List<PlayerCash> cashs = new ArrayList<PlayerCash>();
		if(jsonData != null){
			if(jsonData.has("data")){
				JSONArray array = jsonData.getJSONArray("data");
				for(int i = 0 ; i < array.length() ; i++){
					JSONObject obj = array.getJSONObject(i);
					PlayerCash cash = new Gson().fromJson(obj.toString(),PlayerCash.class);
					cashs.add(cash);
				}
			}
			
			if(jsonData.has("count"))
				paging_cashList.setTotalSize(jsonData.getInt("count"));
		}
		
		listBoxCash.setItemRenderer(new CashRenderer());
		listBoxCash.setModel(new ListModelList(cashs));
	}

	public void onCreate$cashListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_search(Event event) throws Exception {
		paging_cashList.setActivePage(0);
		refreshModel();
	}
	
	private final class OnPagingEventListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			refreshModel();
		}
	}	
	
	public void onCashListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxCash.getSelectedItem();
		if (item != null) {
			PlayerCash cash = (PlayerCash) item.getAttribute(Constants.DATA);
			showDetailView(cash);
		}
	}

	private void showDetailView(PlayerCash cash) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("cash", cash);
		map.put("listBoxCash", listBoxCash);
		Executions.createComponents("/WEB-INF/pages/zc/manage/cashDetail.zul", null, map);
	}	
}
