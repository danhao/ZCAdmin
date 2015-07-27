package com.zc.common.web.sec;

import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.zc.common.model.SecUser;
import com.zc.common.model.SecUserExample;
import com.zc.common.service.UserService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.MsgBox;

public class UserDialogCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;

	private transient UserService userService;

	private transient SecUser user;
	protected transient Window userDialogWindow;
	protected transient Listbox listBoxUser;
	protected transient Textbox tb_user_name;
	protected transient Textbox tb_user_password;
	protected transient Textbox tb_user_descn;
	protected transient Checkbox cb_user_enabled;
	protected transient Button btn_user_save;
	protected transient Button btn_cancel;
	protected transient Caption cp_title;

	public UserDialogCtrl() {
		super();
	}

	public void init() {
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
		btn_user_save.setImage(Constants.BTN_ICON_SAVE);
		btn_user_save.setVisible(getUserWorkspace().isAllowed("btn_user_save"));
	}

	public void onCreate$userDialogWindow(Event event) throws Exception {
		init();
		Map<String, Object> args = getCreationArgsMap(event);
		user = (args.containsKey("user")) ? (SecUser) args.get("user") : null;
		setUser(user);
		listBoxUser = (args.containsKey("listBoxUser")) ? (Listbox) args.get("listBoxUser") : null;
		doShowDialog(getUser());
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			userDialogWindow.onClose();
		}
	}

	public void onClick$btn_user_save(Event event) throws Exception {
		doSave();
	}

	private void doClose() throws Exception {
		userDialogWindow.onClose();
	}

	public void doSave() throws Exception {
		SecUser user = getUser();
		doModel(user);

		if (user.getId() == null || user.getId().intValue() == 0) {
			SecUserExample example = new SecUserExample();
			example.createCriteria().andNameEqualTo(user.getName());
			if (getUserService().isExists(example)) {
				MsgBox.alert("已经存在名字为" + user.getName() + "的用户。");
				return;
			}
		}

		getUserService().saveOrUpdate(user);
		ListModelList lml = (ListModelList) listBoxUser.getListModel();
		int index = lml.indexOf(user);
		if (index == -1) {
			lml.add(0, user);
			index = 0;
		} else {
			lml.set(index, user);
		}
		listBoxUser.setSelectedIndex(index);
		listBoxUser.invalidate();
		listBoxUser.getPaginal().setTotalSize(listBoxUser.getPaginal().getTotalSize() + 1);
		doClose();
	}

	public void doShowDialog(SecUser user) throws Exception {
		try {
			doInitComponents(user);
			userDialogWindow.doModal();
		} catch (Exception e) {
			throw e;
		}
	}

	public void doInitComponents(SecUser user) {
		cp_title.setLabel((user.getId() != null) ? "编辑" : "新增");
		tb_user_name.setValue(user.getName());
		tb_user_descn.setValue(user.getDescn());
		tb_user_password.setValue(user.getPassword());
		cb_user_enabled.setChecked((user.getEnabled() == null || !user.getEnabled()) ? false : true);

		//是自己
		if (isMySelf(user.getName())) {
			btn_user_save.setVisible(true);
		}
	}

	public void doModel(SecUser user) {
		user.setName(tb_user_name.getValue());
		user.setPassword(tb_user_password.getValue());
		user.setDescn(tb_user_descn.getValue());
		user.setEnabled(cb_user_enabled.isChecked() ? true : false);
		user.setAccountnonexpired(true);
		user.setCredentialsnonexpired(true);
		user.setAccountnonlocked(true);
	}

	public SecUser getUser() {
		return user;
	}

	public void setUser(SecUser user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}