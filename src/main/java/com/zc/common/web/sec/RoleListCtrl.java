package com.zc.common.web.sec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;
import com.zc.common.model.SecRoleRightExample;
import com.zc.common.model.SecUserRoleExample;
import com.zc.common.service.RoleService;
import com.zc.common.service.SecurityService;
import com.zc.common.util.Constants;
import com.zc.common.web.sec.renderer.RoleListBoxRenderer;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.OrderByComparator;

public class RoleListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;

	private transient RoleService roleService;
	private transient SecurityService securityService;
	private transient SecRoleExample roleExample;

	protected transient Window roleListWindow;
	protected transient Listbox listBoxRole;
	protected transient Listheader lh_role_id;
	protected transient Listheader lh_role_name;
	protected transient Paging paging_roleList;
	private transient Button btn_refresh;
	protected transient Button btn_role_new;
	protected transient Button btn_role_del;

	public RoleListCtrl() {
		super();
	}

	public void init() {
		setRoleExample(new SecRoleExample());
		lh_role_id.setSortAscending(new OrderByComparator("id"));
		lh_role_id.setSortDescending(lh_role_id.getSortAscending());
		lh_role_name.setSortAscending(new OrderByComparator("name"));
		lh_role_name.setSortDescending(lh_role_name.getSortAscending());

		btn_refresh.setImage(Constants.BTN_ICON_REFRESH);
		btn_role_new.setImage(Constants.BTN_ICON_NEW);
		btn_role_del.setImage(Constants.BTN_ICON_DELETE);

		btn_role_new.setVisible(getUserWorkspace().isAllowed(btn_role_new.getId()));
		btn_role_del.setVisible(getUserWorkspace().isAllowed(btn_role_del.getId()));
		paging_roleList.setActivePage(0);
	}

	public void refreshModel() {
		listBoxRole.setItemRenderer(new RoleListBoxRenderer());
		getPagingWrap().paginate(roleService, getRoleExample(), paging_roleList, listBoxRole);
	}

	public void onCreate$roleListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_role_new(Event event) throws Exception {
		showDetailView(new SecRole());
	}

	public void onClick$btn_refresh(Event event) throws Exception {
		Events.postEvent(Events.ON_CREATE, roleListWindow, event);
		roleListWindow.invalidate();
	}

	@SuppressWarnings("unchecked")
	public void onClick$btn_role_del(Event event) throws Exception {
		if (MsgBox.delSelectedAlert("角色") != Messagebox.OK) {
			return;
		}

		List<Integer> roleIds = new ArrayList<Integer>();
		for (Listitem item : (List<Listitem>) listBoxRole.getItems()) {
			if (item.isSelected()) {
				roleIds.add((Integer) item.getValue());
			}
		}

		if (roleIds != null && !roleIds.isEmpty()) {
			SecRoleExample example = new SecRoleExample();
			example.createCriteria().andIdIn(roleIds);
			getRoleService().delete(example);

			SecUserRoleExample urExample = new SecUserRoleExample();
			urExample.createCriteria().andRoleIdIn(roleIds);
			getSecurityService().deleteUserRole(urExample);

			SecRoleRightExample rrExample = new SecRoleRightExample();
			rrExample.createCriteria().andRoleIdIn(roleIds);
			getSecurityService().deleteRoleRight(rrExample);
			onClick$btn_refresh(event);
		}
	}

	public void onRoleListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxRole.getSelectedItem();
		if (item != null) {
			SecRole role = (SecRole) item.getAttribute(Constants.DATA);
			showDetailView(role);
		}
	}

	private void showDetailView(SecRole role) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("role", role);
		map.put("listBoxRole", listBoxRole);
		Executions.createComponents("/WEB-INF/pages/common/sec/roleDialog.zul", null, map);
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public SecRoleExample getRoleExample() {
		return roleExample;
	}

	public void setRoleExample(SecRoleExample roleExample) {
		this.roleExample = roleExample;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
