package com.zc.common.web.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

public abstract class WindowBaseCtrl extends Window implements AfterCompose, Serializable {
	private static final long serialVersionUID = -490236797673860257L;
	protected transient AnnotateDataBinder binder;
	protected transient Map<String, Object> args;

	public WindowBaseCtrl() {
		super();
	}

	public void doOnCreateCommon(Window w) throws Exception {
		binder = new AnnotateDataBinder(w);
		binder.loadAll();
	}

	@SuppressWarnings("unchecked")
	public void doOnCreateCommon(Window w, Event fe) throws Exception {
		doOnCreateCommon(w);
		CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
		args = ce.getArg();
	}

	public void afterCompose() {
		processRecursive(this, this);
	}

	@SuppressWarnings("unchecked")
	private void processRecursive(Window main, Window child) {
		Components.wireVariables(main, child);
		Components.addForwards(main, this);
		List<Component> winList = child.getChildren();
		for (Component window : winList) {
			if (window instanceof Window) {
				processRecursive(main, (Window) window);
			}
		}
	}

}
