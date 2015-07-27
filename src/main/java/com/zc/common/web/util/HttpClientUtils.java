package com.zc.common.web.util;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.zc.common.util.Constants;

public class HttpClientUtils {

    public static final String HTTP_SCHEME = "http";

    /**
     * 发送请求，返回json结果
     * 
     * @param domain
     * @param qparams
     * @return
     * @throws Exception
     */
    public static JSONObject sendAndReturnJsonResult(final String domain, final List<NameValuePair> ps) throws Exception {
        String json = sendAndReturnStrResult(domain, ps);
        if (StringUtils.isBlank(json) || json.indexOf("result") == -1) {
            json = "{}";
        }
        return new JSONObject(json);
    }

    /**
     * 发送请求，返回字符串结果
     * 
     * @param domain
     * @param qparams
     * @return
     * @throws Exception
     */
    public static String sendAndReturnStrResult(final String domain, final List<NameValuePair> ps) throws Exception {
        String url = domain;
        String hp = url;
        String path = "";
        if (url.indexOf(Constants.URL_SPLIT_SYMBOL) != -1) {
            String[] us = url.split(Constants.URL_SPLIT_SYMBOL);
            int len = us.length;
            if (len >= 1) {
                hp = us[0];
            }

            if (len >= 2) {
                path = us[1];
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
        return sendAndReturnStrResult(host, port, path, ps);
    }

    /**
     * 发送请求，返回字符串结果
     * 
     * @param host
     * @param port
     * @param path
     * @param ps
     * @return
     * @throws Exception
     */
    public static String sendAndReturnStrResult(final String host, final int port, final String path, final List<NameValuePair> ps) throws Exception {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 3000);

        HttpClient httpclient = new DefaultHttpClient(httpParams);
        URI uri = URIUtils.createURI(HTTP_SCHEME, host, port, path, URLEncodedUtils.format(ps, HTTP.UTF_8), null);
        HttpGet httpget = new HttpGet(uri);
        httpget.setParams(httpParams);
        HttpResponse response = httpclient.execute(httpget);
        String result = EntityUtils.toString(response.getEntity());
        httpget.abort();
        httpclient.getConnectionManager().shutdown();
        return result;
    }

    public static boolean handleSendResult(JSONObject jsonData) {
        return (jsonData != null && jsonData.has("result") && jsonData.optBoolean("result"));
    }

    public static void handleSendResult(final String domain, final List<NameValuePair> ps) throws Exception, JSONException {
        JSONObject jsonData = sendAndReturnJsonResult(domain, ps);
        handleResult(handleSendResult(jsonData));
    }

    public static void handleResult(boolean tip) throws Exception {
        if (tip) {
            MsgBox.info("操作成功。");
        } else {
            MsgBox.alert("操作失败。");
        }
    }

    public static String getServerDomain(Map<String, Map<String, String>> admins, String pid) {
        return getServerDomainMap(admins, pid).get("domain");
    }

    public static Map<String, String> getServerDomainMap(Map<String, Map<String, String>> admins, String pid) {
        return admins.get(String.valueOf(getHashIndex(pid, admins.size())));
    }

    public static int getHashIndex(String id, int num) {
        final int hashCode = id.hashCode();
        return Math.abs(hashCode % num);
    }
}
