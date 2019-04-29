package com.theta.jar.report.ver1.dim1.model.ds;

import java.util.List;

import org.dom4j.Node;

import com.theta.jar.report.ver1.dim1.model.ds.info.RefDS;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

public class MultipleDS extends BaseDS{


	private RefDS	rDs1 = null;
	private RefDS 	rDs2 = null;
	
	private int   mergeType=3;

	public IReportOut getData(IReportIn report) {

		IDataSource ds1 = this.rDs1.getDataSource();
		IDataSource ds2 = this.rDs2.getDataSource();
		
		if(ds1==null){
			logger.error("ds1 is null: "+rDs1.toString());
		}
		if(ds2==null){
			logger.error("ds2 is null: " + rDs2.toString());
		}
	
	 
		IReportOut data1 = ds1.getData(report);
		
		report.setGroupNameList(this.groupNameList);
		IReportOut data2 = ds2.getData(report);
		
		IReportOut data =  null;
		
		if(mergeType==1){
			
			data = iMerge.sameMerge(data1, data2, report);
		
		}else if(mergeType==2){
		 
				data = iMerge.groupMerge(data1, data2, data2.getGroupNameList(), report);	
			 
		}else if(mergeType==3){
				data = iMerge.fieldMerge(data1, data2, data1.getGroupNameList(), report);	
			 
		}
  
		return data;
	}

	public boolean load(Node node) {
		
		super.load(node);
		
		List<Node> nodes = node.selectNodes("data");
		
		if(nodes==null||nodes.size()<2){
			logger.error("multiple datasource must have two /data . "+node.asXML());
			return false;
		}
		
		RefDS rd1 = new RefDS();
		rd1.load(nodes.get(0));
		
		RefDS rd2 = new RefDS();
		rd2.load(nodes.get(1));
		
		this.rDs1 = rd1;
		this.rDs2 = rd2;
		
		return true;
	}

	public int getDSType() {
	
		return IDataSource.TYPE_Multiple;
	}

	@Override
	public String toString() {
		return "MultipleDS [rDs1=" + rDs1 + ", rDs2=" + rDs2 + ", mergeType="
				+ mergeType + "]";
	}

	
}
