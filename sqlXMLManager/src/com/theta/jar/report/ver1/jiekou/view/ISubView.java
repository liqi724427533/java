package com.theta.jar.report.ver1.jiekou.view;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Node;

import com.theta.jar.report.ver1.jiekou.IReportIn;

public interface ISubView {

	/**
	 * 加载配置
	 * @param tempNode
	 * @return
	 */
	boolean load(Node tempNode);

	/**
	 * 取得页面显示 所需 模型信息
	 * @param showModelMap
	 * @param report
	 */
	void getShowModelInfo(Map<String, Object> showModelMap, IReportIn report);

	/**
	 * 输出模型涉及到数据
	 * @param reportIn
	 * @param json
	 * @param firstRecordMap
	 * @param subViewIndex
	 * @return
	 */
	JSONObject exportData(IReportIn reportIn, JSONObject json,
			Map<String, Object> firstRecordMap, int subViewIndex);

	/**
	 * chek 模型是否正确配置
	 * @return
	 */
	boolean check();

	/**
	 * 输出模型数据。
	 * @param json
	 * @param showMap
	 * @param out
	 * @param writer
	 * @param report
	 * @param fileType
	 * @param fileName
	 */
	void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report,
			int fileType, String fileName);
	
	void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report,
			int fileType, String fileName,String groupHeaders);
	
	
	public String getRef();

/*	
	*//**
	 * 是否查询记录总数;
	 * @return
	 *//*
	public boolean bFindTotal();*/

}
