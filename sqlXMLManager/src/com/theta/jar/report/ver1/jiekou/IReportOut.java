package com.theta.jar.report.ver1.jiekou;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import com.theta.jar.report.ver1.dim1.model.data.FieldInfo;
import com.theta.jar.report.ver1.jiekou.data.IData;

public interface IReportOut {
	
 
	public static int  Type_List = 1;
	public static int  Type_Hash = 2;
	
	/**
	 * ȡ�ô洢���� ��ʽ ��
	 * Type_List  ����
	 * Type_Hash  Hash��
	 * @return
	 */
	public int getDataType();
	
	/**
	 * ����Դ���ֶ���Ϣ ;
	 * @return
	 */
	public List<FieldInfo> getFieldList();
	public void setFiledList(List<FieldInfo> fieldList);
	
	/**
	 * �ֶθ��� ��
	 * @param colCount
	 */
	public int getColumNumber();
	
	/**
	 * ��¼��
	 * @param recordNumber
	 */
	public int 	getRecordNumber();	
	
	/**
	 * ���ز�ѯ������¼��;
	 * @return
	 */
	public int getTotalNumber();
	
	/**
	 * �ܼ�¼���Ƿ���ȷ 
	 * @return
	 */
	public boolean bRightTotalNumber();
	/**
	 *  ����Դ ������Ϣ ���б���Ϣ����
	 * @return
	 */
	public List<List<IData>> getDataList();
	public void setDataList(List<List<IData>> list);
	
	/**
	 *  ����Դ ������Ϣ �� hash����ʽ���� 
	 * @return
	 */
	public Map<Object, List<IData>>  getDataMap();
	public void setDataMap(Map<Object, List<IData>> map);
	
	/**
	 *  ȡ�������Ƿ�ɹ��� 
	 * @return
	 */
	public boolean getSuccess();
	public void setSuccess(boolean b);
	
	/**
	 * ��ʾ��Ϣ ��
	 * @return 
	 */
	public String getTipInfo();
	public void setTipInfo(String message);

	/**
	 * ȡ�õ�ǰ�洢 ���ݵķ��� �ֶ��б� ; �����ֶ����ڽ�������ʱʹ�� 
	 * @return
	 */
	public List<String> getGroupNameList();
	public void			setGroupNameList(List<String> groupNameList);
	
	/**
	 * ����filedNameֵȡ�ø��ֶ��ڽ�����е�λ��.
	 * @param fieldName
	 * @return
	 */
	public Integer getIndexByFieldName(String fieldName);
	/**
	 * ȡ�� ���� �ֶ��ڼ�¼����������  �б� ��
	 * @return
	 */
	public List<Integer> getGroupIndexList();
 
	 
	/**
	 * �����ṩ�ֶ����б� ����  �ڼ�¼�ж�Ӧ�� λ�� �����б� ��
	 * @param fieldNames
	 * @return
	 */
	public List<Integer> getIndexListByName(List<String> nameList);
	
	
	public void output(PrintStream os);

	/**
	 * ȡ�ü�¼��ʼ�����
	 */
	public int getRecordStart();
}
