package com.theta.jar.report.ver1.jiekou.view;

 

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
 
import org.dom4j.Node;

import net.sf.json.JSONObject;
 
import com.theta.jar.report.ver1.jiekou.IReportIn;
 

public interface IViewModel {

	/**
	 * ����ģ�� ID . viewId ;
	 * @return
	 */
	public String getId();
 
 
	

	/**
	 * ȡ�� ������ʾ����� �ֶ� ��
	 * ���� header, field ;...
	 * @param report
	 * @return
	 */
	public Map<String, Object>  getModelShowInfo(IReportIn report);
	/**
	 * ȡ�� ģ��������Ϣ �� title 
	 * @return
	 */
	public String getDesc();

	/**
	 * ȡ��ģ������
	 * @param reportIn
	 * @return
	 */
	public JSONObject getReportData(IReportIn reportIn);

	/**
	 * ��� ģ���õ���Դ�Ƿ��С�
	 * @return
	 */
	public boolean check();



	/**
	 * �����ļ� 1,2,4,8
	 * excel,pdf,txt,html
	 * out �� writerΪ��
	 * @param out
	 * @param writer
	 * @param report 
	 * @param fileType
	 * @param fileName
	 */
	public void exportFile(OutputStream out, Writer writer, IReportIn report, int fileType, String fileName);
	/**
	 * ����ҳ����ʾ���н��пɿ��Ƶĵ���
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
