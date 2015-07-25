package com.zc.manage.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.google.gson.Gson;
import com.zc.common.util.Constants;
import com.zc.common.util.Utils;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.UserWorkspace;
import com.zc.common.web.util.WebUtils;
import com.zc.manage.common.Cmds;
import com.zc.manage.web.renderer.PlayerRenderer;
import com.zc.web.core.Constant;
import com.zc.web.data.model.Player;
import com.zc.web.util.FileUtil;

public class PlayerListCtrl  extends GFCBasePagingCtrl{
	private static final long serialVersionUID = 6846983385269851136L;
	final static Logger logger = LoggerFactory.getLogger(PlayerListCtrl.class);
	
	private transient Map<String, Map<String, String>> zcZones;
	
	protected transient Window sgPlayerListWindow;
	protected transient Button btn_search;
	protected transient Textbox tb_search_value;
	protected transient Listbox ls_search_zone;
	protected transient Hbox hbox_tool;
	protected transient Hbox hbox_tool_validate;
	protected transient Button btn_id;
	protected transient Button btn_co;
	protected transient Button btn_clear;
	
	private Player player;
	
	protected transient Listbox listBoxValidate;
	
	protected transient Grid gd_player;	
	private boolean isAdmin;
	
	public PlayerListCtrl() {
		super();
	}
	
	public boolean isAdmin(){
		UserWorkspace userWorkspace = (UserWorkspace) SpringUtil.getBean("userWorkspace");
		return userWorkspace.isAllowed("menu_sys");
	}
	
	@Override
	public void init() {
		btn_search.setImage(Constants.BTN_ICON_SEARCH);
		isAdmin = isAdmin();
		for (String zone : zcZones.keySet()) {
			ls_search_zone.appendItem(zcZones.get(zone).get("name"), zone);
		}
		ls_search_zone.setSelectedIndex(0);

		refreshTab();
	}

	public void refreshTab(){
		if(canUse()){
		}else{
		}
	}
	
	public void onSelect$ls_search_zone(){
		refreshTab();
	}
	
	public void refreshModel() {
	}
	
	public void onCreate$sgPlayerListWindow(Event event) throws Exception {
		init();
		
		Map<String, Object> args = getCreationArgsMap(event);
		player = (args.containsKey("player")) ? (Player) args.get("player") : null;
		if(player != null){
			listBoxValidate = (args.containsKey("listBoxValidate")) ? (Listbox) args.get("listBoxValidate") : null;
			sgPlayerListWindow.setClosable(true);
			sgPlayerListWindow.setWidth("400px");
			sgPlayerListWindow.setHeight("500px");
			hbox_tool.setVisible(false);
			hbox_tool_validate.setVisible(true);
			refreshData(player);
			sgPlayerListWindow.doModal();
		}else{
			sgPlayerListWindow.setClosable(false);
			sgPlayerListWindow.setWidth("100%");
			sgPlayerListWindow.setHeight("100%");
			hbox_tool.setVisible(true);
			hbox_tool_validate.setVisible(false);
		}
	}
	
