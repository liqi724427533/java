package com.theta.jar.report.ver1.jiekou;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IReportIn {
	

	/**
	 * ����Э����ҳ ;Ĭ����Ҫ.
	 * @return
	 */
	public boolean autoPage();
	
	/**
	 * �Ƿ��ѯ��¼����;
	 * @return
	 */
	//public boolean bFindTotal();

	/**
	 * set can auto queryTotal;
	 * @param autoQueryTotal1
	 */
	public void setAutoQueryTotal(boolean autoQueryTotal1);
	/**
	 * ���� �Ƿ� ��ѯ ����;
	 * @param bFindTotal
	 */
	//public void setFindTotal(boolean bFindTotal);

	
	/**
	 * �����Ƿ�֧���Զ���ҳ
	 * @param autoPage
	 */
	public void setAutoPage(boolean autoPage);
	/**
	 * ���� ���� �����б� ��
	 * @param groupNameList
	 */
	public Map<String, String> getRequestMap();
	
	/**
	 * ����groupNameListֵ���� ����Դ�и��ġ�
	 * @param groupNameList
	 */
	public void setGroupNameList(List<String> groupNameList);
	/**
	 * ȡ�� ���� �����б� ;
	 * @return
	 */
	public List<String> getGroupNameList();
	
	/**
	 * ȡ�� ���� ���� ;
	 * @return
	 */
	public int  getGroupNameCount();
	/**
	 * ���ز�ѯ SQL��� ��
	 * @return
	 */
	public String getSql();
	/**
	 * ����SQL��� 
	 * @return
	 */
	public void setSql(String sql);
	
	/**
	 * ��ȡ��ѯʱ����û���
	 * **/
	public String getUserName();
	/**
	 * ���ò�ѯʱ����û���
	 * @param string 
	 * **/
	public void setUserName(String string);
	
	public boolean isSetSql();
	/**
	 * HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request);
	/**
	 * ����  HttpServletResponse
	 */
	public void setResponse(HttpServletResponse response);
	
	/**
	 * ȡ��  HttpServletRequest
	 * @return 
	 */
	public HttpServletRequest getRequest();
	
	/**
	 * ȡ��HttpServletResponse 
	 */
	public HttpServletResponse getResponse();
	
	/**
	 * ȡ�����ݿ�����
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * �������ݿ����� 
	 * @param connection
	 */
	public void setConnection(Connection connection);
 
	/**
	 * �ر����� 
	 * @param conn
	 */
	public void closeConnection();
	
	/**
	 * ȡ����ʾ��Ϣ
	 * @return
	 */
	public String getTipInfo();

	/**
	 * ���� ��ʾ��Ϣ
	 * @param tipInfo
	 */
	public void setTipInfo(String tipInfo);

	/**
	 * �鿴���� ��ѯ�Ƿ�ɹ�
	 * @return
	 */
	public boolean isSuccess();

	/**
	 * �����Ƿ�ɹ���־��
	 * @param success
	 */
	public void setSuccess(boolean success);
	
	/**
	 * �Ƿ������˳�ʱʱ��
	 * @return
	 */
	public boolean isTimeOut() ;
	
 
	/**
	 * ȡ�����ݿ��ѯ��ʱʱ��;
	 * @return
	 */
	public int getTimeQueryOut();
	
	/**
	 * ����һ��IReportInʵ��
	 * @return
	 */
	public IReportIn copy();
 
	
	 
	/**
	 * �Ƿ��Զ� ��ѯ��������Ĭ�Ϸ��� true��
	 * @return
	 */
	public boolean autoQueryTotal();	
	
 
	/**
	 * �Ƿ���Ҫע��connection ;
	 * @param dbIndex
	 * @return
	 */
	public boolean needSetConnect(int dbIndex);
	/**
	 * ���� connect,��connect ���� Index;
	 * @param conn
	 * @param dbIndex
	 */
	public void setConnection(Connection conn, int dbIndex);

	/**
	 * ȡ�����������б�;
	 * @return
	 */
	public List<Integer> getDbIndexList();
	

	/**
	 * ���� tableName;
	 * @param tableName
	 * @return
	 */
	public void setTableName(String tableName, boolean multiDb);
	
	public String getPageName();

	public void setPageName(String pageName);
	

	public String getUrl();

	public void setUrl(String url);
}
