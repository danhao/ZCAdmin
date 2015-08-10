package com.zc.manage.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.zc.manage.web.renderer.ApplyRenderer;
import com.zc.web.data.model.Debt;
import com.zc.web.data.model.DebtEndApply;

public class ApplyDebtEndListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;
	final static Logger logger = LoggerFactory.getLogger(ApplyDebtEndListCtrl.class);

	protected transient Window applyDebtEndListWindow;
	protected transient Listbox listBoxApply;
	protected transient Listheader lh_id;
	protected transient Paging paging_applyList;
	protected transient Button btn_search;
	protected transient Textbox tb_search_id;
	protected transient Textbox tb_search_playerid;

	public ApplyDebtEndListCtrl() {
		super();
	}

	@Override
	public void init() {
		lh_id.setSortAscending(new OrderByComparator("id"));
		lh_id.setSortDescending(lh_id.getSortAscending());
		
		btn_search.setImage(Constants.BTN_ICON_SEARCH);
		paging_applyList.setActivePage(0);
		paging_applyList.setPageSize(Constants.PAGESIZE);
		paging_applyList.addEventListener(ZulEvents.ON_PAGING, new OnPagingEventListener());
	}

	public void refreshModel() throws Exception{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.LIST_APPLY_DEBT_END.getCmd(), "0", 
				String.valueOf(paging_applyList.getActivePage() + 1), 
				tb_search_id.getValue(), tb_search_playerid.getValue())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		List<DebtEndApply> applies = new ArrayList<DebtEndApply>();
		if(jsonData != null){
			if(jsonData.has("data")){
				JSONArray array = jsonData.getJSONArray("data");
				for(int i = 0 ; i < array.length() ; i++){
					JSONObject obj = array.getJSONObject(i);
					DebtEndApply apply = new Gson().fromJson(obj.toString(),DebtEndApply.class);
					applies.add(apply);
				}
			}
			
			if(jsonData.has("count"))
				paging_applyList.setTotalSize(jsonData.getInt("count"));
		}
		
		listBoxApply.setItemRenderer(new ApplyRenderer());
		listBoxApply.setModel(new ListModelList(applies));
	}

	public void onCreate$applyDebtEndListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_search(Event event) throws Exception {
		paging_applyList.setActivePage(0);
		refreshModel();
	}
	
	private final class OnPagingEventListener implements EventListener {
		public void onEvent(Event event) throws Exception {
			refreshModel();
		}
	}	
	
	public void onApplyListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxApply.getSelectedItem();
		if (item != null) {
			DebtEndApply apply = (DebtEndApply) item.getAttribute(Constants.DATA);
			showDetailView(apply);
		}
	}

	private void showDetailView(DebtEndApply apply) throws Exception {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.GET_DEBT.getCmd(), "0", String.valueOf(apply.getId()))));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		if(jsonData.has("data")){
			Debt debt = new Gson().fromJson(jsonData.getString("data"), Debt.class);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("debt", debt);
			Executions.createComponents("/WEB-INF/pages/zc/manage/debtDetail.zul", null, map);
		}
	}	
}
