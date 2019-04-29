package com.theta.jar.report.ver1.dim1.model.view.pluginmodel.tree;

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
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
 
import com.theta.jar.report.ver1.jiekou.ILoadXML;

/**
 * 
 * 	<tree refDs="" refModel="">
					    <title><![CDATA[网络诊断]]></title>
						<children>
							<tree>
								<title><![CDATA[网络流量:]]></title>
								<children>
									<tree>
									  <title><![CDATA[Gn接口网络流量:]]></title>
									</tree>
									<tree>
										<title><![CDATA[主要SGSN网络流量]]></title>
									</tree>
								</children>
							</tree>
							<tree>
								<title><![CDATA[信令面指标:]]></title>
								<children>
								</children>
							</tree>
						    <tree>
						    	<title><![CDATA[业务状况:]]></title>
						    	<children>
						    	</children>
						    </tree>
						</children>
					</tree>	
					
 * @author Administrator
 *
 */
public class TreeNode implements ILoadXML {
	
	private static final Logger logger = Logger.getLogger(TreeNode.class);
	 
	/**
	 * 数据源ID.
	 */
	public String refDsId;
	
	/**
	 *  模型ID
	 */
	public String refModel;
 
	public Map<String, String> map  = null;
	public List<TreeNode> children = null;
	public boolean  isLeaf=true;
	public int depth=0;
	public boolean load(Node node) {

		Element el = (Element)node;
		Iterator iter = el.attributeIterator();
		while(iter.hasNext()){
			
			Attribute  att = (Attribute) iter.next();
			if("refDs".equalsIgnoreCase(att.getName().trim())){
				this.refDsId = att.getValue() ;
			}else if("refModel".equalsIgnoreCase(att.getName().trim())){
				 
				 this.refModel = att.getValue();
			}else{
				 if(this.map == null){
					 this.map = new HashMap<String, String>();
				 }
				 this.map.put(att.getName().toLowerCase(), att.getValue());		
			}
 
		
			 if(logger.isDebugEnabled()){
					logger.debug( this.getBlankStr(depth)+this.depth+". tree attribute:"+att.getName()+"="+att.getValue());	
			 }
		}
		
		List<Node> children = node.selectNodes("children/tree");
		if(children!=null){
			
			for(int i=0; i<children.size(); i++){
				Node tempNode = children.get(i);
				TreeNode tree = new TreeNode();
				 tree.depth = this.depth++;
				if(tree.load(tempNode)){
					 this.appendChild(tree);
					
				}
				
			}
		}
		
		if(this.children!=null&&this.children.size()>0){
			this.isLeaf = false;
		}else{
			this.isLeaf = true;
		}
	
		List<Node> nodes = el.elements();
		if(nodes==null){
			return true;
		}
		for(int i=0; i<nodes.size(); i++){
			
			Node tempNode = nodes.get(i);
			if("children".equalsIgnoreCase(tempNode.getName())){
				continue ;
			}
			 if(this.map == null){
				this.map = new HashMap<String, String>();
			 }
			this.map.put(tempNode.getName().toLowerCase(), tempNode.getText());
			if(logger.isDebugEnabled()){
			 
				logger.debug(this.getBlankStr(depth)+this.depth+". treeElement:"+tempNode.getName()+"="+tempNode.getText());	
			}
			
		}
	
		return true;
	}
	
	public void appendChild(TreeNode node){
		
		if(this.children==null){
			this.children = new ArrayList<TreeNode>();
		}
		this.children.add(node);
	}
	
	public String getBlankStr(int depth){
		
		String str = "";
		if(this.depth<0){
			return "";
		}
		while(depth-->0){
			str +="\t";
		}
		return str;
	}

 
	
	@Override
	public String toString() {
		return "TreeNode [refDsId=" + refDsId + ", refModel=" + refModel
				+ ", map=" + map + ", children=" + children + ", isLeaf="
				+ isLeaf + ", depth=" + depth + "]";
	}

	public JSONArray getChildren(List<TreeNode> list){
		 
		 JSONArray array = new JSONArray();
		 if(list==null){
			 return array;
		 }
		 
		 int size = list.size();
		 for(int i=0; i< list.size(); i++){
			 TreeNode node = list.get(i);
			 JSONObject json = node.getTreeInfo();
			 array.add(json);
		 }
		return array;
	}
	/**
	 * {refDs:"",refModel:"", leaf:false, children:[{},{}]}
	 * @return
	 */
	public JSONObject getTreeInfo(){
		
		JSONObject json = new JSONObject();
		json.put("refDs", this.refDsId);
		json.put("refModel", this.refModel);
		json.put("leaf", this.isLeaf);
		
		if(this.map!=null){
			
			Set<Entry<String, String>> entry = this.map.entrySet();
			Iterator<Entry<String, String>> iter = entry.iterator();
			while(iter.hasNext()){
				 Entry<String, String> it = iter.next();
				 json.put(it.getKey(), it.getValue());
				 
			}
		}
		if(!this.isLeaf){
			json.put("children",getChildren(this.children));
					
		}
	 
		return json;
	}
}
