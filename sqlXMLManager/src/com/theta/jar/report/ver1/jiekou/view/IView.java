package com.theta.jar.report.ver1.jiekou.view;


import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;


public interface IView {

 
	/**
	 *  ȡ��  ҳ��ģ����ʾ ��Ϣ��  key, JSONOBJECT��ʽ����,���浽 showModelMap�� ��
	 *  
	 *  
	 * @param showList
	 * @param obj 
	 * @param report
	 * @param showModelMap
	 * @param viewIndex
	 */
	public void getShowModelInfo(List<ShowInfo> showList, Object obj, IReportIn report,
			Map<String, Object> showModelMap, String viewIndex);

 
	/**
	 * 	 * ������� �� 
	 * �����޸� showList,  reportData ֵ
	 * @param showList
	 * @param reportData
	 * @param reportIn
	 * @param map:�������
	 * @param viewIndex 
	 * @return
	 */
	public JSONObject exportData(List<ShowInfo> showList, IReportOut reportData, IReportIn reportIn, Map<String, Object> map, String viewIndex);


	/**
	 * �� ģ�� ������� . 
	 * @param json
	 * @param showMap 
	 * @param out
	 * @param writer
	 * @param report
	 * @param fileType
	 * @param fileName
	 * @param index 
	 */
	public void exportFile(JSONObject json, Map<String, Object> showMap, OutputStream out,
			Writer writer, IReportIn report, int fileType, String fileName, String index);
	
	public void exportFile(JSONObject json, Map<String, Object> showMap, OutputStream out,
			Writer writer, IReportIn report, int fileType, String fileName, String index,String groupHeaders);
	


}
