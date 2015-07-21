package com.zc.common.web.util;

public enum SecRightType {
	SEC_RIGHT_TYPE_MENU(0, "菜单"), SEC_RIGHT_TYPE_COMPONENT(1, "组件"),SEC_RIGHT_TYPE_PAGE(2, "页面");

	private int id;
	private String name;

	private SecRightType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static SecRightType[] getSecRightTypes() {
		return values();
	}

	public static SecRightType getSecRightType(int id) {
		SecRightType[] secRightTypes = getSecRightTypes();
		for (SecRightType secRightType : secRightTypes) {
			if (secRightType.getId() == id) {
				return secRightType;
			}
		}
		return SEC_RIGHT_TYPE_COMPONENT;
	}
}
