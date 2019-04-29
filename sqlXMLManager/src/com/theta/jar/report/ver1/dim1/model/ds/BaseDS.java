package com.theta.jar.report.ver1.dim1.model.ds;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.report.myutil.BaseUtil2;
import com.theta.jar.report.myutil.SqlUtil;
import com.theta.jar.report.ver1.dim1.model.ds.info.SqlInfo;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;

import com.theta.jar.report.ver1.jiekou.control.IGroup;
import com.theta.jar.report.ver1.jiekou.control.IMerge;
import com.theta.jar.report.ver1.jiekou.control.IShowOut;
import com.theta.jar.report.ver1.jiekou.control.OnQuery;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

public    class BaseDS implements IDataSource, ILoadXML{

	public static final Logger logger = Logger.getLogger(BaseDS.class);

	
	public String tableName;
	public String dsId;
	public String desc; 
	public SqlInfo sqlInfo = null;
 
	public IMerge		iMerge;
	public IGroup		iGroup;
	public OnQuery		onQuery;

	public IShowOut		showOut;
	public  String 		groupNames=null;
	public  List<String> 	groupNameList = new ArrayList();

	public  IReportOut rsData = null;


	protected boolean multidb=false;

	public IMerge getiMerge() {
		return iMerge;
	}

	public void setiMerge(IMerge iMerge) {
		this.iMerge = iMerge;
	}

	public IGroup getiGroup() {
		return iGroup;
	}

	public void setiGroup(IGroup iGroup) {
		this.iGroup = iGroup;
	}

	



	public OnQuery getOnQuery() {
		return onQuery;
	}

	public void setOnQuery(OnQuery onQuery) {
		this.onQuery = onQuery;
	}

	public boolean load(Node node) {
	
		if(node==null){
			logger.error("load report ds failed . no element . ");
			return false;
		}
	    Element el = (Element)node;
	    
	    this.dsId = el.attributeValue("id");
	    this.dsId = this.dsId.trim().toLowerCase();
	   
	    if(this.dsId==null||this.dsId.trim().length()<1){
	    	logger.error("dataSource id is null . "+node.asXML());
	    }
	    
	    this.tableName = el.attributeValue("tableName");
	    if(this.tableName!=null&&this.tableName.indexOf("|")>0){
	    	this.tableName = this.tableName.toLowerCase().split("|")[0];
	    }
		this.desc = el.attributeValue("desc");
		
		if(this.getDSType()!=TYPE_Multiple){
			
			this.sqlInfo = new SqlInfo();
			this.sqlInfo.dsId = dsId;
			sqlInfo.load(node.selectSingleNode("sql"));
			this.multidb=this.sqlInfo.multidb;
			
			if(this.tableName==null){
				if(this.sqlInfo.getSql()!=null){
 					
					String tempTableName=SqlUtil.getTableNameFromSql(this.sqlInfo.getSql());
					if(tempTableName!=null&&tempTableName.length()>0){
						this.tableName = tempTableName;
					}
				}
			}
		}
			//load group key;
		
			groupNameList.clear();
			String keys = BaseUtil2.getXpathValue(node, "key");
			this.groupNames = keys;
			if(keys!=null&&keys.trim().length()>2){
		
			 
				String[] ks = keys.trim().split(",");
				if(ks!=null){
					for(int k=0; k< ks.length; k++){
						if(ks[k]!=null&&ks[k].trim().length()>0){
							this.groupNameList.add(ks[k].trim().toLowerCase());			
						}
					
					}
					
				}
			}
	 
		return true;
 
	}

	public int getDSType() {
		
		return 0;
	}


	public IReportOut getData(IReportIn report) {
		
		return null;
	}

	public IShowOut getShowOut() {
		return showOut;
	}
	public void setShowOut(IShowOut showOut) {
		this.showOut = showOut;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public IReportOut reLoad(IReportIn report) {
		
		this.rsData = null;
		
		return getData(report);
	}
	
	
}
