package com.zc.common.web.sec.renderer;

import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.model.SecRight;
import com.zc.common.model.SecRole;
import com.zc.common.service.SecurityService;
import com.zc.common.util.Constants;
import com.zc.common.web.util.SelectionCtrl;

public class RoleRightListBoxRenderer implements ListitemRenderer {
	private transient final SelectionCtrl<SecRole> parentController;
	private transient SecurityService securityService;

	public SecurityService getSecurityService() {
		if (securityService == null) {
			securityService = (SecurityService) SpringUtil.getBean("securityService");
			setSecurityService(securityService);
		}
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public RoleRightListBoxRenderer(SelectionCtrl<SecRole> ctrl) {
		this.parentController = ctrl;
	}

	public void render(Listitem item, Object data) throws Exception {
		SecRight right = (SecRight) data;
		SecRole role = parentController.getSelected();
		Listcell lc = new Listcell("");
		Checkbox cb = new Checkbox();
		cb.setChecked(getSecurityService().isRoleInRight(role, right));
		
		lc.appendChild(cb);
		lc.setParent(item);

		lc = new Listcell(right.getName());
		lc.setParent(item);

		lc = new Listcell(right.getDescn());
		lc.setParent(item);

		item.setValue(right.getId());
		item.setAttribute(Constants.DATA, data);
	}

}
