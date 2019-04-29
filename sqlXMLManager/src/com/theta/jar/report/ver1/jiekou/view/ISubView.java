package com.theta.jar.report.ver1.jiekou.view;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Node;

import com.theta.jar.report.ver1.jiekou.IReportIn;

public interface ISubView {

	/**
	 * ��������
	 * @param tempNode
	 * @return
	 */
	boolean load(Node tempNode);

	/**
	 * ȡ��ҳ����ʾ ���� ģ����Ϣ
	 * @param showModelMap
	 * @param report
	 */
	void getShowModelInfo(Map<String, Object> showModelMap, IReportIn report);

	/**
	 * ���ģ���漰������
	 * @param reportIn
	 * @param json
	 * @param firstRecordMap
	 * @param subViewIndex
	 * @return
	 */
	JSONObject exportData(IReportIn reportIn, JSONObject json,
			Map<String, Object> firstRecordMap, int subViewIndex);

	/**
	 * chek ģ���Ƿ���ȷ����
	 * @return
	 */
	boolean check();

	/**
	 * ���ģ�����ݡ�
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
	 * �Ƿ��ѯ��¼����;
	 * @return
	 *//*
	public boolean bFindTotal();*/

}
