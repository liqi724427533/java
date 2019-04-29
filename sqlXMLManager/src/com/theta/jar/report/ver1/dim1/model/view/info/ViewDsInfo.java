package com.theta.jar.report.ver1.dim1.model.view.info;

import org.apache.log4j.Logger;
import org.dom4j.Node;

import com.theta.jar.jiekou.IReportModelManager;
import com.theta.jar.report.myutil.BaseUtil2;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.ReportModelManager;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

public class ViewDsInfo  implements ILoadXML {

    private static final Logger logger = Logger.getLogger(ViewDsInfo.class);
	 
    
	private String refDsId;
	private String groupName;
	
	private IDataSource	 iSource = null;
	/**
	 * 		<ds>
				<ref>lacciInfo</ref>
				<group>groupId</group>
			</ds>
	 */
	public boolean load(Node node) {
		
		
		if(node == null){
			logger.error("load ds config failed . must config /model/ds node ! ") ;		
			return false;
		}

		this.refDsId = BaseUtil2.getXpathValue(node, "@ref");
		 if(refDsId==null){
			
			logger.error("no config reference data source ! /model/ds/ref is null ."+node.asXML());
			return false;
		}
		iSource = null;
		this.refDsId = this.refDsId.trim().toLowerCase();
		
		this.groupName = BaseUtil2.getXpathValue(node, "group");
		if(groupName==null){
			logger.error("no config reference data source ! model/ds/group .");
			return false;
		}
 
		IReportModelManager reportModelManager = (IReportModelManager) ObjectManager.getSingleObjectInstance(ObjectManager.ReportModelManager);
		this.iSource = reportModelManager.getDataSourceById(this.refDsId);
		if(this.iSource==null){
			logger.error("reference data source is null ");
			return false ;
		}
		return true;
	}


	/**
	 * 取得引用到dataSource.
	 * @return
	 */
	public IDataSource getDataSource(){
		
		return this.iSource;
	}
	
	public String getRefDsId() {
		return refDsId;
	}

	public void setRefDsId(String refDsId) {
		this.refDsId = refDsId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "ViewDsInfo [refDsId=" + refDsId + ", groupName=" + groupName
				+ "]";
	}

	
	
}
