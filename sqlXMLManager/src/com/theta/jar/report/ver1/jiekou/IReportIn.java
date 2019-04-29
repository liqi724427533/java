package com.theta.jar.report.ver1.jiekou;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IReportIn {
	

	/**
	 * 程序协助分页 ;默认需要.
	 * @return
	 */
	public boolean autoPage();
	
	/**
	 * 是否查询记录总数;
	 * @return
	 */
	//public boolean bFindTotal();

	/**
	 * set can auto queryTotal;
	 * @param autoQueryTotal1
	 */
	public void setAutoQueryTotal(boolean autoQueryTotal1);
	/**
	 * 设置 是否 查询 总数;
	 * @param bFindTotal
	 */
	//public void setFindTotal(boolean bFindTotal);

	
	/**
	 * 设置是否支持自动分页
	 * @param autoPage
	 */
	public void setAutoPage(boolean autoPage);
	/**
	 * 设置 请求 参数列表 。
	 * @param groupNameList
	 */
	public Map<String, String> getRequestMap();
	
	/**
	 * 设置groupNameList值，在 数据源中更改。
	 * @param groupNameList
	 */
	public void setGroupNameList(List<String> groupNameList);
	/**
	 * 取得 分组 名称列表 ;
	 * @return
	 */
	public List<String> getGroupNameList();
	
	/**
	 * 取得 名字 个数 ;
	 * @return
	 */
	public int  getGroupNameCount();
	/**
	 * 返回查询 SQL语句 。
	 * @return
	 */
	public String getSql();
	/**
	 * 设置SQL语句 
	 * @return
	 */
	public void setSql(String sql);
	
	/**
	 * 获取查询时候的用户名
	 * **/
	public String getUserName();
	/**
	 * 设置查询时候的用户名
	 * @param string 
	 * **/
	public void setUserName(String string);
	
	public boolean isSetSql();
	/**
	 * HttpServletRequest
	 */
	public void setRequest(HttpServletRequest request);
	/**
	 * 设置  HttpServletResponse
	 */
	public void setResponse(HttpServletResponse response);
	
	/**
	 * 取得  HttpServletRequest
	 * @return 
	 */
	public HttpServletRequest getRequest();
	
	/**
	 * 取得HttpServletResponse 
	 */
	public HttpServletResponse getResponse();
	
	/**
	 * 取得数据库连接
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * 设置数据库连接 
	 * @param connection
	 */
	public void setConnection(Connection connection);
 
	/**
	 * 关闭连接 
	 * @param conn
	 */
	public void closeConnection();
	
	/**
	 * 取得提示信息
	 * @return
	 */
	public String getTipInfo();

	/**
	 * 设置 提示信息
	 * @param tipInfo
	 */
	public void setTipInfo(String tipInfo);

	/**
	 * 查看本次 查询是否成功
	 * @return
	 */
	public boolean isSuccess();

	/**
	 * 设置是否成功标志。
	 * @param success
	 */
	public void setSuccess(boolean success);
	
	/**
	 * 是否设置了超时时间
	 * @return
	 */
	public boolean isTimeOut() ;
	
 
	/**
	 * 取得数据库查询超时时间;
	 * @return
	 */
	public int getTimeQueryOut();
	
	/**
	 * 复制一份IReportIn实例
	 * @return
	 */
	public IReportIn copy();
 
	
	 
	/**
	 * 是否自动 查询总条数。默认返回 true；
	 * @return
	 */
	public boolean autoQueryTotal();	
	
 
	/**
	 * 是否需要注入connection ;
	 * @param dbIndex
	 * @return
	 */
	public boolean needSetConnect(int dbIndex);
	/**
	 * 设置 connect,及connect 所属 Index;
	 * @param conn
	 * @param dbIndex
	 */
	public void setConnection(Connection conn, int dbIndex);

	/**
	 * 取得数据所引列表;
	 * @return
	 */
	public List<Integer> getDbIndexList();
	

	/**
	 * 设置 tableName;
	 * @param tableName
	 * @return
	 */
	public void setTableName(String tableName, boolean multiDb);
	
	public String getPageName();

	public void setPageName(String pageName);
	

	public String getUrl();

	public void setUrl(String url);
}
