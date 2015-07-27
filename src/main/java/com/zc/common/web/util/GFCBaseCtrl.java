package com.zc.common.web.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import com.zc.common.util.Constants;

public abstract class GFCBaseCtrl extends GenericForwardComposer {
	private static final long serialVersionUID = 1828467262899347046L;
	final static Logger logger = LoggerFactory.getLogger(GFCBaseCtrl.class);

	protected transient AnnotateDataBinder binder;
	protected transient Map<String, Object> args;
	private transient UserWorkspace userWorkspace;

	protected transient Map<String, Map<String, String>> zcZones;

	public Map<String, Map<String, String>> getZcZones() {
		return zcZones;
	}

	public void setZcZones(Map<String, Map<String, String>> zcZones) {
		this.zcZones = zcZones;
	}
	

	protected String getZone() {
		return "0";
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCreationArgsMap(Event event) {
		CreateEvent ce = (CreateEvent) ((ForwardEvent) event).getOrigin();
		return ce.getArg();
	}

	public void doCreateDataBinding(Window window) throws Exception {
		window.setVariable("controller", this, false);
		binder = new AnnotateDataBinder(window);
		binder.loadAll();
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

	public void onEvent(Event event) throws Exception {
		final Object controller = getController();
		final Method mtd = ComponentsCtrl.getEventMethod(controller.getClass(), event.getName());

		if (mtd != null) {
			isAllowed(mtd);
		}
		super.onEvent(event);
	}

	public abstract void init();

	private void isAllowed(Method mtd) {
		Annotation[] annotations = mtd.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof Secured) {
				Secured secured = (Secured) annotation;
				for (String rightName : secured.value()) {
					if (!userWorkspace.isAllowed(rightName)) {
						throw new SecurityException("你没有操作权限！");
					}
				}
				return;
			}
		}
	}

	final protected UserWorkspace getUserWorkspace() {
		return userWorkspace == null ? (UserWorkspace) SpringUtil.getBean("userWorkspace") : userWorkspace;
	}

	public void setUserWorkspace(UserWorkspace userWorkspace) {
		this.userWorkspace = userWorkspace;
	}

	public String getLoggedInUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/**
	 * 是否是用户自己
	 * @param name
	 * @return
	 */
	public boolean isMySelf(String name) {
		return getLoggedInUserName().equals(name) ? true : false;
	}

	@SuppressWarnings("unchecked")
	public void checkboxSelectAll(Checkbox checkbox, Listbox listbox) {
		List<Listitem> li = listbox.getItems();
		for (Listitem listitem : li) {
			Listcell lc = (Listcell) listitem.getFirstChild();
			Checkbox cb = (Checkbox) lc.getFirstChild();
			if (cb != null) {
				cb.setChecked(checkbox.isChecked());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Integer>> getIds(Listbox listbox) {
		Map<String, List<Integer>> results = new HashMap<String, List<Integer>>();
		List<Integer> ids = new ArrayList<Integer>();
		List<Integer> noIds = new ArrayList<Integer>();

		List<Listitem> li = listbox.getItems();
		for (Listitem listitem : li) {
			Listcell lc = (Listcell) listitem.getFirstChild();
			Checkbox cb = (Checkbox) lc.getFirstChild();
			if (cb != null) {
				int id = NumberUtils.toInt(String.valueOf(cb.getAttribute(Constants.DATA)));
				if (cb.isChecked()) {
					ids.add(id);
				} else {
					noIds.add(id);
				}
			}
		}
		results.put(Constants.SELECTED, ids);
		results.put(Constants.NO_SELECTED, noIds);
		return results;
	}

	public void changeCheckbox(Listbox listbox) {
		Listitem item = listbox.getSelectedItem();
		Listcell lc = (Listcell) item.getFirstChild();
		Checkbox cb = (Checkbox) lc.getFirstChild();
		cb.setChecked(!cb.isChecked());
		listbox.setSelectedIndex(-1);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<Long>> getLongIds(Listbox listbox) {
		Map<String, List<Long>> results = new HashMap<String, List<Long>>();
		List<Long> ids = new ArrayList<Long>();
		List<Long> noIds = new ArrayList<Long>();

		List<Listitem> li = listbox.getItems();
		for (Listitem listitem : li) {
			Listcell lc = (Listcell) listitem.getFirstChild();
			Checkbox cb = (Checkbox) lc.getFirstChild();
			if (cb != null) {
				long id = NumberUtils.toLong(String.valueOf(cb.getAttribute(Constants.DATA)));
				if (cb.isChecked()) {
					ids.add(id);
				} else {
					noIds.add(id);
				}
			}
		}
		results.put(Constants.SELECTED, ids);
		results.put(Constants.NO_SELECTED, noIds);
		return results;
	}

}