	public void onClick$btn_search(Event event) throws Exception {
		String pid = getPid();
		
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.QUERY_PLAYER.getCmd(), "0", pid)));
		JSONObject obj = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);
		if (!WebUtils.handleJsonResult(obj)) {
			return;
		}

		Player player = new Gson().fromJson(obj.toString(),Player.class);
		refreshData(player);
	}
	
	public void onClick$btn_id(Event event) throws Exception {
		doValidate(Constant.USER_ID_VALIDATED);
	}

	public void onClick$btn_co(Event event) throws Exception {
		doValidate(Constant.USER_CO_VALIDATED);
	}
	
	public void onClick$btn_clear(Event event) throws Exception {
		doValidate(-Constant.USER_EMAIL_VALIDATED - Constant.USER_MOBILE_VALIDATED);
	}

	private void doValidate(int state) throws Exception{
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, WebUtils.getCmdData(Cmds.VALIDATE_USER.getCmd(), String.valueOf(player.getId()), String.valueOf(state))));
		JSONObject jsonData = WebUtils.postJson(WebUtils.getAdminServerDomain(zcZones, getZone()), qparams);
		if (!WebUtils.handleJsonResult(jsonData)) {
			return;
		}
		
		player = new Gson().fromJson(jsonData.getString("data"),Player.class);
		
		ListModelList lml = (ListModelList) listBoxValidate.getListModel();
		Iterator it = lml.iterator();
		while(it.hasNext()){
			Player p = (Player)it.next();
			if(p.getId() == player.getId()){
				int index = lml.indexOf(p);
				lml.set(index, player);
				listBoxValidate.setSelectedIndex(index);
				break;
			}
		}
		listBoxValidate.invalidate();
		
		sgPlayerListWindow.onClose();
	}
	
	private void refreshData(Player player) throws Exception{
		Map<String, String> dataModel = new LinkedHashMap<String, String>();
		
		dataModel.put("用户ID", String.valueOf(player.getId()));
		dataModel.put("用户名", player.getName());
		dataModel.put("Email", player.getEmail());
		dataModel.put("手机号", player.getMobile());
		dataModel.put("余额（分）", String.valueOf(player.getMoney()));
		dataModel.put("类型", player.getType()==0?"个人":"企业");
		dataModel.put("角色", player.getRole()==0?"债权人":"追债人");
		dataModel.put("VIP", String.valueOf(player.getVip()));
		if(player.getFileId() != null)
			dataModel.put("身份证", "<a href='" + FileUtil.genDownloadUrl(player.getFileId().getId()) + "'>" + player.getFileId().getName() + "</a>");
		if(player.getFileNoneCrime() != null)
			dataModel.put("无犯罪证明", "<a href='" + FileUtil.genDownloadUrl(player.getFileNoneCrime().getId()) + "'>" + player.getFileNoneCrime().getName() + "</a>");
		if(player.getFileCredit() != null)
			dataModel.put("信用报告", "<a href='" + FileUtil.genDownloadUrl(player.getFileCredit().getId()) + "'>" + player.getFileCredit().getName() + "</a>");
		if(player.getFileOrganizationCode() != null)
			dataModel.put("组织机构代码证", "<a href='" + FileUtil.genDownloadUrl(player.getFileOrganizationCode().getId()) + "'>" + player.getFileOrganizationCode().getName() + "</a>");
		if(player.getFileBusinessLicence() != null)
			dataModel.put("营业执照", "<a href='" + FileUtil.genDownloadUrl(player.getFileBusinessLicence().getId()) + "'>" + player.getFileBusinessLicence().getName() + "</a>");
		if(player.getFileTaxNumber() != null)
			dataModel.put("税务登记证", "<a href='" + FileUtil.genDownloadUrl(player.getFileTaxNumber().getId()) + "'>" + player.getFileTaxNumber().getName() + "</a>");
		if(player.getFileAccountPermit() != null)
			dataModel.put("开户许可证", "<a href='" + FileUtil.genDownloadUrl(player.getFileAccountPermit().getId()) + "'>" + player.getFileAccountPermit().getName() + "</a>");
		dataModel.put("最近访问时间", Utils.int2FormatDate(player.getAccessTime(), Constants.DEFAULT_CREATED_AT_ALL_FORMAT));
		dataModel.put("创建时间", Utils.int2FormatDate(player.getCreateTime(), Constants.DEFAULT_CREATED_AT_ALL_FORMAT));
		dataModel.put("登陆时间", Utils.int2FormatDate(player.getLoginTime(), Constants.DEFAULT_CREATED_AT_ALL_FORMAT));
		dataModel.put("上次登陆时间", Utils.int2FormatDate(player.getLastLoginTime(), Constants.DEFAULT_CREATED_AT_ALL_FORMAT));
		dataModel.put("封号至时间", Utils.int2FormatDate(player.getBanAccountTime(), Constants.DEFAULT_CREATED_AT_ALL_FORMAT));

		gd_player.setRowRenderer(new PlayerRenderer());
		gd_player.setModel(new ListModelMap(dataModel));
	}
	
	private String getPid() throws Exception {
		String id = tb_search_value.getValue();
		if (StringUtils.isBlank(id)) {
			MsgBox.alert("错误的用户ID，请确认！");
		}
		return id.trim();
	}

	protected String getZone() {
		return String.valueOf(ls_search_zone.getSelectedItem().getValue());
	}
	
	public Map<String, Map<String, String>> getZcZones() {
		return zcZones;
	}
	public void setSgZones(Map<String, Map<String, String>> zcZones) {
		this.zcZones = zcZones;
	}
	
	public boolean canUse(){
		String zone = getZone();
		boolean isBeta = Boolean.valueOf(zcZones.get(zone).get("beta"));
		return isBeta || isAdmin;
	}
}
