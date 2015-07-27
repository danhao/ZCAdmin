package com.zc.common.web.menu;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.SimpleTreeModel;
import org.zkoss.zul.SimpleTreeNode;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Window;

import com.zc.common.service.MenuService;
import com.zc.common.util.Constants;
import com.zc.common.web.menu.renderer.MenuTreeitemRenderer;
import com.zc.common.web.util.GFCBaseCtrl;
import com.zc.common.web.util.WebUtils;

public class MainMenuCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = -4697006119573991671L;
	final static Logger logger = LoggerFactory.getLogger(MainMenuCtrl.class);

	private transient MenuService menuService;
	private transient Window mainMenuWindow;
	private transient Div div_menu;
	private transient Menupopup tabsMenuPopup;

	@Override
	public void init() {

	}

	public void onCreate$mainMenuWindow(Event event) throws Exception {
		init();
		createMenu();
	}

	private void createMenu() throws Exception {
		Tree tree = new Tree();
		div_menu.appendChild(tree);
		tree.setZclass("z-dottree");
		tree.setStyle("border:none");
		tree.setHeight("100%");
		tree.setStyle("padding:0px");
		tree.setVflex(true);

		tree.setTreeitemRenderer(new MenuTreeitemRenderer());
		tree.setModel(new SimpleTreeModel(new SimpleTreeNode("ROOT", menuService.getMenuTreeNodes(menuService.getRootMenus()))));
		WebUtils.showPage("/WEB-INF/pages/common/welcome.zul", Constants.START_TAB_LABEL);
		setTabsContentListener();
	}

	private void setTabsContentListener() {
		Tabs tabs = WebUtils.getMainTabs();
		tabs.setContext(tabsMenuPopup);
		List<?> contextMenu = tabsMenuPopup.getChildren();
		for (Object obj : contextMenu) {
			if (obj instanceof Menuitem) {
				Menuitem menuItem = (Menuitem) obj;
				menuItem.addEventListener(Events.ON_CLICK, new ContextListener(tabs));
			}
		}
	}

	public Window getMainMenuWindow() {
		return mainMenuWindow;
	}

	public void setMainMenuWindow(Window mainMenuWindow) {
		this.mainMenuWindow = mainMenuWindow;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	private final class ContextListener implements EventListener {
		private Tabs tabs;

		public ContextListener(Tabs tabs) {
			this.tabs = tabs;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			Menuitem menuItem = (Menuitem) event.getTarget();
			Tabbox tabBox = tabs.getTabbox();
			if ("close_current_tab".equals(menuItem.getId())) {
				Tab tab = tabBox.getSelectedTab();
				if (!Constants.START_TAB_LABEL.equals(tab.getLabel())) {
					tab.onClose();
				}
			} else if ("close_other_tab".equals(menuItem.getId())) {
				List<Tab> tab = tabs.getChildren();
				for (int i = tab.size() - 1; i >= 0; i--) {
					Tab t = tab.get(i);
					if (!t.isSelected() && !t.getLabel().equals(Constants.START_TAB_LABEL)) {
						t.onClose();
					}
				}
			} else if ("close_all_tab".equals(menuItem.getId())) {
				List<Tab> tab = tabs.getChildren();
				for (int i = tab.size() - 1; i >= 0; i--) {
					Tab t = tab.get(i);
					if (!t.getLabel().equals(Constants.START_TAB_LABEL)) {
						t.onClose();
					}
				}
			}
		}
	}
}
