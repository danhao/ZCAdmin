package com.zc.common.web.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Vbox;

import com.zc.common.model.Module;
import com.zc.common.service.ModuleService;
import com.zc.common.util.Config;
import com.zc.common.util.Constants;

public class WebUtils {
	private static int cachesize = 1024;
	private static Inflater decompresser = new Inflater();
	private static Deflater compresser = new Deflater();

	public static final String HTTP_PREFIX = "http://";
	public static final String DOMAIN = "domain";
	public static final String ADMIN_ID = "adminId";
	final static Logger logger = LoggerFactory.getLogger(WebUtils.class);

	public static void showPage(String zulFilePathName, String tabName) throws Exception {
		showPage(zulFilePathName, tabName, "/images/icons/16/page.png", null, false);
	}

	public static void showPage(String zulFilePathName, String tabName, Map<String, Object> data) throws Exception {
		showPage(zulFilePathName, tabName, "/images/icons/16/page.png", data, false);
	}

	public static String getModuleLabel(String zulFilePathName) {
		String label = "";
		List<Module> modules = ModuleService.getModules();
		for (Module module : modules) {
			if (zulFilePathName.indexOf("/" + module.getCode() + "/") != -1) {
				label = module.getName();
				break;
			}
		}
		return label;
	}

	public static String createTabName(String zulFilePathName, String tabName) {
		String name = tabName.trim();
		String label = getModuleLabel(zulFilePathName);
		if (StringUtils.isNotBlank(label)) {
			name = label + "_" + tabName.trim();
		}
		return name;
	}

	public static String createTabId(String zulFilePathName, String tabName) {
		return "tab_" + createTabName(zulFilePathName, tabName);
	}

	public static void showPage(String zulFilePathName, String tabName, String imageUrl) throws Exception {
		showPage(zulFilePathName, tabName, imageUrl, null, false);
	}

	public static void showPage(String zulFilePathName, String tabName, String imageUrl, Map<String, Object> data, boolean reOpen) throws Exception {
		try {
			int workWithTabs = 1;
			if (workWithTabs == 1) {
				Tabs tabs = getMainTabs();
				Tab checkTab = null;
				try {
					checkTab = (Tab) tabs.getFellow(createTabId(zulFilePathName, tabName));
					checkTab.setSelected(true);
				} catch (ComponentNotFoundException ex) {
				}

				if (checkTab != null && reOpen) {
					checkTab.onClose();
					showPage(zulFilePathName, tabName, imageUrl, data, false);
				}

				if (checkTab == null) {
					int tabNum = tabs.getChildren().size();
					if (tabNum > Constants.MAX_TAB_NUM) {// 默认的开始tab算一个，因此实际数量为此数量加1
						MsgBox.info("最多只能打开" + (Constants.MAX_TAB_NUM + 1) + "个标签页，请关闭不再使用的标签页。");
						return;
					}
					final Tab tab = new Tab();
					tab.setId(createTabId(zulFilePathName, tabName));
					tab.setLabel(createTabName(zulFilePathName, tabName));
					if (!Constants.START_TAB_LABEL.equals(tab.getLabel())) {
						tab.setClosable(true);
						tab.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
							public void onEvent(Event event) throws Exception {
								tab.onClose();
							}
						});
					}
					if (StringUtils.isNotBlank(imageUrl)) {
						tab.setImage(imageUrl);
					}
					tab.setParent(tabs);

					Tabpanels tabpanels = getMainTabpanels();
					tabpanels.setHeight("100%");
					tabpanels.setStyle("padding: 0px;");
					Tabpanel tabpanel = new Tabpanel();
					tabpanel.setStyle("padding: 0px;");
					tabpanel.setParent(tabpanels);
					Executions.createComponents(zulFilePathName, tabpanel, data);
					tab.setSelected(true);
				}
			} else {
				Div divCenter = (Div) getMainCenter().getFirstChild();
				divCenter.getChildren().clear();
				Panel panel = new Panel();
				panel.setTitle(tabName);
				divCenter.appendChild(panel);
				Executions.createComponents(zulFilePathName, divCenter, data);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public static Center getMainCenter() {
		return ((Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain")).getCenter();
	}

	public static Tabs getMainTabs() {
		return (Tabs) getMainCenter().getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");
	}

	public static Tabpanels getMainTabpanels() {
		return (Tabpanels) getMainTabs().getFellow("tabpanelsBoxIndexCenter");
	}

	public static String sendAdminRequest(String host, int port, String requestUrl, List<NameValuePair> qparams) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		URI uri = URIUtils.createURI("http", host, port, requestUrl, URLEncodedUtils.format(qparams, HTTP.UTF_8), null);
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		httpget.abort();
		httpclient.getConnectionManager().shutdown();
		httpclientLog(uri, result.toString(), qparams);
		return result.toString();
	}

	public static String sendAdminRequest(String url, List<NameValuePair> qparams) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url + "?" + URLEncodedUtils.format(qparams, HTTP.UTF_8));
		logger.debug("sendAdminRequest|{}", httpget.getURI().toString());
		HttpResponse response = httpclient.execute(httpget);
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		httpget.abort();
		httpclient.getConnectionManager().shutdown();
		return result.toString();
	}

