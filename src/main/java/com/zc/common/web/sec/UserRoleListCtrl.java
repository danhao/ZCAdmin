package com.zc.common.web.sec;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;
import com.zc.common.model.SecUser;
import com.zc.common.model.SecUserExample;
import com.zc.common.model.SecUserRoleKey;
import com.zc.common.service.RoleService;
import com.zc.common.service.SecurityService;
import com.zc.common.service.UserService;
import com.zc.common.util.Constants;
import com.zc.common.web.sec.renderer.UserNameListBoxRenderer;
import com.zc.common.web.sec.renderer.UserRoleListBoxRenderer;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.OrderByComparator;
import com.zc.common.web.util.SelectionCtrl;

public class UserRoleListCtrl extends GFCBasePagingCtrl implements SelectionCtrl<SecUser> {
	private static final long serialVersionUID = -5559198515711412366L;

	private transient UserService userService;
	private transient RoleService roleService;
	private transient SecurityService securityService;

	private transient SecRoleExample roleExample;
	private transient SecUserExample userExample;

	protected transient Window userRoleListWindow;
	protected transient Listbox listBoxUser;
	protected transient Listbox listBoxRole;
	protected transient Listheader lh_user_name;
	protected transient Listheader lh_role_id;
	protected transient Listheader lh_role_name;
	protected transient Paging paging_ListBoxUser;
	protected transient Paging paging_ListBoxRole;
	private transient SecUser selectedUser;
	private transient Button btn_refresh;
	private transient Button btn_user_role_save;

	public UserRoleListCtrl() {
		super();
	}

	public void init() {
		setRoleExample(new SecRoleExample());
		setUserExample(new SecUserExample());
		lh_user_name.setSortAscending(new OrderByComparator("name"));
		lh_user_name.setSortDescending(lh_user_name.getSortAscending());
		lh_role_id.setSortAscending(new OrderByComparator("id"));
		lh_role_id.setSortDescending(lh_role_id.getSortAscending());
		lh_role_name.setSortAscending(new OrderByComparator("name"));
		lh_role_name.setSortDescending(lh_role_name.getSortAscending());

		btn_refresh.setImage(Constants.BTN_ICON_REFRESH);
		btn_user_role_save.setImage(Constants.BTN_ICON_SAVE);
		btn_user_role_save.setVisible(getUserWorkspace().isAllowed(btn_user_role_save.getId()));
	}

	public void refreshModel() {
		listBoxUser.setItemRenderer(new UserNameListBoxRenderer());
		getPagingWrap().paginate(userService, getUserExample(), paging_ListBoxUser, listBoxUser);

		listBoxRole.setItemRenderer(new UserRoleListBoxRenderer(this));
		listBoxRolePaging();
	}

	private void listBoxRolePaging() {
		getPagingWrap().paginate(roleService, getRoleExample(), paging_ListBoxRole, listBoxRole);
	}

	public void onCreate$userRoleListWindow(Event event) throws Exception {
		init();
		refreshModel();
		setSelectedUser((SecUser) listBoxUser.getModel().getElementAt(0));
		listBoxUser.setSelectedIndex(0);
	}

	public void onSelect$listBoxUser(Event event) throws Exception {
		Listitem item = listBoxUser.getSelectedItem();
		SecUser user = (SecUser) item.getAttribute(Constants.DATA);
		setSelectedUser(user);
		listBoxRolePaging();
	}

	public void onClick$btn_user_role_save(Event event) throws Exception {
		doSave();
		MsgBox.info("成功保存。");
	}

	public void onClick$btn_refresh(Event event) throws Exception {
		Events.postEvent(Events.ON_CREATE, userRoleListWindow, event);
		userRoleListWindow.invalidate();
	}

	@SuppressWarnings("unchecked")
	public void doSave() throws Exception {
		List<Listitem> li = listBoxRole.getItems();
		for (Listitem listitem : li) {
			Listcell lc = (Listcell) listitem.getFirstChild();
			Checkbox cb = (Checkbox) lc.getFirstChild();
			if (cb != null) {
				if (cb.isChecked() == true) {
					SecRole role = (SecRole) listitem.getAttribute(Constants.DATA);
					SecUser user = getSelectedUser();
					SecUserRoleKey userRole = getSecurityService().getUserroleByUserAndRole(user, role);
					if (userRole == null) {
						userRole = new SecUserRoleKey();
						userRole.setRoleId(role.getId());
						userRole.setUserId(user.getId());
						getSecurityService().saveOrUpdateUserRole(userRole);
					}
				} else if (cb.isChecked() == false) {
					SecRole role = (SecRole) listitem.getAttribute(Constants.DATA);
					SecUser user = getSelectedUser();
					SecUserRoleKey userRole = getSecurityService().getUserroleByUserAndRole(user, role);
					if (userRole != null) {
						getSecurityService().deleteUserRole(userRole);
					}
				}
			}
		}

	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setUserExample(SecUserExample userExample) {
		this.userExample = userExample;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SecUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(SecUser selectedUser) {
		this.selectedUser = selectedUser;
	}

	public SecUser getSelected() {
		return getSelectedUser();
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public SecRoleExample getRoleExample() {
		return roleExample;
	}

	public void setRoleExample(SecRoleExample roleExample) {
		this.roleExample = roleExample;
	}

	public SecUserExample getUserExample() {
		return userExample;
	}
}
