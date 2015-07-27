package com.zc.common.web.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

public abstract class BaseReport<T extends Object> {
	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private String exportFileName;
	private String title;
	private int cellHeaderLen;

	public BaseReport(String exportFileName, String title) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
		this.exportFileName = exportFileName;
		this.title = title;
		this.cellHeaderLen = getCellHeader().length;
		createTitleRow();
		createTableHeader();
	}

	public abstract String[] getCellHeader();

	public abstract void exportExcel(List<T> data);

	public HSSFCellStyle getTitleCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setColor(HSSFColor.BLUE.index);
		font.setFontHeightInPoints((short) 15);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		titleCellStyle.setFont(font);
		return titleCellStyle;
	}

	public HSSFCellStyle getHeaderCellStyle() {
		HSSFCellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setWrapText(true);
		headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		return headerCellStyle;
	}

	public void createTableHeader() {
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 500);
		for (int i = 0; i < cellHeaderLen; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(getHeaderCellStyle());
			cell.setCellValue(getCellHeader()[i]);
			sheet.setColumnWidth(i, 4000);
		}
	}

	public void createTitleRow() {
		HSSFRow row_title = sheet.createRow(0);
		row_title.setHeight((short) 500);
		HSSFCell row_title_cell = row_title.createCell(0);
		row_title_cell.setCellValue(getTitle());
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellHeaderLen - 1));
		row_title_cell.setCellStyle(getTitleCellStyle(workbook));
	}

	public void downloadFile(HSSFWorkbook workbook) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			workbook.write(stream);
			InputStream is = new ByteArrayInputStream(stream.toByteArray());
			if (null != is) {
				Filedownload.save(is, "application/vnd.ms-excel", getExportFileName() + "_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".xls");
			} else {
				Messagebox.show("文件下载错误，找不到要下载的文件！", "文件下载出错", Messagebox.OK, Messagebox.ERROR);
			}
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public int getCellHeaderLen() {
		return cellHeaderLen;
	}

	public void setCellHeaderLen(int cellHeaderLen) {
		this.cellHeaderLen = cellHeaderLen;
	}

}
