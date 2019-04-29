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
	 * 是否查询总数
	 */
	//private boolean bFindTotal = false;

	private String		    outClass = null;

    private List<ShowInfo>  showList = null;
    private List<ShowInfo>  outNamesList = null;
     
	private String 			outAttr=null;
	private String			ref = null;
	
	
	

	/**
	 * 默认为1,2,3.... 
	 * 输出数据 的  后缀索引。 jsonobject.(view)(index)
	 */
	public String			index="1";
	
	/**
	 * 是否输出 模型值。
	 */
	private boolean			outModel = true;
	
	/**
	 * 显示插件接口.
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
	 * 解析 /model/show部分 
	 * @param node
	 * @return
	 */
	private boolean parseShowColsInfo(Node viewNode) {
		 
		List<Node>  list = viewNode.selectNodes("show/col");
		
		//如果show节点上有reportId,且reportId不为空则替换viewNode节点下的内容
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
	 * @param json:返回对象父对象
	 * @param map:前面视图 ,数据记录
	 * @param viewIndex：试图index号
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
		
			//只导出index="1"视图, 否则要大改;等空闲时 大改. 
			if("1".equals(this.index)){
				this.gridView.exportFile(json , showMap, out, writer, report, fileType, fileName, this.index);
			}
		}


	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report, int fileType,
			String fileName, String groupHeaders) {
		//只导出index="1"视图, 否则要大改;等空闲时 大改. 
		if("1".equals(this.index)){
			this.gridView.exportFile(json , showMap, out, writer, report, fileType, fileName, this.index, groupHeaders);
		}
		
	}
	
	public String getRef() {
		return ref;
	}


}
