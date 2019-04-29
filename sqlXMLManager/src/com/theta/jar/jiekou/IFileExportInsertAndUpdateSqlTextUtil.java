package com.theta.jar.jiekou;

import java.io.Writer;

import net.sf.json.JSONArray;

public interface IFileExportInsertAndUpdateSqlTextUtil {

	public static final int InsertTxtType = 10;
	public static final int UpdateTxtType = 11;
	public static final int PageSize = 60000;
	
	public void exportSiteClassInsertSqlTxt(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens);
	
	public void exportSiteClassUpdateSqlTxt(Writer writer, String fileName,String[] header, String[] dataIndex, JSONArray array,boolean[] hiddens) ;
	
	
}
