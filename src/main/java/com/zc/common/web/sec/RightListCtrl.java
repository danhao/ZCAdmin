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

import com.zc.common.model.SecRight;
import com.zc.common.model.SecRightExample;
import com.zc.common.model.SecRoleRightExample;
import com.zc.common.service.RightService;
import com.zc.common.service.SecurityService;
import com.zc.common.util.Constants;
import com.zc.common.web.sec.renderer.RightListBoxRenderer;
import com.zc.common.web.util.GFCBasePagingCtrl;
import com.zc.common.web.util.MsgBox;
import com.zc.common.web.util.OrderByComparator;

public class RightListCtrl extends GFCBasePagingCtrl {
	private static final long serialVersionUID = -5559198515711412366L;

	private transient RightService rightService;
	private transient SecurityService securityService;
	private transient SecRightExample rightExample;

	protected transient Window rightListWindow;
	protected transient Listbox listBoxRight;
	protected transient Listheader lh_right_id;
	protected transient Listheader lh_right_name;
	protected transient Listheader lh_right_type;
	protected transient Paging paging_rightList;
	private transient Button btn_refresh;
	protected transient Button btn_right_new;
	protected transient Button btn_right_del;

	public RightListCtrl() {
		super();
	}

	public void init() {
		setRightExample(new SecRightExample());
		lh_right_id.setSortAscending(new OrderByComparator("id"));
		lh_right_id.setSortDescending(lh_right_id.getSortAscending());
		lh_right_name.setSortAscending(new OrderByComparator("name"));
		lh_right_name.setSortDescending(lh_right_name.getSortAscending());
		lh_right_type.setSortAscending(new OrderByComparator("type"));
		lh_right_type.setSortDescending(lh_right_type.getSortAscending());

		btn_refresh.setImage(Constants.BTN_ICON_REFRESH);
		btn_right_new.setImage(Constants.BTN_ICON_NEW);
		btn_right_del.setImage(Constants.BTN_ICON_DELETE);

		btn_right_new.setVisible(getUserWorkspace().isAllowed(btn_right_new.getId()));
		btn_right_del.setVisible(getUserWorkspace().isAllowed(btn_right_del.getId()));
		paging_rightList.setActivePage(0);
	}

	public void refreshModel() {
		listBoxRight.setItemRenderer(new RightListBoxRenderer());
		getPagingWrap().paginate(rightService, getRightExample(), paging_rightList, listBoxRight);
	}

	public void onCreate$rightListWindow(Event event) throws Exception {
		init();
		refreshModel();
	}

	public void onClick$btn_right_new(Event event) throws Exception {
		showDetailView(new SecRight());
	}

	public void onClick$btn_refresh(Event event) throws Exception {
		Events.postEvent(Events.ON_CREATE, rightListWindow, event);
		rightListWindow.invalidate();
	}

	@SuppressWarnings("unchecked")
	public void onClick$btn_right_del(Event event) throws Exception {
		if (MsgBox.delSelectedAlert("权限资源") != Messagebox.OK) {
			return;
		}

		List<Integer> rightIds = new ArrayList<Integer>();
		for (Listitem item : (List<Listitem>) listBoxRight.getItems()) {
			if (item.isSelected()) {
				rightIds.add((Integer) item.getValue());
			}
		}

		if (rightIds != null && !rightIds.isEmpty()) {
			SecRightExample example = new SecRightExample();
			example.createCriteria().andIdIn(rightIds);
			getRightService().delete(example);

			SecRoleRightExample rrExample = new SecRoleRightExample();
			rrExample.createCriteria().andRightIdIn(rightIds);
			getSecurityService().deleteRoleRight(rrExample);
			onClick$btn_refresh(event);
		}
	}

	public void onRightListItemDoubleClicked(Event event) throws Exception {
		Listitem item = listBoxRight.getSelectedItem();
		if (item != null) {
			SecRight right = (SecRight) item.getAttribute(Constants.DATA);
			showDetailView(right);
		}
	}

	private void showDetailView(SecRight right) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("right", right);
		map.put("listBoxRight", listBoxRight);
		Executions.createComponents("/WEB-INF/pages/common/sec/rightDialog.zul", null, map);
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public SecRightExample getRightExample() {
		return rightExample;
	}

	public void setRightExample(SecRightExample rightExample) {
		this.rightExample = rightExample;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

}
