package com.zc.manage.web.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;

import com.zc.common.util.Constants;
import com.zc.web.data.model.File;
import com.zc.web.util.FileUtil;

public class FileRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data) throws Exception {
		File file = (File) data;

		Listcell lc = new Listcell();
		lc.setParent(item);
		
		//prepare the Toolbarbutton
		Toolbarbutton tb = new Toolbarbutton(file.getName());
		tb.setHref(FileUtil.genDownloadUrl(file.getId()));
		tb.setTarget("_blank");
		tb.setParent(lc);
		
		lc = new Listcell(DateFormatUtils.format(file.getCreateTime() * 1000L, Constants.DEFAULT_CREATED_AT_FORMAT));
		lc.setParent(item);

		lc = new Listcell(file.getState() == 0 ? "处理中":"已处理");
		lc.setParent(item);
		
		item.setValue(file.getId());
	}

}
