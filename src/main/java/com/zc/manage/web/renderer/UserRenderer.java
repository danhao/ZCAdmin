package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.core.Constant;
import com.zc.web.data.model.Player;

public class UserRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		Player player = (Player) data;
		Listcell lc = new Listcell(String.valueOf(player.getId()));
		lc.setParent(item);

		lc = new Listcell(player.getName());
		lc.setParent(item);

		lc = new Listcell(player.getEmail());
		lc.setParent(item);

		lc = new Listcell(player.getMobile());
		lc.setParent(item);

		lc = new Listcell(String.valueOf(player.getMoney()));
		lc.setParent(item);

		lc = new Listcell(player.getType() == 0?"个人":"企业");
		lc.setParent(item);

		lc = new Listcell(DateFormatUtils.format(player.getCreateTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		lc = new Listcell(getStatus(player.getStatus()));
		lc.setParent(item);

		item.setValue(player.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onValidateListItemDoubleClicked");
	}
	
	private String getStatus(int status){
		if(status == 0)
			return "无";
		
		String s = "";
		if((status & Constant.USER_EMAIL_VALIDATED) == Constant.USER_EMAIL_VALIDATED)
			s += "Email验证,";
		if((status & Constant.USER_MOBILE_VALIDATED) == Constant.USER_MOBILE_VALIDATED)
			s += "手机验证,";
		if((status & Constant.USER_ID_VALIDATED) == Constant.USER_ID_VALIDATED)
			s += "身份验证,";
		if((status & Constant.USER_CO_VALIDATED) == Constant.USER_CO_VALIDATED)
			s += "企业验证,";
		
		return s.substring(0, s.length() - 1);
	}

}
