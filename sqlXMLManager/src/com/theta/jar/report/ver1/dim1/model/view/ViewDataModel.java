package com.theta.jar.report.ver1.dim1.model.view;


import java.io.OutputStream;
import java.io.Writer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
 
import org.dom4j.Element;
import org.dom4j.Node;
 
import com.theta.jar.jiekou.IFileExportUtil;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.view.pluginmodel.GridViewModelInfo;

 
import com.theta.jar.report.ver1.jiekou.IReportIn;

import com.theta.jar.report.ver1.jiekou.control.OnQuery;
import com.theta.jar.report.ver1.jiekou.view.ISubView;
import com.theta.jar.report.ver1.jiekou.view.IViewModel;

import edu.emory.mathcs.backport.java.util.Arrays;


/**
 * 		<model id="lacciInfo" desc="无线小区质量情况表">
			<flow id="flowlacci">
				<ds ref="lacciInfo" >
					<group>groupId</group>
				</ds>
			</flow>
			<views>
				<view>
					<cols><![CDATA[ lac, ci, uplink_bytes, Attach_Number,Attach_Success, Attach_Delay ]]></cols>
					<addColumn>
						<col name="Attach成功率" value="Attach成功率"><![CDATA[$Attach_Success+$Attach_Failed ]]></col>
					</addColumn>
					
					<show>
					   <col  name="Attach_Number" desc="Attach_Number" bVisible="1"  bOut="1" converter="com.theta.report.ver1.dim1.converter.DateConverter" para="YYYY-MM-DD hh:mm:ss"/>
					</show>
				</view>
			</views>
		</model>
		
 * @author Administrator
 *
 */
public class ViewDataModel implements IViewModel {

	 private static final Logger logger = Logger.getLogger(ViewDataModel.class);
		
	 private String id;
	 private String desc;
 

	/**
	 * 存储数据显示模型信息.
	 */
	private List<ISubView> viewModelList = null;

	
	public  Map<String , Object>  showModelMap = null;
	
	public boolean load(Node node){
		
		showModelMap = null;
		
		if(node==null){
			logger.warn("load ViewDataModel failed  . no element . ");
			return false;
		}

		
	    Element el = (Element)node;
	    
	    this.id = el.attributeValue("id");
	    this.desc = el.attributeValue("desc");
	
	   // this.refFlowId = el.attributeValue("flowId");
	 
	    
	    /**
	     * 
	     */
	    if(this.id==null||this.id.trim().length()<1){
	    	
	    	logger.error("view id is null or blank  ! node:"+node.asXML());
	    	
	    	return false;
	    }
	    
	    this.id = this.id.trim().toLowerCase();
	    if(logger.isDebugEnabled()){
	    	logger.debug("load model : "+ this.id +"; desc:"+this.desc);
	    }
	    List<Node> nodes = node.selectNodes("views/view");
	    if(nodes==null||nodes.size()<1){
	    		
	    	logger.error("views/view is null .id:"+this.id+";desc:"+this.desc);
	    	return false;
	    }
		
		if(viewModelList==null){
			viewModelList = new ArrayList<ISubView>();
		}else{
			viewModelList.clear();
		}
		
		
	    for(int i=0; i< nodes.size(); i++){
		    
	    	Node tempNode = (Node) nodes.get(i);
	    	if(!loadViewConfig(tempNode)){
		    	
		    	return false;
		    }	    	
	    }
	    if(logger.isDebugEnabled()){
	    	logger.debug("ViewDataModel load over: "+this.toString());
	    }
	    return true;
	}

  

	/**
	 * 加载model/views 配置信息.
	 * @param tempNode
	 * @return
	 */
	private boolean loadViewConfig(Node tempNode) {
	
			
			Element el = (Element)tempNode;
			if(el==null){
				return false;
			}
			ISubView isv = null;
			String vewClass = el.attributeValue("loadclass");
			if(vewClass==null||vewClass.trim().length()<5){
				
				isv = new GridViewModelInfo();
			}else{
				isv = (ISubView)ObjectManager.createObjectInstance(vewClass);
				if(isv==null){
					return false;
				}
			}
			
			if(!isv.load(tempNode)){
				return false;
			}
		 
			this.viewModelList.add(isv);
		    return true;
	}

 

	public Map<String, Object> getModelShowInfo(IReportIn report) {
		
		if(showModelMap==null){
			showModelMap = new HashMap<String, Object>();
		}else{
			
			//缓存
			//return showModelMap;
			
			showModelMap.clear();
		}
		
		if(this.viewModelList==null||this.viewModelList.size()<1){
			return null;
		}

		for(int i=0; i<this.viewModelList.size(); i++){
			
		
			ISubView view = this.viewModelList.get(i);

			view.getShowModelInfo(showModelMap, report);
		}
		
		return showModelMap;
		
	}
 
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	 
 

