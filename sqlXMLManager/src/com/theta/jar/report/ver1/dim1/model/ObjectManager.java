package com.theta.jar.report.ver1.dim1.model;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
 
public class ObjectManager {

	private static final Logger logger = Logger.getLogger(ObjectManager.class);
		
	//public static String sqlQueryKey = "com.theta.report.ver1.dim1.control.query.DbOracleQuery"; 
	public static String sqlQueryKey = "com.theta.report.ver1.dim1.control.query.DbOracleQuery"; 
	public static String FileExportUtil = "com.theta.jar.FileExportUtil";
	public static String FileExportInsertAndUpdateSqlTextUtil = "com.theta.jar.FileExportInsertAndUpdateSqlTextUtil";
	public static String DBListManager = "com.theta.jar.DBListManager";
	public static String ReportModelManager = "com.theta.jar.report.ver1.dim1.model.ReportModelManager";
	private static Map<String, Object> map = new Hashtable<String, Object>();
	
	/**
	 * 保证每次返回 的Object是唯一的 。
	 * @param className
	 * @return
	 */
	public static synchronized Object getSingleObjectInstance(String className){
		
		if(className==null||className.length()<1){
			return null;
		}
		
		Object obj = map.get(className);
		if(obj!=null){
			
			return obj;
		}
		 
		try {
			
			Class cl =Class.forName(className);
			obj = cl.newInstance();
			if(obj!=null){
				map.put(className, obj) ;
			}
		} catch (Exception e) {
			logger.error("className:"+className+"create object error , className:"+className,e);
		}
		
		return obj;
	}
	
 
	/**
	 * 创建一个 object对象
	 * @param className
	 * @return
	 */
	public static Object createObjectInstance(String className){
		
		if(className==null){
			return null;
		}
		
		Object obj =  null;
		 
		try {
			Class cl =Class.forName(className);
			obj = cl.newInstance();
		 
		} catch (Exception e) {
			logger.error("className:"+className+"create object error :",e);
		}
		
		return obj;
	}
	
 
	public static void clear(){
		
		map.clear();
	}
}
