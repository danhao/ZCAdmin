package com.zc.common.model;

import java.io.Serializable;

public class Menu extends BaseModel implements Serializable{

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.id
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private String name;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.code
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private String code;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.url
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private String url;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.right_name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private String rightName;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.icon
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private String icon;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column menu.menu_order
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private Integer menuOrder;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table menu
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.id
	 * @return  the value of menu.id
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.id
	 * @param id  the value for menu.id
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.name
	 * @return  the value of menu.name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.name
	 * @param name  the value for menu.name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.code
	 * @return  the value of menu.code
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public String getCode() {
		return code;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.code
	 * @param code  the value for menu.code
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.url
	 * @return  the value of menu.url
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.url
	 * @param url  the value for menu.url
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.right_name
	 * @return  the value of menu.right_name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public String getRightName() {
		return rightName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.right_name
	 * @param rightName  the value for menu.right_name
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.icon
	 * @return  the value of menu.icon
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.icon
	 * @param icon  the value for menu.icon
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column menu.menu_order
	 * @return  the value of menu.menu_order
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public Integer getMenuOrder() {
		return menuOrder;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column menu.menu_order
	 * @param menuOrder  the value for menu.menu_order
	 * @ibatorgenerated  Tue Apr 20 11:16:38 CST 2010
	 */
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
}