package com.theta.jar.jiekou;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import com.theta.jar.report.ver1.dim1.model.data.FieldInfo;
import com.theta.jar.report.ver1.jiekou.IHeader;
import com.theta.jar.report.ver1.jiekou.data.IData;


import net.sf.json.JSONArray;

public interface IFileExportUtil {

	public static final int ExcelType = 0;
	public static final int TxtType = 1;
	public static final int HtmlType = 2;
	public static final int PdfType = 3;
	public static final int XmlType = 4;
	public static final int TxtTypeNoIndex = 5;
	public static final int ImeiInsertSql = 6;
	public static final int ImsiProperties = 7;
	public static final int MODULELISTSql = 8;
	public static final int PageSize = 60000;
	
	public void exportExcel(OutputStream out, String sheetName,String[] header, String[] dataIndex, JSONArray array, boolean[] hiddens) ;
	
	public  void exportExcel(OutputStream out, String sheetName, IHeader [] header, JSONArray array);
	
	public  void exportExcel(OutputStream out, String sheetName, IHeader [] header, JSONArray array,String groupHeaders);
	
	public void exportTxt(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportXml(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportHtml(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportPdf(OutputStream out, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportExcel(FileOutputStream out, String sheetName,List<FieldInfo> fieldList, List<List<IData>> list);
	
	public void exportTxt(Writer writer, String sheetName,List<FieldInfo> fieldList, List<List<IData>> list) ;
	
	public void exportXml(Writer writer, String sheetName,List<FieldInfo> fieldList, List<List<IData>> list);
	
	public void exportHtml(Writer writer, String sheetName,List<FieldInfo> fieldList, List<List<IData>> list);
	
	public void exportTxt3(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportPdf(OutputStream out, String sheetName,List<FieldInfo> fieldList, List<List<IData>> list);
	
	public void exportImei(Writer writer, String fileName,String[] header, JSONArray array,boolean[] hiddens);
	
	public void exportImeiSql(Writer writer, String fileName,String[] header, JSONArray array,boolean[] hiddens);
	
	public void exportImeiProperties(Writer writer, String fileName,String[] header, JSONArray array,boolean[] hiddens);
	
	
}
