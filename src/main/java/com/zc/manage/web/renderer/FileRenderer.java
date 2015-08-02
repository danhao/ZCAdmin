package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.zc.common.util.Constants;
import com.zc.web.data.model.File;

public class FileRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		File file = (File) data;

		Listcell lc = new Listcell(file.getName());
		lc.setParent(item);
		
		lc = new Listcell(DateFormatUtils.format(file.getCreateTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		lc = new Listcell(file.getState() == 0 ? "处理中":"已处理");
		lc.setParent(item);

		item.setValue(file.getId());
		item.setAttribute(Constants.DATA, data);
		ComponentsCtrl.applyForward(item, "onDoubleClick=onFileListItemDoubleClicked");
		
	}

}
