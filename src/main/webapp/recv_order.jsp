<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%
try{
  request.setCharacterEncoding("UTF-8");

  String result=request.getParameter("result");
  String orderid=request.getParameter("orderid");
  String amount=request.getParameter("amount");//最终支付金额，实际支付金额
  String mid=request.getParameter("mid");
  String gid=request.getParameter("gid");
  String sid=request.getParameter("sid");
  String uif=request.getParameter("uif");
  String utp=request.getParameter("utp");
  String eif=request.getParameter("eif");
  String pcid=request.getParameter("pcid");
  String cardno=request.getParameter("cardno");
  String timestamp=request.getParameter("timestamp");
  String errorcode=request.getParameter("errorcode");
  String verstr=request.getParameter("verstring");
  String remark=request.getParameter("remark");

  StringBuilder sb=new StringBuilder();
  sb.append("itype=1");
  sb.append("&result=").append(result);
  sb.append("&orderid=").append(orderid);
  sb.append("&amount=").append(amount);
  sb.append("&mid=").append(mid.toString());
  sb.append("&gid=").append(gid.toString());
  sb.append("&sid=").append(sid.toString());
  sb.append("&uif=").append(uif);//java.net.URLEncoder.encode(uif,"UTF-8")
  sb.append("&utp=").append(utp.toString());
  sb.append("&eif=").append(eif);
  sb.append("&pcid=").append(pcid);
  sb.append("&cardno=").append(cardno);
  sb.append("&timestamp=").append(timestamp);
  sb.append("&errorcode=").append(errorcode);
  sb.append("&verstring=").append(verstr);
  sb.append("&remark=").append(URLEncoder.encode(remark,"UTF-8"));
	
	String[] ss = eif.split("@");
	String urlStr = "http://221.181.72.49:11010/";
	//if(ss.length > 1){
	//			
	//}
	
		PrintWriter writer = null;
		HttpURLConnection httpConn = null;
		String res = null;
		try {
			byte[] data = sb.toString().getBytes();
			URL url = new URL(urlStr);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("ContentType",
					"application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Content-Length", String
					.valueOf(data.length));
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setConnectTimeout(30000);// jdk 1.5换成这个,连接超时
			httpConn.setReadTimeout(30000);// jdk 1.5换成这个,读操作超时
			httpConn.connect();
			OutputStream os = httpConn.getOutputStream();
			os.write(data);
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == responseCode) {
				byte[] buffer = new byte[1024];
				int len = -1;
				InputStream is = httpConn.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while ((len = is.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				res = bos.toString();
				is.close();
				
				out.print(res);
			} else {
				out.print("failed");
			}
		} catch (Exception ex) {
		out.print(ex);
			out.print("failed");
		} finally {
			if (null != writer) {
				writer.close();
			}
			if (null != httpConn) {
				httpConn.disconnect();
			}
		}
  
}catch(Exception e){
	out.print("failed");
}  
%>
