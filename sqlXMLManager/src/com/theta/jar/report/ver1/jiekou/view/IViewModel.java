package com.theta.jar.report.ver1.jiekou.view;

 

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
 
import org.dom4j.Node;

import net.sf.json.JSONObject;
 
import com.theta.jar.report.ver1.jiekou.IReportIn;
 

public interface IViewModel {

	/**
	 * 报表模块 ID . viewId ;
	 * @return
	 */
	public String getId();
 
 
	

	/**
	 * 取得 界面显示所需的 字段 ；
	 * 比如 header, field ;...
	 * @param report
	 * @return
	 */
	public Map<String, Object>  getModelShowInfo(IReportIn report);
	/**
	 * 取得 模型描述信息 。 title 
	 * @return
	 */
	public String getDesc();

	/**
	 * 取得模型数据
	 * @param reportIn
	 * @return
	 */
	public JSONObject getReportData(IReportIn reportIn);

	/**
	 * 检查 模型用到资源是否都有。
	 * @return
	 */
	public boolean check();



	/**
	 * 导出文件 1,2,4,8
	 * excel,pdf,txt,html
	 * out 或 writer为空
	 * @param out
	 * @param writer
	 * @param report 
	 * @param fileType
	 * @param fileName
	 */
	public void exportFile(OutputStream out, Writer writer, IReportIn report, int fileType, String fileName);
	/**
	 * 根据页面显示的列进行可控制的导出
	 * @param out
	 * @param writer
	 * @param report
	 * @param fileType
	 * @param fileName
	 * @param column
	 */
	public void exportFile(OutputStream out, Writer writer, IReportIn report, int fileType, String fileName,String column);
	public void exportFile(OutputStream out, Writer writer, IReportIn report, int fileType, String fileName,String column,String groupHeaders);



	public boolean load(Node node);
	
	public JSONObject getReportTotal(IReportIn reportIn);
}