	public static String sendAdminRequestForTextPlain(String host, int port, String requestUrl, List<NameValuePair> qparams) throws Exception {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);

		HttpClient httpclient = new DefaultHttpClient(httpParams);
		URI uri = URIUtils.createURI("http", host, port, requestUrl, URLEncodedUtils.format(qparams, HTTP.UTF_8), null);
		HttpGet httpget = new HttpGet(uri);
		httpget.setParams(httpParams);
		HttpResponse response = httpclient.execute(httpget);
		String result = EntityUtils.toString(response.getEntity());
		httpget.abort();
		httpclient.getConnectionManager().shutdown();
		httpclientLog(uri, result, qparams);
		return result;
	}

	private static void httpclientLog(URI uri, String result, List<NameValuePair> qparams) {
		logger.info("uri:{}#{}#{}", new Object[] { uri, qparams, result });
	}

	public static boolean sendAdminRequestForHlzy(List<NameValuePair> qparams) throws Exception {
		String host = Config.getConfig().get(Constants.MANOR_ADMIN_SERVER);
		int port = Config.getConfig().getInt(Constants.MANOR_ADMIN_SERVER_PORT);
		String requestUrl = Config.getConfig().get(Constants.MANOR_ADMIN_SERVER_URL);
		String result = sendAdminRequest(host, port, requestUrl, qparams);
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.indexOf("\"ok\"") != -1) ? true : false;
	}

	public static boolean sendAdminRequestForZhsh(String domain, List<NameValuePair> qparams) throws Exception {
		String result = sendAdminRequestForZhshResult(domain, qparams);
		if (result == null || StringUtils.isBlank(result)) {
			return false;
		}
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.indexOf("\"ok\"") != -1) ? true : false;
	}

	public static String sendAdminRequestForZhshResult(String domain, List<NameValuePair> qparams) throws Exception {
		String result = null;
		if (domain.indexOf(Constants.PORT_SPLIT_SYMBOL) != -1) {
			String host = domain.split(Constants.PORT_SPLIT_SYMBOL)[0];
			int port = NumberUtils.toInt(domain.split(Constants.PORT_SPLIT_SYMBOL)[1]);
			String requestUrl = Config.getConfig().get(Constants.ZHSH_ADMIN_SERVER_URL);
			result = sendAdminRequest(host, port, requestUrl, qparams);
		}
		return result;
	}

	public static boolean postFormForZhsh(String domain, List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + domain + Config.getConfig().get(Constants.ZHSH_ADMIN_SERVER_URL);
		return postForm(submitUrl, formparams);
	}

	public static boolean postFormForJh(String domain, List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + domain + Config.getConfig().get(Constants.JH_ADMIN_SERVER_URL);
		return postForm(submitUrl, formparams);
	}

	public static boolean postFormForHlzy(List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + getHlzyAdminDomain() + Config.getConfig().get(Constants.MANOR_ADMIN_SERVER_URL);
		return postForm(submitUrl, formparams);
	}

	public static boolean postFormForFxwr(String submitUrl, List<NameValuePair> formparams) throws Exception {
		return postForm(submitUrl, formparams);
	}

	public static boolean postFormForWebFxwr(List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + Config.getConfig().get(Constants.WEB_FXWR_ADMIN_SERVER_URL);
		return postForm(submitUrl, formparams);
	}

	public static JSONObject postFormForFcwq(List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + Config.getConfig().get(Constants.FCWQ_ADMIN_SERVER_URL);
		return post(submitUrl, formparams);
	}

	public static JSONObject postFormForFcwq(String domain, List<NameValuePair> formparams) throws Exception {
		String submitUrl = HTTP_PREFIX + domain;
		return post(submitUrl, formparams);
	}

	private static String getHlzyAdminDomain() {
		String host = Config.getConfig().get(Constants.MANOR_ADMIN_SERVER);
		int port = Config.getConfig().getInt(Constants.MANOR_ADMIN_SERVER_PORT);
		String domain = host + ":" + port;
		return domain;
	}

	public static boolean postForm(String submitUrl, List<NameValuePair> formparams) throws Exception {
		StringBuilder result = portReuest(submitUrl, formparams);
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.toString().toLowerCase().indexOf("\"ok\"") != -1) ? true : false;
	}

	public static JSONObject post(String submitUrl, List<NameValuePair> formparams) throws Exception {
		StringBuilder ret = portReuest(submitUrl, formparams);
		String result = "{}";
		if (ret != null) {
			result = ret.toString();
		}
		return new JSONObject(result);
	}

	/**
	 * web版本非仙勿扰post提交.
	 * 
	 * @param domain
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static JSONObject postFormReturnJsonForWebFxwr(String domain, JSONObject json) throws Exception {
		return postJsonByte(domain, json);
	}

	/**
	 * post json提交
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject postJson(String url, JSONObject json) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity stringEntity = new StringEntity(json.toString(), "application/json", HTTP.UTF_8);
			post.setEntity(stringEntity);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	/**
	 * 压缩json提交
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static JSONObject postJsonByte(String url, JSONObject json) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(WebUtils.HTTP_PREFIX + url);
		// logger.error(post.getURI().toString());
		JSONObject response = null;
		try {
			ByteArrayEntity byteEntity = new ByteArrayEntity(compressBytes(json.toString().getBytes(HTTP.UTF_8)));
			post.setEntity(byteEntity);

			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				response = new JSONObject(new String(decompressBytes(inputStreamToByte(entity.getContent())), HTTP.UTF_8));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	public static JSONObject postJson(String submitUrl, List<NameValuePair> formparams) {
		JSONObject response = null;
		try {
			StringBuilder result = portReuest(submitUrl, formparams);
//			response = new JSONObject(new String(result.toString().getBytes("GBK"), "UTF-8"));
			response = new JSONObject(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private static StringBuilder portReuest(String submitUrl, List<NameValuePair> formparams) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", 30000);
		HttpPost httppost = new HttpPost(submitUrl);
		httppost.setEntity(entity);
		HttpResponse response = httpclient.execute(httppost);

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder result = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		httppost.abort();
		httpclient.getConnectionManager().shutdown();
		return result;
	}

	public static void searchHlzyItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.HLZY_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchZhsyItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.ZHSH_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchUcZhsyItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.UCZH_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchJhItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.JH_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchZholItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.ZHOL_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchZholPet(Bandbox bd_pet, Vbox vbox_pet) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("bd_pet", bd_pet);
		Executions.createComponents(Constants.ZHOL_SEARCH_PET_PAGE_URL, vbox_pet, args);
	}

	public static void searchZholItem2(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("bd_mail_item", bd_item);
		args.put("gold", gold);
		Executions.createComponents(Constants.ZHOL_SEARCH_ITEM_PAGE_URL2, vbox_item, args);
	}

	public static void searchFxwrItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.FXWR_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchWebFxwrItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.WEB_FXWR_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchAdnroidFxwrItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.ANDROID_FXWR_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	public static void searchFcwqItem(Bandbox bd_item, Vbox vbox_item, boolean gold) {
		Map<String, Object> args = initParam(bd_item, gold);
		Executions.createComponents(Constants.FCWQ_SEARCH_ITEM_PAGE_URL, vbox_item, args);
	}

	private static Map<String, Object> initParam(Bandbox bd_item, boolean gold) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("bd_item", bd_item);
		args.put("gold", gold);
		return args;
	}

	public static boolean sendAdminRequestForJh(String domain, List<NameValuePair> qparams) throws Exception {
		String result = sendAdminRequestForJhResult(domain, qparams);
		if (result == null) {
			return false;
		}
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.toLowerCase().indexOf("\"ok\"") != -1) ? true : false;
	}

	public static String sendAdminRequestForZholResult(String domain, List<NameValuePair> qparams) throws Exception {
		String result = null;
		if (domain.indexOf(Constants.PORT_SPLIT_SYMBOL) != -1) {
			String host = domain.split(Constants.PORT_SPLIT_SYMBOL)[0];
			int port = NumberUtils.toInt(domain.split(Constants.PORT_SPLIT_SYMBOL)[1]);
			String requestUrl = Config.getConfig().get(Constants.ZHOL_ADMIN_SERVER_URL);
			result = sendAdminRequest(host, port, requestUrl, qparams);
		}
		return result;
	}

	public static boolean sendAdminRequestForZhol(String domain, List<NameValuePair> qparams) throws Exception {
		String result = sendAdminRequestForZholResult(domain, qparams);
		if (result == null) {
			return false;
		}
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.indexOf("success") != -1 || result.toLowerCase().indexOf("\"ok\"") != -1) ? true : false;
	}

	public static String sendAdminRequestForJhResult(String domain, List<NameValuePair> qparams) throws Exception {
		String result = null;
		if (domain.indexOf(Constants.PORT_SPLIT_SYMBOL) != -1) {
			String host = domain.split(Constants.PORT_SPLIT_SYMBOL)[0];
			int port = NumberUtils.toInt(domain.split(Constants.PORT_SPLIT_SYMBOL)[1]);
			String requestUrl = Config.getConfig().get(Constants.JH_ADMIN_SERVER_URL);
			result = sendAdminRequest(host, port, requestUrl, qparams);
		}
		return result;
	}

	public static String sendAdminRequestForFxwrResult(final String url, List<NameValuePair> qparams) throws Exception {
		String hp = url;
		String requestUrl = "";
		if (url.indexOf(Constants.URL_SPLIT_SYMBOL) != -1) {
			String[] us = url.split(Constants.URL_SPLIT_SYMBOL);
			int len = us.length;
			if (len >= 1) {
				hp = us[0];
			}

			if (len >= 2) {
				requestUrl = us[1];
			}
		}

		logger.debug("sendAdminRequestForFxwrResult:{}",url);
		String host = hp.split(Constants.PORT_SPLIT_SYMBOL)[0];
		int port = NumberUtils.toInt(hp.split(Constants.PORT_SPLIT_SYMBOL)[1]);
		return sendAdminRequestForTextPlain(host, port, requestUrl, qparams);
	}

	public static String sendAdminRequestResult(String domain, List<NameValuePair> qparams) throws Exception {
		String url = domain;
		String hp = url;
		String requestUrl = "";
		if (url.indexOf(Constants.URL_SPLIT_SYMBOL) != -1) {
			String[] us = url.split(Constants.URL_SPLIT_SYMBOL);
			int len = us.length;
			if (len >= 1) {
				hp = us[0];
			}

			if (len >= 2) {
				requestUrl = us[1];
			}
		}

		String[] hps = hp.split(Constants.PORT_SPLIT_SYMBOL, 2);
		int psLen = ArrayUtils.getLength(hps);

		String host = "";
		if (psLen >= 1) {
			host = hps[0];
		}

		int port = 80;
		if (psLen >= 2) {
			port = NumberUtils.toInt(hps[1]);
		}
		return sendAdminRequestForTextPlain(host, port, requestUrl, qparams);
	}

	/**
	 * get请求返回成功或失败
	 * 
	 * @param qparams
	 * @return
	 * @throws Exception
	 */
	public static boolean sendAdminRequestForFxwr(final String url, List<NameValuePair> qparams) throws Exception {
		String result = sendAdminRequestForFxwrResult(url, qparams);
		if (result == null) {
			return false;
		}
		return (result.indexOf("\"true\"") != -1 || result.indexOf("true") != -1 || result.indexOf("success") != -1 || result.toLowerCase().indexOf("\"ok\"") != -1) ? true : false;
	}

	/**
	 * get请求返回json
	 * 
	 * @param qparams
	 * @return
	 * @throws Exception
	 */
	public static JSONObject sendAdminRequestForFxwrJsonResult(final String url, List<NameValuePair> qparams) throws Exception {
		String result = sendAdminRequestForFxwrResult(url, qparams);
		if (result == null || result.indexOf("result") == -1) {
			result = "{}";
		}
		return new JSONObject(result);
	}

	public static JSONObject sendAdminRequestForJsonResult(String domain, List<NameValuePair> qparams) throws Exception {
		String json = sendAdminRequestResult(domain, qparams);
		if (json == null || json.indexOf("result") == -1) {
			json = "{}";
		}
		return new JSONObject(json);
	}

	public static void handleResult(boolean tip) throws Exception {
		if (tip) {
			MsgBox.info("操作成功。");
		} else {
			MsgBox.alert("操作失败。");
		}
	}

	public static void handleResult(JSONObject json) throws Exception {
		String status = json.getString("status");
		handleResult("succ".equals(status));
	}

	@SuppressWarnings("unchecked")
	public static void selectedListBox(Listbox listbox, String value) {
		int i = 0;
		for (Listitem lt : (List<Listitem>) listbox.getItems()) {
			if (String.valueOf(value).equals(String.valueOf(lt.getValue()))) {
				listbox.setSelectedIndex(i);
				break;
			}
			i++;
		}
	}

	public static JSONObject postWebFxwrJsonReturn(final String domain, final String cmd, final String id) throws Exception {
		// logger.error("postWebFxwrJsonReturn|" + domain + "|" + cmd + "|" + id);
		return postWebFxwrJsonReturn(domain, cmd, id, null);
	}

	public static JSONObject postWebFxwrJsonReturn(final String domain, final String cmd, final String id, final String[] data) throws Exception {
		JSONObject jsonPs = new JSONObject();
		jsonPs.put(Constants.CMD, cmd);
		jsonPs.put(Constants.PID, id);
		if (data != null) {
			jsonPs.put(Constants.DATA, data);
		}
		return WebUtils.postFormReturnJsonForWebFxwr(domain, jsonPs);
	}

	public static void postWebFxwrJson(final String domain, final String cmd, final String id) throws Exception {
		postWebFxwrJson(domain, cmd, id, null);
	}

	public static void postWebFxwrJson(final String domain, final String cmd, final String id, final String[] data) throws Exception {
		WebUtils.handleResult(postWebFxwrJsonReturn(domain, cmd, id, data));
	}

	public static String getFxwrAdminId(Map<String,String> fxwr_admin_uids) {
		return fxwr_admin_uids.get("0");
	}

	public static String getWebFxwrAdminId() {
		return Config.getConfig().get(Constants.WEB_FXWR_ADMIN_ID);
	}

	public static String getSexName(int sex) {
		return (sex == 0) ? "女" : (sex == 1) ? "男" : "未知";
	}

	public static String getNewSexName(int sex) {
		return (sex == 1) ? "女" : (sex == 2) ? "男" : "未知";
	}

	public static Map<Integer, String> getFxwrResons() {
		Map<Integer, String> resons = new HashMap<Integer, String>();
		resons.put(0, "刷屏");
		resons.put(1, "发布色情言论");
		resons.put(2, "发布政治反动言论");
		resons.put(3, "发布广告");
		resons.put(4, "使用不文明用语");
		resons.put(5, "诈骗行为");
		resons.put(6, "恶意利用BUG，破坏游戏平衡");
		resons.put(20, "其它");
		return resons;
	}

	/**
	 * Zlib 压缩
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] compressBytes(byte input[]) {
		compresser.reset();
		compresser.setInput(input);
		compresser.finish();
		byte output[] = new byte[0];
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];
			int got;
			while (!compresser.finished()) {
				got = compresser.deflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	/**
	 * Zlib 解压缩
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] decompressBytes(byte input[]) {
		byte output[] = new byte[0];
		decompresser.reset();
		decompresser.setInput(input);
		ByteArrayOutputStream o = new ByteArrayOutputStream(input.length);
		try {
			byte[] buf = new byte[cachesize];
			int got;
			while (!decompresser.finished()) {
				got = decompresser.inflate(buf);
				o.write(buf, 0, got);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}

	public static byte[] inputStreamToByte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	public static Map<String, String> getWebfxwrServer(Map<String, Map<String, String>> webFxwrServers, final String id) {
		int serverNum = webFxwrServers.size();
		int index = Math.abs(id.hashCode()) % serverNum;
		return webFxwrServers.get(String.valueOf(index));
	}

	public static void handleFcwqResult(String domain, List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.sendAdminRequestForJsonResult(domain, formparams);
		WebUtils.handleResult(!jsonData.isNull("result") && jsonData.getBoolean("result"));
	}
	
	public static void handlePostResult(String domain, List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.postJson(domain, formparams);
		WebUtils.handleResult(!jsonData.isNull("result") && jsonData.getBoolean("result"));
	}
	
	public static void handleSgPostResult(String domain, List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.postJson(domain, formparams);
		WebUtils.handleResult(jsonData!=null && !jsonData.isNull("ret") && jsonData.getBoolean("ret"));
	}
	public static boolean handleSgPostResult2(String domain, List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.postJson(domain, formparams);
		WebUtils.handleResult(jsonData!=null && !jsonData.isNull("ret") && jsonData.getBoolean("ret"));
		return jsonData!=null && !jsonData.isNull("ret") && jsonData.getBoolean("ret");
	}

	public static void handleFcwqPostRequestResult(List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.postFormForFcwq(formparams);
		WebUtils.handleResult(!jsonData.isNull("result") && jsonData.getBoolean("result"));
	}

	public static void handleFcwqPostRequestResult(String domain, List<NameValuePair> formparams) throws Exception, JSONException {
		JSONObject jsonData = WebUtils.postFormForFcwq(domain, formparams);
		WebUtils.handleResult(!jsonData.isNull("result") && jsonData.getBoolean("result"));
	}

	public static boolean handleFcwqResult(JSONObject jsonData) {
		return (jsonData != null && jsonData.has("result") && jsonData.optBoolean("result"));
	}

	public static boolean handleJsonResult(JSONObject jsonData) {
		return (jsonData != null && jsonData.has("result") && jsonData.optBoolean("result"));
	}

	public static Map<String, String> getFxwrAdminServerMap(Map<String, Map<String, String>> fxwr_admins, String playerId) {
		int hash = getHashIndex(playerId, fxwr_admins.size());
		return fxwr_admins.get(String.valueOf(hash));
	}

	public static String getFxwrAdminServerDomain(Map<String, Map<String, String>> fxwr_admins, String playerId) {
		return getFxwrAdminServerMap(fxwr_admins, playerId).get("domain");
	}

	public static Map<String, String> getFcwqAdminServerMap(Map<String, Map<String, String>> fcwq_admins, String playerId) {
		int hash = getCrc32Hash(fcwq_admins, playerId);
		return fcwq_admins.get(String.valueOf(hash));
	}

	public static int getCrc32Hash(Map<String, Map<String, String>> fcwq_admins, String playerId) {
		CRC32 crc32 = new CRC32();
		crc32.update(playerId.getBytes());
		int hash = (int) (crc32.getValue() % fcwq_admins.size());
		return hash;
	}

	public static String getFcwqAdminServerDomain(Map<String, Map<String, String>> fcwq_admins, String playerId) {
		return getFcwqAdminServerMap(fcwq_admins, playerId).get("domain");
	}

	public static String getFcwqAdminServerAdminId(Map<String, Map<String, String>> fcwq_admins, String playerId, int serverNum) {
		return getFcwqAdminServerMap(fcwq_admins, playerId).get("admin_id");
	}

	public static String getKfAdminServerDomain(Map<String, Map<String, String>> kfZones, String zone, String playerId) {
		return "http://" + kfZones.get(zone).get("domain") + "/";
	}
	
	public static String getAdminServerDomain(Map<String, Map<String, String>> sgZones, String zone) {
		return "http://" + sgZones.get(zone).get("domain") + "/";
	}

	public static List<Map<String, String>> getSearchSmses(String sms) {
		sms = sms.replaceAll("[\r\n]{2,3}", "=\r\n");
		final String regex = "(.+)\\t|([\\d]{4}.[\\d]{1,2}.[\\d]{1,2}.[\\d]{1,2}:[\\d]{1,2})|(.*=)";
		Matcher m = Pattern.compile(regex).matcher(sms);

		List<Map<String, String>> smses = new LinkedList<Map<String, String>>();
		int i = 1;
		Map<String, String> oneSms = new LinkedHashMap<String, String>();
		while (m.find()) {
			String key = (i == 1) ? "phone" : (i == 2) ? "date" : "uid";
			oneSms.put(key, m.group().trim().replace("=", ""));
			if (i % 3 == 0) {
				i = 1;
				smses.add(oneSms);
				oneSms = new LinkedHashMap<String, String>();
				continue;
			}
			i++;
		}
		return smses;
	}

	public static int getHashIndex(String id, int num) {
		return getHashcode(id) % num;
	}

	public static int getHashcode(String id) {
		if (StringUtils.isBlank(id)) {
			return 0;
		}
		return (id.hashCode() == Integer.MIN_VALUE) ? Integer.MIN_VALUE + 1 : Math.abs(id.hashCode());
	}

	public static void main(String[] args) {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair(Constants.CMD, "m_UPP|2|1_2_1"));
		httpclientLog(null, "true", qparams);
	}

	public static String getFxwrAdminUrl(String zone, Map<String, String> fxwr_zones_admin_url) {
		return (fxwr_zones_admin_url.containsKey(zone)) ? fxwr_zones_admin_url.get(zone) : null;
	}

	public static String getCmdData(final String cmd, final String pid, String... datas) {
		String s = StringUtils.join(datas, "_");
		return cmd + "|" + pid + "|" + (s == null?"":s);
	}
}
