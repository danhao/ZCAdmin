package com.zc.common.web.sec;

import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.zc.common.model.SecUser;
import com.zc.common.service.UserService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.MsgBox;

public class UserChangePasswordDialogCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;

	private transient UserService userService;
	private transient Window userChangePasswordDialogWindow;
	protected transient Button btn_user_change_password_save;
	protected transient Button btn_cancel;
	protected transient Caption cp_title;
	protected transient Textbox tb_user_old_password;
	protected transient Textbox tb_user_new_password;
	protected transient Textbox tb_user_re_new_password;

	public UserChangePasswordDialogCtrl() {
		super();
	}

	public void init() {
		btn_user_change_password_save.setImage(Constants.BTN_ICON_SAVE);
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
	}

	public void onCreate$userChangePasswordDialogWindow(Event event) throws Exception {
		init();
		doShowDialog();
	}

	public void onClick$btn_user_change_password_save(Event event) throws Exception {
		doSave();
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			userChangePasswordDialogWindow.onClose();
		}
	}

	private void doClose() throws Exception {
		userChangePasswordDialogWindow.onClose();
	}

	public void doSave() throws Exception {
		String oldPassword = tb_user_old_password.getValue();
		if (StringUtils.isBlank(oldPassword)) {
			MsgBox.alert("请输入你的旧密码。");
			tb_user_old_password.setFocus(true);
			return;
		}

		String userName = getLoggedInUserName();
		SecUser user = userService.getUserByLoginname(userName);
		if (user == null || !user.getPassword().equals(oldPassword)) {
			MsgBox.alert("旧密码错误。");
			tb_user_old_password.setFocus(true);
			return;
		}

		String newPassword = tb_user_new_password.getValue();
		String renewPassword = tb_user_re_new_password.getValue();

		if (StringUtils.isBlank(newPassword)) {
			MsgBox.alert("请输入你的新密码。");
			tb_user_new_password.setFocus(true);
			return;
		}

		if (!newPassword.equals(renewPassword)) {
			MsgBox.alert("新密码与确认新密码不一致，请确认。");
			tb_user_new_password.setFocus(true);
			return;
		}

		user.setPassword(newPassword);
		userService.saveOrUpdate(user);
		MsgBox.show("密码修改成功,请牢记你的密码。");
		doClose();
	}

	public void doShowDialog() throws Exception {
		try {
			userChangePasswordDialogWindow.doModal();
		} catch (Exception e) {
			throw e;
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}