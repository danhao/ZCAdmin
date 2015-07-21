package com.zc.common.service;

import java.util.ArrayList;
import java.util.List;

import com.zc.common.model.Menu;
import com.zc.common.model.Module;

public class ModuleService {
	private MenuService menuService;
	private static List<Module> modules = new ArrayList<Module>();

	public ModuleService(MenuService menuService) {
		this.menuService = menuService;
	}

	public List<Module> loadModules() {
		List<Menu> results = menuService.getModuleMenus();
		if (results != null && !results.isEmpty()) {
			for (Menu menu : results) {
				String rightName = menu.getRightName();
				if (rightName != null) {
					String[] rightNames = rightName.split("_", 2);
					if (rightNames.length >= 2) {
						String code = rightNames[1];
						String name = menu.getName();
						if ("99".equals(menu.getCode())) {
							code = "sec";
							name = "权限系统";
						}
						modules.add(new Module(code, name));
					}
				}
			}
		}
		return modules;
	}

	public static List<Module> getModules() {
		return modules;
	}

	public static Module getModule(String code) {
		List<Module> modules = getModules();
		for (Module module : modules) {
			if (module.getCode().equals(code)) {
				return module;
			}
		}
		return new Module("", "");
	}
}
