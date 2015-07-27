package com.zc.common.web;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.West;
import org.zkoss.zul.Label;

import com.zc.common.util.Config;
import com.zc.common.util.Constants;
import com.zc.common.web.util.GFCBaseCtrl;

public class IndexCtrl extends GFCBaseCtrl implements Serializable {
	private static final long serialVersionUID = 2091887899877482239L;
	private static final String[] whiteIpList = new String[]{ 
		"127.0.0.1",
		"192.168.1", 
		"58.61.152.91", 
		"58.61.152.87" ,
		"58.61.39.100" ,
		"218.17.157.170" ,
		"121.35.211" , //全网段
		"183.12.64" ,  //全网段
		"113.116.62" ,  //全网段
		"183.12.66.203", 
		"218.107.55.252",
		"218.107.55.253",
		"218.107.55.254"
		};

	private transient Label lb_login_user_name;
	private transient Label lb_change_password;
	private transient Label lb_app_title;

	public IndexCtrl() {
		super();
	}

	@Override
	public void init() {
		desktop.getPage("indexPage").setTitle(Config.getConfig().get(Constants.APP_TITLE));
		lb_app_title.setValue(Config.getConfig().get(Constants.APP_TITLE));
		lb_login_user_name.setValue(getLoggedInUserName());
		lb_change_password.addEventListener(Events.ON_CLICK, new EventListener() {
			public void onEvent(Event event) throws Exception {
				Executions.createComponents("/WEB-INF/pages/common/sec/userChangePasswordDialog.zul", null, null);
			}
		});
	}

	public void onCreate$outerIndexWindow(Event event) throws Exception {
		String name =getLoggedInUserName();
		//String ip = Executions.getCurrent().getRemoteAddr();
		String ip=getIpAddr();
		Executions.getCurrent().getHeaderNames().toString();
//		System.out.println("name:"+name+"ip:"+ip+"-----------------------------");
		if(!inwhiteIpList(ip)){
			getUserWorkspace().doLogout();
			return;
		}else{
			init();
			createMainTreeMenu();
		}
		/*init();
		createMainTreeMenu();*/
	}

	public void onClick$lb_logout(Event event) throws Exception {
		getUserWorkspace().doLogout();
	}

	public String getIpAddr() {
		   HttpServletRequest request = null;
		   if(Sessions.getCurrent() != null){//ZK获得Request
			   ServletContext sc = (ServletContext) Sessions.getCurrent().getWebApp().getNativeContext();
			   request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		   }
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
//	       System.out.println("ipAddress:"+ip);
	       return ip;
	}

	private boolean inwhiteIpList(String ip){
		if(ip==null || "".equals(ip)){
			return false;
		}
		for(String str : whiteIpList){
			if(ip.startsWith(str)){
				return true;
			}
		}
		return true;
	}
	
	private void createMainTreeMenu() {
		Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
		West west = bl.getWest();
		west.getChildren().clear();
		west.setTitle(Config.getConfig().get(Constants.MENU_TREE_TITLE));
		Executions.createComponents("/WEB-INF/pages/common/menu/mainMenu.zul", west, null);
	}
}
