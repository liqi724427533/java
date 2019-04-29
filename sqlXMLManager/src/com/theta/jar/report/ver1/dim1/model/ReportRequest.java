package com.theta.jar.report.ver1.dim1.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.common.app.config.AppConfigManager;
import com.theta.jar.jiekou.IDBListManager;

public class ReportRequest implements IReportIn {
	 
	private static final Logger logger = Logger.getLogger(ReportRequest.class);
	static 	IDBListManager idbListManager = (IDBListManager) ObjectManager.getSingleObjectInstance(ObjectManager.DBListManager);	
	
	private Connection  connection=null;
	private String 		sql = null;
	private String userName = null;
	private String pageName = null;
	private String url = null;
	
	private boolean autoPage = true;
	/**
	 * 
	 */
	private boolean beSetSql = false;
	public  Map<String, String> map = null;
	private List<String>  groupNameList = null;
	
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	
	private boolean success = true;
	private String  tipInfo = null;

	private boolean isTimeOut = false;
	private boolean autoQueryTotal = true;
	
	private int timeOut =300;

	private int  dbIndex = 0;
	/**
	 * 表格名称;
	 */
	private String tableName = null;
	//1. have $ ; 2,have _0; 3: $,_0;
	private int    flag = 0;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 是否多个数据库
	 */
	private boolean multiDb;
	

	public String getTipInfo() {
		return tipInfo;
	}

	public void setTipInfo(String tipInfo) {
		this.tipInfo = tipInfo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAutoQueryTotal(boolean autoQueryTotal1){
		this.autoQueryTotal = autoQueryTotal1;
	}


	public int getGroupNamesCount() {
		
		if(this.groupNameList!=null){
			return this.groupNameList.size();
		}
		return 0;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
 
		beSetSql = true;
		this.sql = sql;
		
	}



	public void setGroupNameList(List<String> groupNameList) {
		this.groupNameList = groupNameList;
	}
	
	public List<String> getGroupNameList() {
		 
		return this.groupNameList;
	}

	public int getGroupNameCount() {

		if(this.groupNameList==null){
			return 0;
		}
		
		return this.groupNameList.size();
		
	}

	public void setRequest(HttpServletRequest request) {

		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		 
		this.response = response ;
	}

	public HttpServletRequest getRequest() {
		 
		return this.request;
	}

	public HttpServletResponse getResponse() {
	 
		return this.response;
	}

	public Map<String, String> getRequestMap() {
		 
		return this.map;
	}

	public boolean isSetSql() {
		 
		return beSetSql;
	}

	/**
	 * 是否需要程序分页,默认需要
	 */
	public boolean autoPage() {
		
		return autoPage;
	}

	public boolean isAutoPage() {
		return autoPage;
	}
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.closeConnection();
		this.connection = connection;
	}
 
	public void closeConnection(){
		
		if(this.connection==null){
			return ;
		}
		
		 try {
			 if(!this.connection.isClosed()){
					this.connection.close();
			 }
		} catch (SQLException e) {
			 logger.error("close connection failed:",e);
		}
		
		this.dbIndex = 0; 
		this.connection = null;
	}

	public void setAutoPage(boolean autoPage1) {
		 
		this.autoPage = autoPage1;
	}

	public boolean isTimeOut() {
		 
		return isTimeOut;
	}

	public int getTimeQueryOut() {
		 
		return timeOut;
	}
	
	public void setTimeOut(int timeOut){
		
		if(timeOut>0){
			this.isTimeOut = true;
			this.timeOut = timeOut;			
		}else{
			this.isTimeOut = false;
		}

	}

	/**
	 * 生成一份副本IReportIn副本
	 */
	public IReportIn copy() {
	 
		ReportRequest rr =new ReportRequest();
		rr.autoPage=this.autoPage;
		rr.connection = this.connection;
		rr.sql = this.sql;
		rr.autoPage = this.autoPage;
		rr.beSetSql = this.beSetSql;
		rr.map = this.map;
		rr.groupNameList = this.groupNameList;
		rr.request = this.request;
		rr.response = this.response;
		rr.success = this.success;
		rr.tipInfo = this.tipInfo;
		rr.isTimeOut = this.isTimeOut;
		rr.timeOut = this.timeOut;
		rr.dbIndex = this.dbIndex;
		rr.tableName = this.tableName;
		rr.flag = this.flag;
		rr.autoQueryTotal = this.autoQueryTotal;
		rr.userName = this.userName;
		 
		return rr;
	}

	public boolean autoQueryTotal() {

		return autoQueryTotal;
	}

	public void setConnection(Connection conn, int dbIndex) {
		 this.closeConnection();
		 this.connection = conn;
		 this.dbIndex = dbIndex;
	}

	public boolean needSetConnect(int dbIndex) {
		
		if(this.connection!=null&&this.dbIndex==dbIndex){
			 return false;
		}
		return true;
	}

	public List<Integer> getDbIndexList() {
		if(AppConfigManager.getIntValue("use_tail_numbe_config")==1
				&&this.tableName!=null&&this.tableName.toLowerCase().startsWith("session_")
				&&this.map!=null&&this.map.get("msisdn")!=null&&!this.map.get("msisdn").trim().isEmpty()){
			
			String msisdn = this.map.get("msisdn");
			String msisdn_end = msisdn.substring(msisdn.length()-1,msisdn.length());
			List<Integer> dbIndexList = idbListManager.getValueWithEnd(msisdn_end);
			if(dbIndexList!=null){
				return dbIndexList;
			}
			
		}
		//李琦原
	    return idbListManager.getDbIndexList(this.tableName, this.flag, this.map, this.multiDb);
	}

	@Override
	public void setTableName(String tableName1, boolean multiDb) {

		this.tableName = tableName1;
		this.flag = 0;
		this.multiDb = multiDb;
		if(tableName==null){
			return ;
		}
		if(tableName.indexOf("$")>=0){
			this.flag = 1;
		}
		
		if(tableName.indexOf("_0")>=0){
			this.flag += 2;
		}
		
		return ;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		
		if (!(obj instanceof ReportRequest)) {
			return false;
		}
		ReportRequest reportRequest = (ReportRequest) obj;
		Map<String, String> thisMap =removeOther(this.map);
		Map<String, String> objMap = removeOther(reportRequest.map);
		
		return this.sql.equals(reportRequest.sql)&&thisMap.equals(objMap);
		
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		Map<String, String> thisMap =removeOther(this.map);
		int result = 17;  //任意素数
		result = 31*result +thisMap.hashCode();
		result = 31*result +this.sql.hashCode();
		return result;
	}
	/**
	 * 先去除过滤条件中为空的和与查询不相关的，再进行hashcode的计算
	 * 修改后导出时也不需要再从数据库查询，而是直接从内存取数据
	 * @param map
	 * @return
	 */
	public Map<String, String> removeOther(Map<String, String> map){
		
		Map<String, String> thisMap =new HashMap<String, String>(map);
		
		if(thisMap.isEmpty()){
			return thisMap;
		}
		
		String[] ketArray = {"_dc","start","limit","secondLoad","columnMode","pageCommand","fileType","timeOut"};
		
		for(int i=0;i<ketArray.length;i++){
			thisMap.remove(ketArray[i]);
		}
		
		Set<String> keySet = thisMap.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			
			if(thisMap.get(key).isEmpty()){
				iterator.remove();
			}
			
		}
		
		return thisMap;
	}

}
