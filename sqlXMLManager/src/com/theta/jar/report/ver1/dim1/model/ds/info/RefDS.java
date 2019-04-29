package com.theta.jar.report.ver1.dim1.model.ds.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.jiekou.IReportModelManager;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

/**
 * 
 * @author Administrator
 *
 */
public class RefDS implements ILoadXML{
	
	public static final Logger logger = Logger.getLogger(RefDS.class);

	/**
	 * 存储 引用到的数据源 Id ;
	 */
	private String dsId=null;
	/**
	 * 存储 分组名称 。
	 */
	private String groupNames=null;

	private List<String> groupNameList=null;
	/**
	 * 引用到的数据源
	 */
	private IDataSource dataSource=null;

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String groupNames) {
		this.groupNameList = null;
		this.groupNames = groupNames;
	}

	public IDataSource getDataSource() {
		
		
		if(dataSource==null){
			IReportModelManager reportModelManager = (IReportModelManager) ObjectManager.getSingleObjectInstance(ObjectManager.ReportModelManager);
			dataSource = reportModelManager.getDSById(dsId);
		}
		return dataSource;
	}
	
	public void setDataSource(IDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean load(Node node) {
		
		if(node==null){
			logger.error("node is null !");
			return false;
		}
		Element el = (Element) node;
		
		String refId = el.attributeValue("ref");
		if(refId==null||refId.trim().length()<1){
			logger.error("multiple datasource must config <data ref=xxx >  ref attribute ! node:"+node.asXML());
			return false;
		}
		this.dsId = refId.trim().toLowerCase();
			
		return true;
	}

	public List<String> getGroupNameList() {
		
		if(this.groupNameList!=null){
			
			return this.groupNameList;
		}
		
		if(this.groupNames==null){
			this.groupNameList = null;
			return null;
		}
		
		String[] names = this.groupNames.split(",");
		if(names!=null&&names.length>0){
			
			this.groupNameList = new ArrayList();
			for(int i=0; i<names.length; i++){
				
				String temp = names[i].toLowerCase().trim();
				if(temp.length()>0){
					this.groupNameList.add(temp);
				}
			}
			
		}
		return groupNameList;
	}

	@Override
	public String toString() {
		return "RefDS [dsId=" + dsId + ", groupNames=" + groupNames
				+ ", groupNameList=" + groupNameList + ", dataSource="
				+ dataSource + "]";
	}

	
}
