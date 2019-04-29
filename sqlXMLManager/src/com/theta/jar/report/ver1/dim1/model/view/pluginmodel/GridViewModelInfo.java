package com.theta.jar.report.ver1.dim1.model.view.pluginmodel;

import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.jiekou.IReportModelManager;
import com.theta.jar.report.ver1.dim1.model.ReportModelManager;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.ds.info.CustomInfo;
import com.theta.jar.report.ver1.dim1.model.view.ViewDataModel;
import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;
import com.theta.jar.report.ver1.jiekou.view.ISubView;
import com.theta.jar.report.ver1.jiekou.view.IView;

public class GridViewModelInfo implements ILoadXML, ISubView{

	private static final Logger logger = Logger.getLogger(ViewDataModel.class);

	/**
	 * �Ƿ��ѯ����
	 */
	//private boolean bFindTotal = false;

	private String		    outClass = null;

    private List<ShowInfo>  showList = null;
    private List<ShowInfo>  outNamesList = null;
     
	private String 			outAttr=null;
	private String			ref = null;
	
	
	

	/**
	 * Ĭ��Ϊ1,2,3.... 
	 * ������� ��  ��׺������ jsonobject.(view)(index)
	 */
	public String			index="1";
	
	/**
	 * �Ƿ���� ģ��ֵ��
	 */
	private boolean			outModel = true;
	
	/**
	 * ��ʾ����ӿ�.
	 */
	public IView             gridView;
	
	public IDataSource		 iSource = null;
	public boolean load(Node node) {
		 
		if(node==null){
			logger.error("node is null");
			return false;
		}
		this.outModel = true;
		Element el = (Element)node;
	 
	/*	String findTotal = el.attributeValue("findTotal");
		
		if(findTotal!=null&&(findTotal.equalsIgnoreCase("true")||findTotal.equalsIgnoreCase("1"))){
			
			this.bFindTotal = true;
		}*/
		
		this.outAttr = el.attributeValue("outAttr");
		if(this.outAttr==null){
			this.outAttr="records";
//			logger.warn("outAttr is null, use default value records .");
		}
		
		this.ref = el.attributeValue("ref");
		if(this.ref==null||this.ref.trim().length()<1){
			  logger.error("view ref is null, no data source config .");
			  return false;
		}else{
			this.ref = this.ref.trim().toLowerCase();
		}
	
		IReportModelManager reportModelManager = (IReportModelManager) ObjectManager.getSingleObjectInstance(ObjectManager.ReportModelManager);
		this.iSource = reportModelManager.getDataSourceById(this.ref);
		if(this.iSource==null){
			logger.error("the view/@ref  no datasource, please config data source id = ref:"+this.ref);
			return false;
		}
		String indexStr = el.attributeValue("index");
		if(indexStr!=null&&indexStr.trim().length()>0){
		 
			this.index =indexStr.trim();
		}
		
		String outModel = el.attributeValue("out");
		if(outModel!=null&&outModel.trim().length()>1){
			this.outModel = false;
		}
		   
		 this.outClass = el.attributeValue("outclass");
		 if(this.outClass==null||this.outClass.trim().length()<2){
		    	this.outClass="com.theta.jar.report.ver1.dim1.model.view.plugin.ExtGridInfo";
		    	 this.gridView = (IView) ObjectManager.getSingleObjectInstance(this.outClass);
		  	   
		   }else{
		    	
		    	this.gridView = (IView) ObjectManager.getSingleObjectInstance(this.outClass);
		        if(this.gridView==null){
			    	logger.error("use default view class: ExtGridInfo . viewClass load error ! viewclass:"+this.outClass);
			    	this.outClass="com.theta.jar.report.ver1.dim1.model.view.plugin.ExtGridInfo";
			    	this.gridView = (IView) ObjectManager.getSingleObjectInstance(this.outClass);
			    	    	
		        }
		      
		    }
		  
		 if(!parseShowColsInfo(node)){
				
				return false;
		 }
		    
		return true;
	}
	

	/**
	 * ���� /model/show���� 
	 * @param node
	 * @return
	 */
	private boolean parseShowColsInfo(Node viewNode) {
		 
		List<Node>  list = viewNode.selectNodes("show/col");
		
		//���show�ڵ�����reportId,��reportId��Ϊ�����滻viewNode�ڵ��µ�����
		Node showNode = viewNode.selectSingleNode("show/@reportId");
		if(showNode != null){
			String reportId = showNode.getStringValue();
			if(reportId != null && !"".equals(reportId)){
				
				list = CustomInfo.getView(reportId);
				
				if(list == null){
					logger.error("config option : "+ reportId + "query data is null, or table does not exist !!");
					list = viewNode.selectNodes("show/col");
				}
					
			}
		}
		
		if(this.showList!=null){
			this.showList.clear();
		}else{
			this.showList = new ArrayList<ShowInfo>();
		}
		if(list==null){
			logger.error("no config show info . views/view/show !");
			return false;
		}
		
		for(int i=0; i<list.size(); i++){
			
			Node node = list.get(i);			
			ShowInfo show = new ShowInfo();
			
			if(!show.load(node)){
		 
				logger.error("/show load failed !");
				return false;
			}
			this.showList.add(show);
		}
		
			
		return true;
	}


	public void getShowModelInfo(Map<String, Object> showModelMap,IReportIn report){
		 	
			if(!this.outModel){
				return ;
			}
			
			this.gridView.getShowModelInfo(showList, null, report, showModelMap, this.index);
	}
 
	/**
	 * 
	 * @param reportIn
	 * @param json:���ض��󸸶���
	 * @param map:ǰ����ͼ ,���ݼ�¼
	 * @param viewIndex����ͼindex��
	 * @return
	 */
	public JSONObject exportData(IReportIn reportIn, JSONObject json, Map<String, Object> map, int viewIndex){
		
		IDataSource is = this.getMainDataSource();
		if(is==null){
			return null;
		}
		IReportOut reportData = is.getData(reportIn);
		JSONObject result = this.gridView.exportData(showList, reportData, reportIn, map, this.index);
		if(this.outModel){
			if(result!=null){
				json.put("view"+this.index, result);
			}else{
				json.put("view"+this.index, "{}");
			}
		}

		return result;
	}

	public IDataSource getMainDataSource(){
		 
		if(iSource==null){
			IReportModelManager reportModelManager = (IReportModelManager) ObjectManager.getSingleObjectInstance(ObjectManager.ReportModelManager);
			iSource = 	reportModelManager.getDataSourceById(this.ref); 
			if(iSource==null){
				logger.error("ref datasource is null !");
			}
		}
	
		return iSource;
		
	}


	public boolean check() {
		 
		this.getMainDataSource();
		if(iSource==null){
			logger.error("view model is error , ref data source is null,  ref="+this.ref);
			
			return false;
		}
		
		return true;
	}


	public void exportFile(JSONObject json, Map<String, Object> showMap, OutputStream out,
			Writer writer, IReportIn report, int fileType, String fileName) {
		
			//ֻ����index="1"��ͼ, ����Ҫ���;�ȿ���ʱ ���. 
			if("1".equals(this.index)){
				this.gridView.exportFile(json , showMap, out, writer, report, fileType, fileName, this.index);
			}
		}


	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report, int fileType,
			String fileName, String groupHeaders) {
		//ֻ����index="1"��ͼ, ����Ҫ���;�ȿ���ʱ ���. 
		if("1".equals(this.index)){
			this.gridView.exportFile(json , showMap, out, writer, report, fileType, fileName, this.index, groupHeaders);
		}
		
	}
	
	public String getRef() {
		return ref;
	}


}
