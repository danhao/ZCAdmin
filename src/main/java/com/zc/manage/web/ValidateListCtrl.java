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
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.gson.Gson;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.OrderByComparator;
import com.zc.common.web.util.WebUtils;
import com.zc.manage.common.Cmds;
import com.zc.manage.web.renderer.UserRenderer;
import com.zc.web.data.model.Player;

public class ValidateListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;
	final static Logger logger = LoggerFactory.getLogger(ValidateListCtrl.class);

	protected transient Window validateListWindow;
	protected transient Listbox listBoxValidate;
	protected transient Listheader lh_id;
	protected transient Button btn_search;
	protected transient Radiogroup type;
	protected transient Textbox tb_search_value;

	public ValidateListCtrl() {
		super();
	}

	@Override
	public void init() {
		lh_id.setSortAscending(new OrderByComparator("id"));
		lh_id.setSortDescending(lh_id.getSortAscending());

		btn_search.setImage(Constants.BTN_ICON_SEARCH);
	}

	public void refreshModel() throws Exception{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.LIST_USER.getCmd(), "0", 
				tb_search_value.getValue(), type.getSelectedItem().getValue())));
		
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);		
		
		List<Player> players = new ArrayList<Player>();
		if(jsonData != null){
			if(jsonData.has("data")){
				JSONArray array = jsonData.getJSONArray("data");
				for(int i = 0 ; i < array.length() ; i++){
					JSONObject obj = array.getJSONObject(i);
					Player player = new Gson().fromJson(obj.toString(),Player.class);
					players.add(player);
				}
			}
		}
		
		listBoxValidate.setItemRenderer(new UserRenderer());
		listBoxValidate.setModel(new ListModelList(players));
	}

	public void onCreate$validateListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_search(Event event) throws Exception {
		refreshModel();
	}

	public void onValidateListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxValidate.getSelectedItem();
		if (item != null) {
			Player player = (Player) item.getAttribute(Constants.DATA);
			showDetailView(player);
		}
	}

	private void showDetailView(Player player) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("player", player);
		map.put("listBoxValidate", listBoxValidate);
		Executions.createComponents("/WEB-INF/pages/zc/manage/playerList.zul", null, map);
	}
}
