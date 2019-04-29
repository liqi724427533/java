package com.theta.jar.report.ver1.dim1.model.ds.info;

 
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.ds.SimpleDS;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.data.ISqlConverter;
 

public class SqlInfo implements ILoadXML{
	 

	private static final Logger logger = Logger.getLogger(SimpleDS.class);
	public String dsId = null;
	private String sql=null;
	private String id = null;
	public boolean multidb=false;
	//public String  dbList=null;
	public boolean autoQueryTotal1 = true;
	
	/**
	 * 此sql是否是模板 
	 */
	private boolean bTemplate=false;
	
	/**
	 * sql生成器
	 */
	private String   	sqlClassName=null;
	
	private ISqlConverter  sqlConverter = null;
	
	private boolean autoPage = true;
	
	//0. select  1, insert; 2.update
	private int   queryType=0;
	
	public boolean load(Node node){

		if(node==null){
			logger.error("sql is null. ds  Id:"+dsId);
			return false;
		}
		Element el = (Element)node;
	
		setSql(el.getText());
		this.id = el.attributeValue("id");
		
		String autoPageStr = el.attributeValue("autoPage");
		if(autoPageStr!=null&&(!"1".equals(autoPageStr))){
			this.autoPage = false;
		}
		
		String autoQueryTotalStr = el.attributeValue("autoQueryTotal");
		if(autoQueryTotalStr!=null&&("false".equalsIgnoreCase(autoQueryTotalStr)||"0".equals(autoQueryTotalStr))){
			this.autoQueryTotal1 = false;	
		}
/*		this.dbList = el.attributeValue("dbList");
		if(this.dbList==null)
			this.dbList="";*/
		
		String sqlType = el.attributeValue("type");
		 
		if(sqlType!=null&&("insert".equalsIgnoreCase(sqlType)||"update".equals(sqlType))){
			this.queryType = 1;
		}
		
		String multidb = el.attributeValue("multidb");
		if(multidb!=null&&"true".equalsIgnoreCase(multidb)){
			this.multidb = true;
		}else{
			this.multidb=false;
		}
		
		String sqlConvert = el.attributeValue("class");
		if(sqlConvert!=null&&sqlConvert.trim().length()>2){
			this.sqlClassName = sqlConvert.trim();
			this.sqlConverter = (ISqlConverter) ObjectManager.getSingleObjectInstance(sqlClassName);
		}
		if(logger.isTraceEnabled()){
			logger.debug("sqlInfo:"+this.sql);			
		}
		return true;
	}
	
 
	public String getSql( ) {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
		
		//check if has $
		if(this.sql!=null){
			if(this.sql.indexOf("$")>=0){
				  this.bTemplate = true;
			}
		}
		
		//检查是否有{}
		//自定义sql,通过配置表生成sql语句
		//使用一对{}来标识使用的是自定义的sql语句
		//{}内包裹的是session表的表名称
		//如：{session_service} 
		if(this.sql != null){
			int start = this.sql.indexOf("{");
			int end = this.sql.indexOf("}");
			if(start >= 0 && end >= 0){
				
				String tableName = this.sql.substring(start + 1, end);
				String s = CustomInfo.getSql(tableName.toUpperCase());
				
				if(s == null){
					logger.error("config option : {" + tableName + "}, query content is null or table does not exists !!");
					this.sql = sql;
					return ;
				}
				
				this.sql = this.sql.replace("{"+tableName+"}", s);
				
			}
		}
		
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	


	public boolean isAutoPage() {
		return autoPage;
	}


	public void setAutoPage(boolean autoPage) {
		this.autoPage = autoPage;
	}

	
	public int getQueryType() {
		return queryType;
	}


	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}


	public String getRunSql(Map<String, String> requestMap) {
		
		if(!bTemplate){
			return this.sql;
		}
	 
		
		String sql2 = this.sql;
		
		Writer sw = new StringWriter();
		 
		VelocityContext context = new VelocityContext();
		VelocityEngine ve = new VelocityEngine();
		
		if(requestMap!=null){

			  Set<Entry<String, String>> set = requestMap.entrySet();
			  Iterator<Entry<String, String>> it = set.iterator();
			  
			  while(it.hasNext()){
				  Entry<String, String> entry = it.next();
				  context.put(entry.getKey(), entry.getValue());	  
			  }	
		}
		
	 
		 try {
			ve.evaluate(context, sw, "", sql2);
		} catch (Exception e) {
			 
			logger.error("parse template error !this.sql:"+this.sql, e);
			return this.sql;
		} 
		 
		 
		sql2 = sw.toString().trim();
		if(logger.isDebugEnabled()){
			logger.debug("query sql:"+sql2);		
		}
	
		
		return sw.toString();
	}


	@Override
	public String toString() {
		return "SqlInfo [dsId=" + dsId + ", sql=" + sql + ", id=" + id
				+ ", multidb=" + multidb + ", autoQueryTotal1="
				+ autoQueryTotal1 + ", bTemplate=" + bTemplate
				+ ", sqlClassName=" + sqlClassName + ", sqlConverter="
				+ sqlConverter + ", autoPage=" + autoPage + ", queryType="
				+ queryType + "]";
	}

}