	/**
	 * 根据请求条件取得  结果 
	 */
	public JSONObject getReportData(IReportIn reportIn) {
		
		
		if(this.viewModelList==null){
			return null;
		}

		JSONObject json = new JSONObject();
 

		/**
		 * 只存储第一行数据
		 */
		Map<String, Object> firstRecordMap = new HashMap();
		
		//
		Map<String, String> requestMap = reportIn.getRequestMap();
		if(requestMap!=null){
			Set<Entry<String, String>> set = requestMap.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while(it.hasNext()){
				
				Entry<String, String> entry = it.next();
				firstRecordMap.put(entry.getKey().toLowerCase()+"_req", entry.getValue());
			}
		}
		
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.putAll(requestMap);
		//用于记录报表使用次数功能
		try{
			String userName = jsonRequest.getString("account_id");
			reportIn.setPageName(desc);
			reportIn.setUserName(userName);
		}catch (Exception e){
		    
		}
		json.put("request", jsonRequest);
		
		if(logger.isInfoEnabled()){
			logger.info("view size:"+this.viewModelList.size());			
		}

		for(int i=0; i< this.viewModelList.size(); i++){
				
			ISubView vm = this.viewModelList.get(i);
			logger.info("view name:"+vm.getRef());
			//无用 ,被sql中autoQueryTotal属性取代 
			//reportIn.setFindTotal(vm.bFindTotal());
			JSONObject re = vm.exportData(reportIn, json, firstRecordMap, (i+1));
			if(!reportIn.isSuccess()){
				
				json.put("success", 0);
				json.put("tipInfo", reportIn.getTipInfo());
				logger.error("view execute failed,"+reportIn.getTipInfo()+" ; not continue execute . view:"+vm.toString());
				break;
			}
		
		}
		reportIn.closeConnection();
		if(logger.isTraceEnabled()){
			logger.debug("result:"+json);
		}
		return json;
	}

	public boolean check() {
		
		if(viewModelList==null){
			logger.error("model /views/view list is null . id:"+this.id +";desc:"+this.desc);
			return false;
		}
	
		for(int i=0; i<this.viewModelList.size(); i++){
			if(!this.viewModelList.get(i).check()){
				return false;
			}
		}
		return true;
	}



	/**
	 * 导出文件
	 */
	public void exportFile(OutputStream out, Writer writer,
			IReportIn report, int fileType, String fileName) {
	
		JSONObject json = getReportData(report);
		Map<String, Object> showMap = getModelShowInfo(report);
		for(int i=0; i< this.viewModelList.size(); i++){
				 ISubView vm = this.viewModelList.get(i);
			 	 vm.exportFile(json, showMap, out, writer, report, fileType, fileName);
		}
	}
	
	public void exportFile(OutputStream out, Writer writer, IReportIn report,
			int fileType, String fileName, String column, String groupHeaders) {
		JSONObject json = getReportData(report);
		Map<String, Object> showMap = getModelShowInfo(report);
		if(!column.isEmpty()){
			showMap = disposeShow(showMap, column, fileType);
		}
		for(int i=0; i< this.viewModelList.size(); i++){
				 ISubView vm = this.viewModelList.get(i);
			 	 vm.exportFile(json, showMap, out, writer, report, fileType, fileName, groupHeaders);
		}
		
	}


	/**
	 * 导出文件 2013/9/13  根据页面显示列信息进行导出
	 */
	public void exportFile(OutputStream out, Writer writer,
			IReportIn report, int fileType, String fileName,String column) {
	
		JSONObject json = getReportData(report);
		Map<String, Object> showMap = getModelShowInfo(report);
		if(!column.isEmpty()){
			showMap = disposeShow(showMap, column, fileType);
		}
		for(int i=0; i< this.viewModelList.size(); i++){
				 ISubView vm = this.viewModelList.get(i);
			 	 vm.exportFile(json, showMap, out, writer, report, fileType, fileName);
		}
	}
	
	/**
	 * 
	 * @param map				列头信息
	 * @param dataIndex			要显示的 dataIndex值
	 * @return
	 */
	private Map<String, Object> disposeShow(Map<String, Object> map, String dataIndex, int fileType){
		
		Set<String> set = map.keySet();
		JSONObject obj = null;
		
		for (String str : set) {
			obj = (JSONObject) map.get(str);
		}
		obj = (JSONObject) map.get("grid1");
		
		JSONArray arr = (JSONArray) obj.get("columns");	
		String[] shows = dataIndex.split(",");
		
		List<String> list = new ArrayList<String>();
		
		for(String str : shows)
			list.add(str);
		
		if(fileType == IFileExportUtil.MODULELISTSql){
			int m_index = Arrays.binarySearch(shows, "module_id");
			int p_index = Arrays.binarySearch(shows, "parent_id");
			
			if(m_index < 0) list.add("module_id");
			if(p_index < 0) list.add("parent_id");
		}
			
		for(int i=0; i < arr.size(); i++){
			
			JSONObject column = (JSONObject) arr.get(i);
			
			for(int k = 0; k < list.size(); k++){
				if(column.getString("dataIndex").equals(list.get(k))){
					column.remove("hidden");
					break;
				}else{
					column.put("hidden", true);
				}
					
			}
		}
		return map;
	}

	/**
	 * 根据请求条件取得  结果 
	 */
	public JSONObject getReportTotal(IReportIn reportIn) {
		
		
		if(this.viewModelList==null){
			return null;
		}

		JSONObject json = new JSONObject();
 

		/**
		 * 只存储第一行数据
		 */
		Map<String, Object> firstRecordMap = new HashMap();
		
		//
		Map<String, String> requestMap = reportIn.getRequestMap();
		if(requestMap!=null){
			Set<Entry<String, String>> set = requestMap.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while(it.hasNext()){
				
				Entry<String, String> entry = it.next();
				firstRecordMap.put(entry.getKey().toLowerCase()+"_req", entry.getValue());
			}
		}
		
		JSONObject jsonRequest = new JSONObject();
		jsonRequest.putAll(requestMap);
		json.put("request", jsonRequest);

		Connection conn = reportIn.getConnection();
		//李琦原
		OnQuery onQuery = (OnQuery) ObjectManager.getSingleObjectInstance(ObjectManager.sqlQueryKey);
		//DbOracleQuery dbQuery = new DbOracleQuery();
		int total = onQuery.getTotalNum(conn, reportIn.getSql(), 60);
		json.put("total", total);

		reportIn.closeConnection();
		if(logger.isTraceEnabled()){
			logger.debug("result:"+json);
		}
		return json;
	}

}
