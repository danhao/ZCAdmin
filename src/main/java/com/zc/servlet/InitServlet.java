package com.zc.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zc.common.service.ModuleService;

public class InitServlet extends HttpServlet {
	final static Logger logger = LoggerFactory.getLogger(InitServlet.class);

	private static final long serialVersionUID = 1L;

	public InitServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

		com.zc.common.service.MenuService menuService = (com.zc.common.service.MenuService) wac.getBean("menuService");
		ModuleService module = new ModuleService(menuService);
		module.loadModules();

//		ItemService itemService = (ItemService) wac.getBean("itemService");
//		itemService.loadItems();
//
//		com.zc.fxwr.res.service.ItemService fxwrItemService = (com.zc.fxwr.res.service.ItemService) wac.getBean("fxwrItemService");
//		fxwrItemService.loadItems();
//
//		com.zc.fxwr.res.service.ItemTypeService fxwrItemTypeService = (com.zc.fxwr.res.service.ItemTypeService) wac.getBean("fxwrItemTypeService");
//		fxwrItemTypeService.loadItemTypes();
//
//		com.zc.androidfxwr.res.service.ItemService androidFxwrItemService = (com.zc.androidfxwr.res.service.ItemService) wac.getBean("androidFxwrItemService");
//		androidFxwrItemService.loadItems();
//
//		com.zc.androidfxwr.res.service.ItemTypeService androidFItemTypeService = (com.zc.androidfxwr.res.service.ItemTypeService) wac.getBean("androidFxwrItemTypeService");
//		androidFItemTypeService.loadItemTypes();
	}
}
