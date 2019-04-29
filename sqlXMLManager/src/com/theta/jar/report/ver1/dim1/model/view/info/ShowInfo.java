package com.theta.jar.report.ver1.dim1.model.view.info;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.report.myutil.BaseUtil2;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.data.IRTConverter;

public class ShowInfo implements ILoadXML{
	
	private static final Logger logger = Logger.getLogger(ShowInfo.class);

	public String name;
	//引用列值,转换器使用 
	public String refName=null;
	public String desc;
	
	//desc是否使用模板
	public boolean bDescUseTpl = false;
	
	public int bVisible = 1;
	public int bOut		= 1;
	
	public Map<String, Object> map = null;
	
	/**
	 * converter是否全局唯一
	 */
	public boolean bSingleConverter = true;
	public String conStr = null;
	/**
	 * 输出数值前调用转换器 
	 */
	public IRTConverter converter = null;
	
	public String parameter = null;

	public  int width=100;
	
	public ShowInfo clone(){
		
		ShowInfo show = new ShowInfo();
		
		show.bOut = this.bOut;
		show.bVisible = this.bVisible;
		show.name = this.name;
		show.desc = this.desc;
		show.converter  = this.converter;
		show.parameter = this.parameter;
		show.conStr = this.conStr;
		show.map = this.map;
		show.width = this.width;
		show.refName = this.refName;
		
		return show ;
		
	}
	public boolean load(Node node) {
		
		if(node==null){
			return false;
		}
		
		Element el = (Element)node;
	
		String visible = null;
		String out = null;
		
		Iterator iter = el.attributeIterator();
		while(iter.hasNext()){
			
			Attribute  att = (Attribute) iter.next();
			if("name".equalsIgnoreCase(att.getName().trim())){
				this.name = att.getValue() ;
			}else if("desc".equalsIgnoreCase(att.getName().trim())){
				this.desc = att.getValue();
			} else if("bVisible".equalsIgnoreCase(att.getName().trim())){
				visible = att.getValue();
			}else if("bOut".equalsIgnoreCase(att.getName().trim())){
				out = att.getValue();
			}else if("converter".equalsIgnoreCase(att.getName().trim())){
				
				this.conStr = att.getValue();
			}else if("ref".equalsIgnoreCase(att.getName().trim())){
				this.refName = att.getValue().trim().toLowerCase();
			}else if("single".equalsIgnoreCase(att.getName().trim())){
				 if("0".equals(att.getValue())||"false".equalsIgnoreCase(att.getValue())){
					 this.bSingleConverter = false;	 
				 } 
			}else if("width".equalsIgnoreCase(att.getName().trim())){
				try{
					this.width =Integer.parseInt(att.getValue().trim());	
				}catch(Exception ex){
					logger.error("width is not a integer . width:"+att.getValue(),ex);
				}
				if(this.map == null){
					this.map = new HashMap<String, Object>();
				}
				this.map.put("width", this.width);
			}else{
		
				
				if(this.map == null){
					this.map = new HashMap<String, Object>();
				}
				this.map.put(att.getName(), att.getValue());
			}
		}
		
		if(this.name==null||this.name.length()<1){
			logger.error("show/col/@name could't couldn't be null !"+node.asXML());
			return false;
		}
		if(this.desc==null||this.desc.length()<1){
			this.desc = this.name.trim() ;
		}
		
		if(this.desc.indexOf("$")>=0){
			this.bDescUseTpl = true;
		}
		
		this.name = this.name.trim().toLowerCase();
		
		if(visible!=null){
			try{
				this.bVisible = Integer.parseInt(visible);	
			}catch(Exception ex){
				logger.error("this.vVisible:"+visible+ex);
			}
			
		}else{
			this.bVisible = 1;
		}
		
	
		if(out!=null){
			this.bOut = Integer.parseInt(out);
		}else{
			this.bOut = 1;
		}
		
		 
		if(this.conStr!=null){
			
			if(this.bSingleConverter){
				this.converter = (IRTConverter) ObjectManager.getSingleObjectInstance(this.conStr.trim());
			}else{
				this.converter = (IRTConverter) ObjectManager.createObjectInstance(this.conStr.trim());
			}
			
		
		}
		
		String temp = el.attributeValue("para");
		if(temp != null && temp.trim().length()>0){
			this.parameter =  temp.trim();
		}else{
			
			temp = BaseUtil2.getXpathValue(node,"para");
			if(temp!=null&&temp.length()>1){
				this.parameter = temp;
			}
		}
		
		/**
		 * 初始化构造器。
		 */
		if(this.converter!=null){
			this.converter.init(this.parameter);
		}
		
		return true;
	}
   
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	
	public String getDesc(Map requestMap){
		
		if(!this.bDescUseTpl){
			
			return desc;
		} 
		
		String desc2 = this.desc;
		
		Writer sw = new StringWriter();
		 
		VelocityContext context = new VelocityContext();
		VelocityEngine ve = new VelocityEngine();
		
		if(requestMap!=null){

			  Set<Entry<String, String>> set = requestMap.entrySet();
			  Iterator<Entry<String, String>> it = set.iterator();
			  
			  while(it.hasNext()){
				  Entry<String, String> entry = it.next();
				  context.put(entry.getKey(), entry.getValue());	  
			  }	
		}
		
	 
		 try {
			ve.evaluate(context, sw, "", desc2);
		 } catch (Exception e) {
			 
			logger.error("parse template error !this.sql:"+this.desc, e);
			return this.desc;
		} 
		 
		 
		 desc2 = sw.toString().trim();
		if(logger.isDebugEnabled()){
			logger.debug("query sql:"+desc2);		
		}
	
		
		return desc2;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getbVisible() {
		return bVisible;
	}
	public void setbVisible(int bVisible) {
		this.bVisible = bVisible;
	}
	public int getbOut() {
		return bOut;
	}
	public void setbOut(int bOut) {
		this.bOut = bOut;
	}
	public String getConStr() {
		return conStr;
	}
	public void setConStr(String conStr) {
		this.conStr = conStr;
	}
	public IRTConverter getConverter() {
		return converter;
	}
	public void setConverter(IRTConverter converter) {
		this.converter = converter;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public String toString() {
		return "ShowInfo [name=" + name + ", refName=" + refName + ", desc="
				+ desc + ", bVisible=" + bVisible + ", bOut=" + bOut + ", map="
				+ map + ", conStr=" + conStr + ", converter=" + converter
				+ ", parameter=" + parameter + ", width=" + width + "]";
	}
  

}
