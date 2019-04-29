package com.theta.jar.report.ver1.dim1.model.ds.info;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.jiekou.IDBListManager;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;



public class CustomInfo {
	
	private static Logger logger = Logger.getLogger(CustomInfo.class);

	private static Map<String,Set<Map<String,String>>> sqls = new HashMap<String,Set<Map<String,String>>>();
	private static Map<String,List<BeanInfo>> columns = new HashMap<String,List<BeanInfo>>();
	

	//清空sqls和columns
	public static void release(){
		sqls.clear();
		columns.clear();
		sqls = null;
		columns = null;
	}
	//李琦 DBListManager.getMainConnection("syc_column_list");
public static void init(int sysType){
	
	    //李琦原
	  	IDBListManager  idbListManager = (IDBListManager)ObjectManager.getSingleObjectInstance(ObjectManager.DBListManager); 
		String configName = getSysType(sysType);
		try {
			Connection conn = idbListManager.getMainConnection("syc_column_list");
			merge(getData(conn,configName));
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getSysType(int sysType){
		String config_name = "";
		switch(sysType){
			case 1:
				config_name = "gb";
				break;
			case 2:
				config_name = "gn";
				break;
			case 3:
				config_name = "wlan";
				break;
			case 4:
				config_name = "lte";
				break;
			default:
				config_name = "gb";
				break;
		}
		
		return config_name;
		
	}

	
	/**
	 * @param conn
	 * @param sql
	 */
	public static void executeSql(Connection conn, String sql){
		
		Statement sm = null;
		
		try {
			conn.setAutoCommit(true);
			sm = conn.createStatement();
			sm.executeUpdate(sql);
			
			logger.info(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 整合syc_column_list表数据，可以按table_name得到sql，也可以按reportid得到展示列信息
	 * @param list
	 */
	public static void merge(List<BeanInfo> list){
		
		
		if(list == null || list.size() <= 0){
			logger.error("No query the data!!!!! ");
			return;
		}
		
		sqls.clear();
		columns.clear();
		
		List<String> reportids = new ArrayList<String>();		//所有reportid
		List<String> table_name = new ArrayList<String>();		//所有表名
		
		for(int i=0; i<list.size(); i++){
			
			BeanInfo bi = list.get(i);
			String tableName = bi.getTableName();
			String reportid = bi.getReportid();
		
			if(i == 0){
				table_name.add(tableName);
				reportids.add(reportid);
			}
			
			if(!table_name.contains(tableName)){
				table_name.add(tableName);
			}
			
			if(!reportids.contains(reportid)){
				reportids.add(reportid);
			}
				
		}
		
		for(String name : table_name){	
			
			Set<Map<String,String>> field = new HashSet<Map<String,String>>();		//所有字段
			
			for(int i=0; i<list.size(); i++){
				BeanInfo bi = list.get(i);
				String tableName = bi.getTableName();
				String column = bi.getcName();			//数据库字段
//				String alias = bi.getColumnAlias();
				String column_sql = bi.getColumnSql();
				
				if(name.equals(tableName)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("column_name", column);
//					map.put("alias", alias);
					map.put("column_sql", column_sql);
					field.add(map);
				}
			}
			sqls.put(name, field);
		}	
		
		for(String report_id : reportids){
			
			List<BeanInfo> beaninfo = new ArrayList<BeanInfo>();
			
			for(int i=0; i<list.size(); i++){
				BeanInfo bi = list.get(i);
				String reportid = bi.getReportid();
				
				if(report_id.equals(reportid)){
					beaninfo.add(bi);
				}
			}
			columns.put(report_id, beaninfo);
		}
		
		
	}
	
	
	/**
	 * 加载会话表所有要显示的字段
	 * @param conn
	 * @param condition
	 * @return
	 */
	public static List<BeanInfo> getData(Connection conn, String condition){
		
		String sql = "select * from " +
						"(select c_name, t_name, b.* from " +
							"(select column_name c_name,table_name t_name from syc_column_config where column_status = 1 and config_name = '"+condition+"') a left join " + 
							"(select * from syc_column_list) b " +
						"on c_name = upper(b.column_name) " +
						"and t_name = upper(b.table_name)) " +
					"where list_id not in 0";
		
		List<BeanInfo> list = new ArrayList<BeanInfo>();
		
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs == null){
				logger.error("syc_column_list have not data or table does not exist !");
				return null;
			}
			
			while(rs.next()){
				
				BeanInfo bi = new BeanInfo();
				
				bi.setReportid(rs.getString("reportid"));
				bi.setTableName(rs.getString("table_name"));
				bi.setcName(rs.getString("c_name"));			//数据库字段名字
				bi.setColumnName(rs.getString("column_name"));
				bi.setColumnAlias(rs.getString("column_alias"));
				bi.setColumnDesc(rs.getString("column_desc"));
				bi.setColumnSql(rs.getString("column_sql"));
				bi.setColumnRef(rs.getString("column_ref"));
				bi.setAlign(rs.getString("align"));
				bi.setConverter(rs.getString("converter"));
				bi.setPara(rs.getString("para"));
				bi.setRenderer(rs.getString("renderer"));
				bi.setWidth(rs.getString("width"));
				bi.setBout(rs.getString("bout"));
				bi.setBvisible(rs.getString("bvisible"));
				bi.setSort_num(rs.getString("sort_num"));
				bi.setSortable(rs.getString("sortable"));
			
				list.add(bi);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(list.size() > 0)
			return list;
		else{
			return null;
		}
	}
	
	/**
	 * 合成会话需要的sql语句
	 * 只有 select 后 及  from 前的查询字段sql
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getSql(String tableName){
		
		Set<Map<String, String>> set = sqls.get(tableName);
		
		if(set == null){
			logger.error("config option error or table does not exist !!");
			return null;
		}
		
		String sql = "";
		
		Iterator<Map<String, String>> it = set.iterator();
		while(it.hasNext()){
			Map<String, String> map = it.next();
			String columnName = map.get("column_name").toLowerCase();
//			String alias = map.get("alias");
			String columnSql = map.get("column_sql");
			if(columnSql != null)
				columnSql = columnSql.trim();
			
			if(sql.contains(columnName)){
				continue;
			}else{
				if(columnSql != null && !"".equals(columnSql))
					sql += columnSql + " as " + columnName + ",\n";
				else
					sql += columnName + ",\n";
			}
		}
		
		if(sql.endsWith(",\n"))
			return sql.substring(0,sql.length() - 2);
		else
			return sql;
		
		
	}
	
	/**
	 * 动态生成 view节点下的 show节点的所有内容
	 * 
	 * <show>
	 * 		<col name="szapn" desc="访问点名" bvisible="1" bout="1" width="80"  sortable="true" align="left" ></col>
	 *		<col name="szmsisdn" desc="用户号码" bvisible="1" bout="1" width="90"  sortable="true" align="left" ></col>
	 *		<col name="szimsi" desc="IMSI号" bvisible="0" bout="1" width="95"  align="left" sortable="true" ></col>
	 *		<col name="szimei" desc="IMEI号" bvisible="0" bout="1" width="95"  align="left" sortable="true" ></col>
	 * </show>
	 * 
	 * @param reportid
	 * @return
	 */
	public static List<Node> getView(String reportid){
		
		Document document = DocumentHelper.createDocument();		// 建立document对象
		Element show = document.addElement("show");					// 建立show节点
		
		List<Object> list = sortColumn(reportid);
		
		if(list == null){
			logger.error("config option error or table does not exist !");
			return null;
		}
		
		for(int i = 0; i < list.size(); i++){
			
			Element col = show.addElement("col");					//建立col节点
			
			BeanInfo bi = (BeanInfo) list.get(i);
			col.addAttribute("name", bi.getColumnName());						
			col.addAttribute("desc", bi.getColumnDesc());
			
			if(bi.getColumnRef() != null && !"".equals(bi.getColumnRef()) && !" ".equals(bi.getColumnRef()))
				col.addAttribute("ref", bi.getColumnRef());
			
			col.addAttribute("align", bi.getAlign());
			col.addAttribute("width", bi.getWidth());
			col.addAttribute("bvisible", bi.getBvisible());
			col.addAttribute("bout", bi.getBout());
			col.addAttribute("sortable", bi.getSortable());
			
			if(bi.getRenderer() != null && !"".equals(bi.getRenderer()) && !" ".equals(bi.getRenderer()))
				col.addAttribute("renderer", bi.getRenderer());
				
			if(bi.getConverter() != null && !"".equals(bi.getConverter()) && !" ".equals(bi.getConverter()))
				col.addAttribute("converter", bi.getConverter());
			
			if(bi.getPara() != null && !"".equals(bi.getPara()) && !" ".equals(bi.getPara()))
				col.addAttribute("para", bi.getPara());
			
		}

		return document.selectNodes("show/col");
		
	}
	
	/**
	 * 对显示的列根据sort_num进行顺序排列
	 * @param reportid
	 * @return
	 */
	public static List<Object> sortColumn(String reportid){
		
		List<BeanInfo> list = columns.get(reportid);
		
		if(list == null){
			return null;
		}
		
		Object[] oo = list.toArray();

		for(int i = 0; i < oo.length; i++){
			BeanInfo bi = (BeanInfo) oo[i];
			for(int j = 0; j < oo.length - i; j++){
				BeanInfo bii = (BeanInfo) oo[j];
				if(Integer.parseInt(bi.getSort_num()) > Integer.parseInt(bii.getSort_num())){
					BeanInfo temp = null;
					temp = bi;
					bi = bii;
					bii = temp;
				}
			}
			
		}
		
		return Arrays.asList(oo);
	}
}
