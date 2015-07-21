package com.zc.common.web.sec;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.zc.common.model.SecUser;
import com.zc.common.model.SecUserExample;
import com.zc.common.model.SecUserRoleExample;
import com.zc.common.service.SecurityService;
import com.zc.common.service.UserService;
import com.zc.common.util.Constants;
import com.zc.common.web.sec.renderer.UserListBoxRenderer;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.OrderByComparator;

public class UserListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;
	final static Logger logger = LoggerFactory.getLogger(UserListCtrl.class);

	private transient UserService userService;
	private transient SecurityService securityService;
	private transient SecUserExample userExample;

	protected transient Window userListWindow;
	protected transient Listbox listBoxUser;
	protected transient Listheader lh_user_id;
	protected transient Listheader lh_user_name;
	protected transient Listheader lh_user_enabled;
	protected transient Listheader lh_user_created_at;
	protected transient Paging paging_userList;
	protected transient Button btn_user_new;
	protected transient Button btn_user_del;
	protected transient Button btn_refresh;
	protected transient Button btn_search;
	protected transient Datebox db_st;
	protected transient Datebox db_et;

	public UserListCtrl() {
		super();
	}

	@Override
	public void init() {
		setUserExample(new SecUserExample());
		lh_user_id.setSortAscending(new OrderByComparator("id"));
		lh_user_id.setSortDescending(lh_user_id.getSortAscending());
		lh_user_name.setSortAscending(new OrderByComparator("name"));
		lh_user_name.setSortDescending(lh_user_name.getSortAscending());
		lh_user_enabled.setSortAscending(new OrderByComparator("enabled"));
		lh_user_enabled.setSortDescending(lh_user_enabled.getSortAscending());
		lh_user_created_at.setSortAscending(new OrderByComparator("created_at"));
		lh_user_created_at.setSortDescending(lh_user_created_at.getSortAscending());

		btn_refresh.setImage(Constants.BTN_ICON_REFRESH);
		btn_search.setImage(Constants.BTN_ICON_SEARCH);
		btn_user_new.setImage(Constants.BTN_ICON_NEW);
		btn_user_del.setImage(Constants.BTN_ICON_DELETE);
		btn_user_new.setVisible(getUserWorkspace().isAllowed(btn_user_new.getId()));
		btn_user_del.setVisible(getUserWorkspace().isAllowed(btn_user_del.getId()));
		db_st.setValue(DateUtils.addWeeks(new Date(), -1));
		db_et.setValue(DateUtils.addDays(new Date(), 1));
		paging_userList.setActivePage(0);
	}

	public void refreshModel() {
		listBoxUser.setItemRenderer(new UserListBoxRenderer());
		getPagingWrap().paginate(userService, getUserExample(), paging_userList, listBoxUser);
	}

	public void onCreate$userListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_search(Event event) throws Exception {
		paging_userList.setActivePage(0);
		SecUserExample userExample = getUserExample();
		userExample.createCriteria().andCreatedAtBetween(db_st.getValue(), db_et.getValue());
		setUserExample(userExample);
		refreshModel();
	}

	public void onClick$btn_user_new(Event event) throws Exception {
		showDetailView(new SecUser());
	}

	public void onClick$btn_refresh(Event event) throws Exception {
		Events.postEvent(Events.ON_CREATE, userListWindow, event);
		userListWindow.invalidate();
	}

	@SuppressWarnings("unchecked")
	public void onClick$btn_user_del(Event event) throws Exception {
		if (MsgBox.delSelectedAlert("用户") != Messagebox.OK) {
			return;
		}

		List<Integer> uids = new ArrayList<Integer>();
		for (Listitem item : (List<Listitem>) listBoxUser.getItems()) {
			SecUser user = (SecUser) item.getAttribute(Constants.DATA);
			if (item.isSelected() && !user.getName().equals(getLoggedInUserName())) {
				uids.add((Integer) item.getValue());
			} else {
				item.setSelected(false);
			}
		}

		if (uids != null && !uids.isEmpty()) {
			SecUserExample example = new SecUserExample();
			example.createCriteria().andIdIn(uids);
			getUserService().delete(example);

			SecUserRoleExample urExample = new SecUserRoleExample();
			urExample.createCriteria().andUserIdIn(uids);
			getSecurityService().deleteUserRole(urExample);
			onClick$btn_refresh(event);
		}
	}

	public void onUserListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxUser.getSelectedItem();
		if (item != null) {
			SecUser user = (SecUser) item.getAttribute(Constants.DATA);
			showDetailView(user);
		}
	}

	private void showDetailView(SecUser user) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("listBoxUser", listBoxUser);
		Executions.createComponents("/WEB-INF/pages/common/sec/userDialog.zul", null, map);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SecUserExample getUserExample() {
		return userExample;
	}

	public void setUserExample(SecUserExample userExample) {
		this.userExample = userExample;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
