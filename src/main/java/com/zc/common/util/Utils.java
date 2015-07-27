package com.zc.common.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;

public abstract class Utils {
	public static String getListCellStyle(boolean val) {
		return val ? "color:green" : "color:red";
	}

	
	public static void bindDateTimeAllData(Row row, Date date) {
		if (date == null) {
			new Label("").setParent(row);
		} else {
			new Label(DateFormatUtils.format(date, Constants.DEFAULT_CREATED_AT_ALL_FORMAT)).setParent(row);
		}
	}

	public static void bindDateTimeData(Row row, Date date) {
		if (date == null) {
			new Label("").setParent(row);
		} else {
			new Label(DateFormatUtils.format(date, Constants.DEFAULT_CREATED_AT_FORMAT)).setParent(row);
		}
	}

	public static void bindDateData(Row row, Date date) {
		if (date == null) {
			new Label("").setParent(row);
		} else {
			new Label(String.valueOf(DateFormatUtils.format(date, Constants.DEFAULT_STAT_FORMAT))).setParent(row);
		}
	}

	public static void bindItemMoveDateData(Row row, Date date) {
		new Label(String.valueOf(DateFormatUtils.format(date, Constants.DEFAULT_CREATED_AT_ALL_FORMAT))).setParent(row);
	}

	public static void bindData(Row row, Integer data, boolean format) {
		if (data == null) {
			data = 0;
		}
		if (format) {
			new Label(formatNum(data)).setParent(row);
		} else {
			new Label(String.valueOf(data)).setParent(row);
		}
	}

	public static void bindData(Row row, Long data, boolean format) {
		if (data == null) {
			data = 0L;
		}
		if (format) {
			new Label(formatNum(data)).setParent(row);
		} else {
			new Label(String.valueOf(data)).setParent(row);
		}
	}

	public static void bindData(Row row, Long data) {
		bindData(row, data, false);
	}

	public static void bindData(Row row, Integer data) {
		bindData(row, data, false);
	}

	public static void bindData(Row row, String data) {
		new Label(data).setParent(row);
	}

	public static void bindData(Listitem li, String data){
		new Listcell(data).setParent(li);
	}
	
	public static void bindData(Listitem li, long data){
		new Listcell(formatNum(data)).setParent(li);
	}
	
	public static Date getMonthStart() {
		return getMonthStart(new Date());
	}
	
	public static Date getNextDay(Date date){
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, 1);
		return cl.getTime();
	}
	public static Date getYearStart(){
		Date date = new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.DAY_OF_YEAR, -cl.get(Calendar.DAY_OF_YEAR)+1);
		return cl.getTime();
	}
	
	public static Date getYearEnd(){
		Date date = Utils.getYearStart();
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.YEAR, 1);
		cl.add(Calendar.DAY_OF_YEAR, -1);
		return cl.getTime();
	}
	
	public static Date getLastDay() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return calendar.getTime();
	}

	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getMonthEnd() {
		return getMonthEnd(new Date());
	}

	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getDateStart() {
		return getDateStart(new Date());
	}

	public static Date getDateStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getDateEnd() {
		return getDateEnd(new Date());
	}

	public static Date getDateEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static String formatNum(Double data) {
		DecimalFormat def = new DecimalFormat("###,###.##");
		return def.format(data);
	}

	public static String formatNum(Integer data) {
		DecimalFormat def = new DecimalFormat("###,###");
		return def.format(data);
	}

	public static String formatNum(Long data) {
		DecimalFormat def = new DecimalFormat("###,###");
		return def.format(data);
	}

	public static int boolean2Flag(boolean val) {
		return val ? 1 : 0;
	}

	public static boolean flag2Boolean(String val) {
		return "1".equals(val) ? true : "true".equalsIgnoreCase(val) ? true : "yes".equalsIgnoreCase(val) ? true : false;
	}

	public static boolean flag2Boolean(Object val) {
		return flag2Boolean(String.valueOf(val));
	}

	public static Date Int2Date(int i) {
		return new Date(((long) i) * 1000);
	}

	public static String int2FormatDate(int i) {
		return int2FormatDate(i, "yyyy-MM-dd");
	}
	
	public static String int2FormatDate(int i, String format) {
	    if(i<=0){
	        return "";
	    }
		Date date = new Date(((long) i) * 1000);
		return DateFormatUtils.format(date, format);
	}
	
	/**
	 * 往后推几天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDays(Date date, int day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, day);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(int2FormatDate(259400634, "yyyy-MM-dd HH:mm:ss"));
	}
}
