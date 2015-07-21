package com.zc.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Constants {
	public static final String IMAGE_SERVER = "http://hlzy.3g.qq.com/img/";
	public static final int MAX_TAB_NUM = 9;
	public static final String ENV = "env";
	public static final String CMD = "cmd";
	public static final String OPT_TYPE = "itype";
	public static final String DATA = "data";
	public static final String ZONE = "zone";
	public static final String PLAYER_ID = "player_id";
	public static final String PID = "pid";
	public static final String VALUE = "value";
	public static final String CTRL_PAGE = "ctrlPage";
	public static final String PLAYER_ITEM = "player_item";
	public static final String GM = "gm";
	public static final String DOMAIN = "domain";
	public static final String CP_ID = "cpid";
	public static final String GAME_ID = "gameid";
	public static final String FXWR_CP_ID = "fxwr_cp_id";
	public static final String FXWR_GAME_ID = "fxwr_game_id";
	public static final String FXWR_PASSWORD = "fxwr_password";
	public static final String PORT_SPLIT_SYMBOL = ":";
	public static final String URL_SPLIT_SYMBOL = "/";
	public static final String ZHSH_ADMIN_SERVER_URL = "zhsh_admin_server_url";
	public static final String JH_ADMIN_SERVER_URL = "jh_admin_server_url";
	public static final String ZHOL_ADMIN_SERVER_URL = "zhol_admin_server_url";
	public static final String FXWR_ADMIN_SERVER_URL = "fxwr_admin_server_url";
	public static final String FCWQ_ADMIN_SERVER_URL = "fcwq_admin_server_url";
	public static final String WEB_FXWR_ADMIN_SERVER_URL = "web_fxwr_admin_server_url";
	public static final String WEB_FXWR_ADMIN_ID = "web_fxwr_admin_id";
	public static final String FXWR_ADMIN_ID = "fxwr_admin_id";
	public static final String MANOR_ADMIN_SERVER = "manor_admin_server";
	public static final String MANOR_ADMIN_SERVER_PORT = "manor_admin_server_port";
	public static final String MANOR_ADMIN_SERVER_URL = "manor_admin_server_url";
	public static final String SMALL_USER_DATA_NUM = "small_user_data_num";
	public static final String MANOR_MAIN_DATA_NUM = "manor_main_data_num";
	public static final String SMALL_USER_DATA_TABLE_NUM = "small_user_data_table_num";
	public static final String APP_TITLE = "app_title";
	public static final String LOGIN_PANEL_TITLE = "login_panel_title";
	public static final String MENU_TREE_TITLE = "menu_tree_title";
	public static final String NO_SELECTED = "no_selected";
	public static final String SELECTED = "selected";
	public static final String CONVERT_SUCC = "CONVERT_SUCC";
	public static final String CONVERT_FAIL = "CONVERT_FAIL";
	public static final String VERIFY_FAIL = "VERIFY_FAIL";
	public static final String VERIFY_SUCC = "VERIFY_SUCC";
	public static final String START_TAB_LABEL = "开始";
	public static final String DESCENDING = "descending";
	public static final String ASCENDING = "ascending";
	public static final String NATURAL = "natural";
	public static final String DEFAULT_CREATED_AT_ALL_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_CREATED_AT_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_STAT_FORMAT = "yyyy-MM-dd";
	public static final String DAHUA_PAGESIZE = "dahua.pagesize";
	public static final String PHOTO_PAGESIZE = "photo.pagesize";
	public static final String FXWR_IMG_SERVER = "fxwr_img_server";
	public static final String ZHSH_IMG_SERVER = "zhsh_img_server";
	public static final String ANDROID_FXWR_IMG_SERVER = "android_fxwr_img_server";
	public static final String FCWQ_IMG_SERVER = "fcwq_img_server";

	public static final String BTN_ICON_PATH = "/images/icons/16/";
	public static final String BTN_ICON_REFRESH = BTN_ICON_PATH + "btn_refresh.png";
	public static final String BTN_ICON_NEW = BTN_ICON_PATH + "btn_new.gif";
	public static final String BTN_ICON_DELETE = BTN_ICON_PATH + "btn_delete.gif";
	public static final String BTN_ICON_SAVE = BTN_ICON_PATH + "btn_save.gif";
	public static final String BTN_ICON_SEARCH = BTN_ICON_PATH + "btn_search.png";
	public static final String BTN_ICON_CLEAR = BTN_ICON_PATH + "edit_clear.png";
	public static final String BTN_ICON_OK = BTN_ICON_PATH + "btn_ok.gif";
	public static final String BTN_ICON_NO = BTN_ICON_PATH + "btn_no.gif";
	public static final String BTN_ICON_CANCEL = BTN_ICON_PATH + "btn_cancel.png";
	public static final String BTN_ICON_EXCEL = BTN_ICON_PATH + "btn_excel.png";

	public static final String HLZY_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/hlzy/manage/searchItem.zul";
	public static final String ZHSH_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/zhsh/manage/searchItem.zul";
	public static final String UCZH_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/uczhsh/stat/searchItem.zul";
	public static final String JH_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/jh/manage/searchItem.zul";
	public static final String ZHOL_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/zhol/manage/searchItem.zul";
	public static final String ZHOL_SEARCH_ITEM_PAGE_URL2 = "/WEB-INF/pages/zhol/manage/searchItem2.zul";
	public static final String ZHOL_SEARCH_PET_PAGE_URL = "/WEB-INF/pages/zhol/manage/searchPet.zul";
	public static final String FXWR_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/fxwr/manage/searchItem.zul";
	public static final String WEB_FXWR_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/web_fxwr/manage/searchItem.zul";
	public static final String FCWQ_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/fcwq/manage/searchItem.zul";
	public static final String ANDROID_FXWR_SEARCH_ITEM_PAGE_URL = "/WEB-INF/pages/android_fxwr/manage/searchItem.zul";
	
	public static final int PAGESIZE = 10;
	
	public static Map<String, String> DEBT_STATE = new LinkedHashMap<String, String>();
	static{
		DEBT_STATE.put("0", "未审核");
		DEBT_STATE.put("1", "已通过");
		DEBT_STATE.put("2", "未通过");
		DEBT_STATE.put("3", "已成交");
		DEBT_STATE.put("4", "已完成");
	}
	
	public static Map<String, String> CASH_STATE = new LinkedHashMap<String, String>();
	static{
		CASH_STATE.put("0", "未处理");
		CASH_STATE.put("1", "成功");
		CASH_STATE.put("2", "失败");
	}
}
