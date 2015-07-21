package com.zc.common.web.sec;

import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.zc.common.model.SecRole;
import com.zc.common.model.SecRoleExample;
import com.zc.common.service.RoleService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.MsgBox;

public class RoleDialogCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;

	private transient RoleService roleService;

	private transient SecRole role;
	protected transient Window roleDialogWindow;
	protected transient Listbox listBoxRole;
	protected transient Textbox tb_role_name;
	protected transient Textbox tb_role_descn;
	protected transient Button btn_role_save;
	protected transient Button btn_cancel;

	public RoleDialogCtrl() {
		super();
	}

	public void init() {
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
		btn_role_save.setImage(Constants.BTN_ICON_SAVE);
		btn_role_save.setVisible(getUserWorkspace().isAllowed("btn_role_save"));
	}

	public void onCreate$roleDialogWindow(Event event) throws Exception {
		init();
		Map<String, Object> args = getCreationArgsMap(event);
		role = (args.containsKey("role")) ? (SecRole) args.get("role") : null;
		setRole(role);
		listBoxRole = (args.containsKey("listBoxRole")) ? (Listbox) args.get("listBoxRole") : null;
		doShowDialog(getRole());
	}

	public void onClick$btn_role_save(Event event) throws Exception {
		doSave();
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			roleDialogWindow.onClose();
		}
	}

	private void doClose() throws Exception {
		roleDialogWindow.onClose();
	}

	public void doSave() throws Exception {
		SecRole role = getRole();
		role.setName(tb_role_name.getValue());
		role.setDescn(tb_role_descn.getValue());

		if (getRoleService().isNew(role)) {
			SecRoleExample example = new SecRoleExample();
			example.createCriteria().andNameEqualTo(role.getName());
			if (getRoleService().isExists(example)) {
				MsgBox.alert("已经存在名字为" + role.getName() + "的角色。");
				return;
			}
		}

		getRoleService().saveOrUpdate(role);
		ListModelList lml = (ListModelList) listBoxRole.getListModel();
		int index = lml.indexOf(role);
		if (index == -1) {
			lml.add(0, role);
			index = 0;
		} else {
			lml.set(index, role);
		}
		listBoxRole.setSelectedIndex(index);
		listBoxRole.invalidate();
		listBoxRole.getPaginal().setTotalSize(listBoxRole.getPaginal().getTotalSize() + 1);
		doClose();
	}

	public void doShowDialog(SecRole user) throws Exception {
		try {
			doInitComponents(user);
			roleDialogWindow.doModal();
		} catch (Exception e) {
			Messagebox.show(e.toString());
		}
	}

	public void doInitComponents(SecRole role) {
		if (role.getId() != null) {
			tb_role_name.setValue(role.getName());
			tb_role_descn.setValue(role.getDescn());
		}
	}

	public SecRole getRole() {
		return role;
	}

	public void setRole(SecRole role) {
		this.role = role;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}