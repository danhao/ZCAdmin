package com.zc.common.web.sec.renderer;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.zc.common.model.SecUser;
import com.zc.common.util.Constants;

public class UserGridRowRenderer implements RowRenderer{

	public void render(Row row, Object data) throws Exception {
		SecUser user = (SecUser) data;
		new Label(user.getName()).setParent(row);
		new Label(user.getEnabled() ? "启用" : "禁用").setParent(row);
		new Label(user.getDescn()).setParent(row);
		new Label(DateFormatUtils.format(user.getCreatedAt(), Constants.DEFAULT_CREATED_AT_FORMAT)).setParent(row);
	}

}
