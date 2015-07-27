package com.zc.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.SimpleTreeNode;

import com.zc.common.dao.MenuDAO;
import com.zc.common.model.Menu;
import com.zc.common.model.MenuExample;
import com.zc.common.web.util.UserWorkspace;

@Service
public class MenuService {
	@Resource
	private MenuDAO menuDAO;

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public UserWorkspace getUserWorkspace() {
		return (UserWorkspace) SpringUtil.getBean("userWorkspace");
	}

	public List<Menu> getRootMenus() {
		return getMenus(null);
	}

	public List<Menu> getMenus(String code) {
		MenuExample example = new MenuExample();
		int codeLen = 2;
		if (StringUtils.isBlank(code)) {
			example.createCriteria().andCodeLengthEqualTo(codeLen);
		} else {
			example.createCriteria().andCodeLike(code + "%").andCodeLengthEqualTo(code.length() + codeLen);
		}
		example.setOrderByClause("menu_order,id");
		return getMenuDAO().selectByExample(example);
	}

	public List<SimpleTreeNode> getMenuTreeNodes(List<Menu> menus) {
		List<SimpleTreeNode> results = new ArrayList<SimpleTreeNode>();
		for (Menu menu : menus) {
			if (getUserWorkspace().isAllowed(menu.getRightName())) {
				results.add(new SimpleTreeNode(menu, getMenuTreeNodes(getMenus(menu.getCode()))));
			}
		}
		return results;
	}

	public List<Menu> getModuleMenus() {
		MenuExample example = new MenuExample();
		example.createCriteria().andCodeLengthEqualTo(2);
		return getMenuDAO().selectByExample(example);
	}
}
