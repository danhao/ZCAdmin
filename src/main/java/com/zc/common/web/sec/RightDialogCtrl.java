package com.zc.common.web.sec;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.zc.common.model.Module;
import com.zc.common.model.SecRight;
import com.zc.common.model.SecRightExample;
import com.zc.common.service.ModuleService;
import com.zc.common.service.RightService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.SecRightType;

public class RightDialogCtrl extends GFCBaseCtrl {
	private static final long serialVersionUID = 3380807421303827863L;
	final static Logger logger = LoggerFactory.getLogger(RightDialogCtrl.class);

	private transient RightService rightService;

	private transient SecRight right;
	protected transient Window rightDialogWindow;
	protected transient Listbox listBoxRight;
	protected transient Textbox tb_right_name;
	protected transient Listbox lb_right_type;
	protected transient Listbox lb_module;
	protected transient Textbox tb_right_descn;
	protected transient Button btn_right_save;
	protected transient Button btn_cancel;

	public RightDialogCtrl() {
		super();
	}

	public void init() {
		btn_cancel.setImage(Constants.BTN_ICON_CANCEL);
		btn_right_save.setImage(Constants.BTN_ICON_SAVE);
		btn_right_save.setVisible(getUserWorkspace().isAllowed(btn_right_save.getId()));

		SecRightType[] types = SecRightType.getSecRightTypes();
		for (SecRightType type : types) {
			lb_right_type.appendItem(type.getName(), String.valueOf(type.getId()));
		}
		lb_right_type.setSelectedIndex(0);

		List<Module> modules = ModuleService.getModules();
		for (Module module : modules) {
			lb_module.appendItem(module.getName(), module.getCode());
		}
		lb_module.setSelectedIndex(0);
	}

	public void onCreate$rightDialogWindow(Event event) throws Exception {
		init();

		Map<String, Object> args = getCreationArgsMap(event);
		right = (args.containsKey("right")) ? (SecRight) args.get("right") : null;
		setRight(right);
		listBoxRight = (args.containsKey("listBoxRight")) ? (Listbox) args.get("listBoxRight") : null;
		doShowDialog(getRight());
	}

	public void onClick$btn_right_save(Event event) throws Exception {
		doSave();
	}

	public void onClick$btn_cancel(Event event) throws Exception {
		try {
			doClose();
		} catch (Exception e) {
			rightDialogWindow.onClose();
		}
	}

	private void doClose() throws Exception {
		rightDialogWindow.onClose();
	}

	public void doSave() throws Exception {
		SecRight role = getRight();
		doModel(role);

		if (role.getId() == null || role.getId().intValue() == 0) {
			SecRightExample example = new SecRightExample();
			example.createCriteria().andNameEqualTo(role.getName());
			if (getRightService().isExists(example)) {
				MsgBox.alert("已经存在名字为" + role.getName() + "的权限资源。");
				return;
			}
		}

		getRightService().saveOrUpdate(role);
		ListModelList lml = (ListModelList) listBoxRight.getListModel();
		int index = lml.indexOf(role);
		if (index == -1) {
			lml.add(0, role);
			index = 0;
		} else {
			lml.set(index, role);
		}
		listBoxRight.setSelectedIndex(index);
		listBoxRight.invalidate();
		listBoxRight.getPaginal().setTotalSize(listBoxRight.getPaginal().getTotalSize() + 1);
		doClose();
	}

	public void doShowDialog(SecRight user) throws Exception {
		try {
			doInitComponents(user);
			rightDialogWindow.doModal();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void doInitComponents(SecRight right) {
		tb_right_name.setValue(right.getName());
		int index = (right.getType() != null) ? right.getType() : 0;
		lb_right_type.setSelectedIndex(index);
		List<Listitem> listItems = lb_module.getItems();
		for (int i = 0; i < listItems.size(); i++) {
			if (listItems.get(i).getValue().equals(right.getModule())) {
				lb_module.setSelectedIndex(i);
			}
		}
		tb_right_descn.setValue(right.getDescn());
	}

	public void doModel(SecRight right) {
		right.setName(tb_right_name.getValue());
		right.setType(NumberUtils.toInt(String.valueOf(lb_right_type.getSelectedItem().getValue())));
		right.setModule(String.valueOf(lb_module.getSelectedItem().getValue()));
		right.setDescn(tb_right_descn.getValue());
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public SecRight getRight() {
		return right;
	}

	public void setRight(SecRight right) {
		this.right = right;
	}

}