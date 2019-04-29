package com.theta.jar.report.ver1.dim1.model.view.plugin;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 
import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.dim1.model.view.pluginmodel.tree.TreeNode;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.view.IView;
import com.theta.jar.report.ver1.jiekou.view.tree.IViewTree;

public class ExtTreeInfo implements IView, IViewTree{

	 private static final Logger logger = Logger.getLogger(ExtTreeInfo.class);

	 /**
		 * [{
	            id: 'topic', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
	            header: "Topic",
	            dataIndex: 'title',
	            width: 420,
	            sortable: true
	        },{
	            id: 'last',
	            header: "Last Post",
	            dataIndex: 'lastpost',
	            width: 150,
	            sortable: true
	        }]
		 */
		
		public JSONArray getHeader(List<ShowInfo> showList, IReportIn report) {
			
			if(showList==null){
				return null;
			}
			
		
			JSONArray al = new JSONArray();
		
			
			JSONObject obj = null;
			
		 
			
			for(int i=0; i < showList.size(); i++){
				 
				ShowInfo show = showList.get(i);
				if(show.bOut<1){
					continue;
				}
				 obj = new JSONObject();
				//obj.put("id", show.getName() );
	           // obj.accumulate(key, value)		
	   			obj.put("header", show.getDesc());
				obj.put("dataIndex", show.getName());
 				Map<String, Object> map = show.getMap();
				
				if(map==null){
					 
					 obj.put("width", 200);
				}
				if(map!=null){
				
		 
					Set<Entry<String, Object>> entry = map.entrySet();
					Iterator<Entry<String, Object>> it = entry.iterator();
					while(it.hasNext()){
						
						Entry<String, Object> en = it.next();
						
						obj.put(en.getKey(), en.getValue());
					}
				}
				
				al.add(obj);
			}
			
			return al;
		}

	public void getShowModelInfo(List<ShowInfo> showList, Object obj,  IReportIn report,
			Map<String, Object> showModelMap, String viewIndex) {
	 
		if(showModelMap==null){
			logger.error("showModelMap is null \n");
			return ;
		}
	
		TreeNode root = (TreeNode)obj;
		JSONObject json = new JSONObject();
		JSONArray al1 = getHeader(showList, report);
		JSONObject rootNode = this.getTreeNodes(showList, root, report);
		 
		json.put(this.columns, al1);
		json.put(this.rootNode, rootNode);
		showModelMap.put("tree"+viewIndex, json);
		 
	}

	public JSONObject exportData(List<ShowInfo> showList,
			IReportOut reportData, IReportIn reportIn, Map<String, Object> map,
			String viewIndex) {
	 
		return null;
	}

	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report,
			int fileType, String fileName, String index) {
		 
	}
	public void exportFile3(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report,
			int fileType, String fileName, String index) {
		
	}
	public JSONObject getTreeNodes(List<ShowInfo> showList, TreeNode node, IReportIn report) {
	 
		JSONObject json = new JSONObject();
		
		if(node==null){
			logger.warn("root node  is null . ");
			return json ;
		}
	
		return node.getTreeInfo();
	}

	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report, int fileType,
			String fileName, String index, String groupHeaders) {
		
	}


}
