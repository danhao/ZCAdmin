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

import com.zc.common.model.SecRight;
import com.zc.common.model.SecRightExample;
import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;
import com.zc.common.model.SecRoleRightKey;
import com.zc.common.service.RightService;
import com.zc.common.service.RoleService;
import com.zc.common.service.SecurityService;
import com.zc.common.util.Constants;
import com.zc.common.web.sec.renderer.RoleNameListBoxRenderer;
import com.zc.common.web.sec.renderer.RoleRightListBoxRenderer;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.OrderByComparator;
import com.zc.common.web.util.SelectionCtrl;

public class RoleRightListCtrl extends GFCBasePagingCtrl implements SelectionCtrl<SecRole> {
	private static final long serialVersionUID = -5559198515711412366L;
	private static final int RIGHT_PAGE_SIZE = 10;

	private transient RightService rightService;
	private transient RoleService roleService;
	private transient SecurityService securityService;

	private transient SecRoleExample roleExample;
	private transient SecRightExample rightExample;

	protected transient Window roleRightListWindow;
	protected transient Listbox listBoxRight;
	protected transient Listbox listBoxRole;
	protected transient Listheader lh_role_name;
	protected transient Listheader lh_right_id;
	protected transient Listheader lh_right_name;
	protected transient Paging paging_ListBoxRight;
	protected transient Paging paging_ListBoxRole;
	private transient SecRole selectedRole;
	private transient Button btn_role_right_save;
	protected transient Button btn_refresh;

	public RoleRightListCtrl() {
		super();
	}

	public void init() {
		setRoleExample(new SecRoleExample());
		setRightExample(new SecRightExample());
		lh_role_name.setSortAscending(new OrderByComparator("name"));
		lh_role_name.setSortDescending(lh_role_name.getSortAscending());
		lh_right_id.setSortAscending(new OrderByComparator("id"));
		lh_right_id.setSortDescending(lh_right_id.getSortAscending());
		lh_right_name.setSortAscending(new OrderByComparator("name"));
		lh_right_name.setSortDescending(lh_right_name.getSortAscending());

		btn_refresh.setImage(Constants.BTN_ICON_REFRESH);
		btn_role_right_save.setImage(Constants.BTN_ICON_SAVE);
		btn_role_right_save.setVisible(getUserWorkspace().isAllowed(btn_role_right_save.getId()));
		paging_ListBoxRight.setActivePage(0);
		paging_ListBoxRole.setActivePage(0);
	}

	public void refreshModel() {
		listBoxRole.setItemRenderer(new RoleNameListBoxRenderer());
		getPagingWrap().paginate(roleService, getRoleExample(), paging_ListBoxRole, listBoxRole);

		listBoxRight.setItemRenderer(new RoleRightListBoxRenderer(this));
		getPagingWrap().paginate(rightService, getRightExample(), paging_ListBoxRight, listBoxRight, RoleRightListCtrl.RIGHT_PAGE_SIZE);
		paging_ListBoxRight.setActivePage(0);
		paging_ListBoxRole.setActivePage(0);
	}

	public void onCreate$roleRightListWindow(Event event) throws Exception {
		init();
		refreshModel();
		setSelectedRole((SecRole) listBoxRole.getModel().getElementAt(0));
		listBoxRole.setSelectedIndex(0);
	}

	public void onClick$btn_refresh(Event event) throws Exception {
		Events.postEvent(Events.ON_CREATE, roleRightListWindow, event);
		roleRightListWindow.invalidate();
	}

	public void onSelect$listBoxRole(Event event) throws Exception {
		Listitem item = listBoxRole.getSelectedItem();
		SecRole role = (SecRole) item.getAttribute(Constants.DATA);
		setSelectedRole(role);
		getPagingWrap().paginate(rightService, getRightExample(), paging_ListBoxRight, listBoxRight, RoleRightListCtrl.RIGHT_PAGE_SIZE);
		paging_ListBoxRight.setActivePage(0);
	}

	public void onClick$btn_role_right_save(Event event) throws Exception {
		doSave();
		MsgBox.info("成功保存。");
	}

	@SuppressWarnings("unchecked")
	public void doSave() throws Exception {
		List<Listitem> li = listBoxRight.getItems();
		for (Listitem listitem : li) {
			Listcell lc = (Listcell) listitem.getFirstChild();
			Checkbox cb = (Checkbox) lc.getFirstChild();
			if (cb != null) {
				if (cb.isChecked() == true) {
					SecRight right = (SecRight) listitem.getAttribute(Constants.DATA);
					SecRole role = getSelectedRole();
					SecRoleRightKey roleRight = getSecurityService().getRolerightByRoleAndRight(role, right);
					if (roleRight == null) {
						roleRight = new SecRoleRightKey();
						roleRight.setRoleId(role.getId());
						roleRight.setRightId(right.getId());
						getSecurityService().saveOrUpdateRoleRight(roleRight);
					}
				} else if (cb.isChecked() == false) {
					SecRight right = (SecRight) listitem.getAttribute(Constants.DATA);
					SecRole role = getSelectedRole();
					SecRoleRightKey roleRight = getSecurityService().getRolerightByRoleAndRight(role, right);
					if (roleRight != null) {
						getSecurityService().deleteRoleRight(roleRight);
					}
				}
			}
		}

	}

	public SecRole getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(SecRole selectedRole) {
		this.selectedRole = selectedRole;
	}

	public SecRole getSelected() {
		return getSelectedRole();
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
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

	public SecRightExample getRightExample() {
		return rightExample;
	}

	public void setRightExample(SecRightExample rightExample) {
		this.rightExample = rightExample;
	}

}
