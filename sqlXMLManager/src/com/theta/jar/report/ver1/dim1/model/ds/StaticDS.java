package com.theta.jar.report.ver1.dim1.model.ds;



import org.apache.log4j.Logger;
import org.dom4j.Node;
 

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

/**
 * 存储静态表的数据 。
 * @author Administrator
 *
 */
public class StaticDS extends BaseDS{

	private static final Logger logger = Logger.getLogger(StaticDS.class);

	
	public boolean load(Node node) {
		/**
		 * 调用父类加载公用  指标。
		 */
		if(!super.load(node)){
			return false;
		}
		return true;
	}

	public IReportOut getData(IReportIn report) {
		
		if(this.rsData==null){
		    if(logger.isInfoEnabled()){
		    	logger.info("静态数据源初始化, 一般情况只执行一次!");
		    }
			reLoadData(report);
		}
		
		return rsData;
	}
	
	/**
	 * 重新加载数据
	 * @param report
	 */
	private synchronized IReportOut reLoadData(IReportIn report) {
		
		if(this.onQuery==null){
			logger.error("no query object .");
			return null;
		}

		int queryType = 0;
		boolean multiDb = false;
		 if(this.sqlInfo != null){
			 //report.setAutoPage(this.sqlInfo.isAutoPage());
			 this.multidb = this.sqlInfo.multidb;
			 report.setAutoPage(false);
			 report.setSql(this.sqlInfo.getRunSql(report.getRequestMap()));		
			 queryType = this.sqlInfo.getQueryType();
		}
		 
		report.setTableName(tableName, multiDb);
		report.setGroupNameList(this.groupNameList);
		
		//Map<Object, List<IData>> map = new HashMap<Object, List<IData>>();

		IReportOut rd = null;
		if(queryType>0){
			rd = this.onQuery.update(report);
		}else{
			if(this.multidb){
				rd = this.onQuery.multiThreadQuery(report);		
			}else{
				rd = this.onQuery.query(report);
				
			}
		}
		if(rd==null){		
			logger.warn("find no record !"+report.toString());
			return null;
		}
		
		if(!rd.getSuccess()){
			
			logger.error("load data failed !tipInfo:"+rd.getTipInfo());
			return null;
		}
	/*	
		*//**
		 * 根据 分组 用户名 返回  这些 分组用户名在记录中的位置 。
		 *//*
		List<Integer> gList = rd.getIndexListByName(report.getGroupNameList());
		if(gList==null){
			gList = rd.getGroupIndexList();
		}
		String tkey=null;
		List dataList = rd.getDataList();
		boolean keyLargeOne = gList.size()>1?true:false;
		
		for(int i=0; i<dataList.size(); i++){
			
			List li = (List) dataList.get(i);
			
			if(keyLargeOne){
				tkey ="";
				for(int j = 0; j< gList.size(); j++){
					tkey = tkey+li.get(gList.get(j));	
				}
				
				map.put(tkey, li);	
			}else{
				map.put(li.get(gList.get(0)), li);
			}
		}
		
		rd.setDataMap(map);*/
		this.rsData = rd;
		
		return rd;

	}

	public int getDSType() {
		
		return IDataSource.TYPE_STATIC;
	}

	@Override
	public String toString() {
		return "StaticDS [tableName=" + tableName + ", dsId=" + dsId
				+ ", desc=" + desc + ", sqlInfo=" + sqlInfo + ", iMerge="
				+ iMerge + ", iGroup=" + iGroup + ", onQuery=" + onQuery
				+ ", showOut=" + showOut + ", groupNames=" + groupNames
				+ ", groupNameList=" + groupNameList + ", rsData=" + rsData
				+ "]";
	}

	
	

}
