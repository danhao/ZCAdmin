package com.zc.common.web.util;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;

public class MenuTreecell extends Treecell implements EventListener, Serializable, ILabelElement {
	private static final long serialVersionUID = 5221385297281381652L;
	final static Logger logger = LoggerFactory.getLogger(MenuTreecell.class);

	private transient String url;

	public void onEvent(Event event) throws Exception {
		WebUtils.showPage(getUrl(), getLabel(), getImage());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		if (StringUtils.isNotBlank(url)) {
			addEventListener(Events.ON_CLICK, this);
		}
	}
}
