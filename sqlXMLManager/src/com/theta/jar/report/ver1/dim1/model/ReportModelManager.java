package com.theta.jar.report.ver1.dim1.model;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.theta.jar.common.app.config.AppConfigManager;
import com.theta.jar.jiekou.IReportModelManager;
import com.theta.jar.report.myutil.BaseUtil2;
import com.theta.jar.report.ver1.dim1.model.ds.MultipleDS;
import com.theta.jar.report.ver1.dim1.model.ds.SimpleDS;
import com.theta.jar.report.ver1.dim1.model.ds.StaticDS;
import com.theta.jar.report.ver1.dim1.model.merge.RsDataMerge;
import com.theta.jar.report.ver1.dim1.model.view.ViewDataModel;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.control.IMerge;
import com.theta.jar.report.ver1.jiekou.control.OnQuery;
import com.theta.jar.report.ver1.jiekou.data.IData;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;
import com.theta.jar.report.ver1.jiekou.view.IViewModel;


public class ReportModelManager implements IReportModelManager{

	private static final Logger logger = Logger.getLogger(ReportModelManager.class);
 
	private static Hashtable<String, IDataSource> dsTable = new Hashtable<String, IDataSource>();
	private static Hashtable<String, IViewModel>  viewTable = new Hashtable<String, IViewModel>();
 
	public static String reportDir  = null;
	public ReportModelManager() {
		super();
	}

	/**
	 * Description: 添加一个文件模型
	 * @author 李琦
	 * @param fileName 要添加模型的文件
	 */
	public synchronized void addOneModelByFile(String fileName) {

		addModel(fileName);
		// 整理model .
		checkModels();
	}
	

	/**
	 * Description: 获取查询模型
	 * @author 李琦
	 * @param sourceId 模型名
	 * @return IDataSource
	 */
	public IDataSource getDataSourceById(String sourceId) {

		return dsTable.get(sourceId);
	}

	 
	/**
	 * Description: 获取查询模型
	 * @author 李琦
	 * @param id 模型名
	 * @return IReportOut
	 */
	public IReportOut getReportOutByDsId(String id) {

		IDataSource ds = getDataSourceById(id);
		if (ds == null) {
			return null;
		}

		IReportIn reportIn = new ReportRequest();
		IReportOut out = ds.getData(reportIn);

		return out;
	}
	
	
	/**
	 *  Description: 执行 模型 信息 ,直接寻找数据源进行操作。
	 * @param dsId 模型名
	 * @param map 查询参数条件
	 * @return IReportOut
	 */
	public IReportOut executeSqlByDsId(String dsId, Map<String, String> map) {

		IDataSource ds = getDataSourceById(dsId.toLowerCase());
		if (ds == null) {
			return null;
		}

		ReportRequest reportIn = new ReportRequest();
		reportIn.map = map;

		return ds.getData(reportIn);

	}
	
	/**
	 * Description: 执行 模型 信息 ,直接寻找数据源进行操作。
	 * @param dsId
	 * @param reportIn
	 * @return
	 */
	public IReportOut executeSqlByDsId(String dsId, ReportRequest reportIn) {

		IDataSource ds = getDataSourceById(dsId.toLowerCase());
		if (ds == null) {
			logger.error("find ds failed ! dsId:" + dsId);
			return null;
		}

		return ds.getData(reportIn);

	}
	
	public Map<Object, List<IData>> getDataMapByDsId(String id) {

		IDataSource ds = getDataSourceById(id);
		if (ds == null) {
			return null;
		}

		IReportIn reportIn = new ReportRequest();
		IReportOut out = ds.getData(reportIn);
		if (out == null) {
			return null;
		}

		return out.getDataMap();
	}
	
	public Map<Object, List<IData>> getDataMapByDsId(String id,Map<String,String> map){
		
		IDataSource ds = getDataSourceById(id);
		if (ds == null) {
			return null;
		}

		ReportRequest reportIn = new ReportRequest();
		reportIn.map = map;
		IReportOut out = ds.getData(reportIn);
		try {
			reportIn.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (out == null) {
			return null;
		}

		return out.getDataMap();
	}
	
	public List<List<IData>> getDataListByDsId(String id) {

		IDataSource ds = getDataSourceById(id);
		if (ds == null) {
			return null;
		}

		IReportIn reportIn = new ReportRequest();
		IReportOut out = ds.getData(reportIn);
		if (out == null) {
			return null;
		}

		return out.getDataList();
	}
	
	/**
	 * 根据dsId, 所给参数 提取 数据
	 * @param id
	 * @param map
	 * @return
	 */
	public List<List<IData>> getDataListByDsId(String dsId, Map<String, String> map){
		
		IDataSource ds = getDataSourceById(dsId);
		if (ds == null) {
			return null;
		}

		ReportRequest reportIn = new ReportRequest();
		reportIn.map = map;
		IReportOut out = ds.getData(reportIn);
		if (out == null) {
			return null;
		}

		return out.getDataList();
	}
	
	public void loadFileNames(List<String> fileList, File file) {

		if (file == null) {
			return;
		}
		
		if (file.isFile() && file.getName().endsWith(".xml")) {

			fileList.add(file.getPath());
			if (logger.isDebugEnabled()) {
				logger.debug("load file:" + file.getPath());
			}

			return;
		}

		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		
		for (int i = 0; i < files.length; i++) {
			loadFileNames(fileList, files[i]);
		}

		return;
	}
	
	/**
	 * Description: 加载目录 下 所有配置文件
	 * @author 李琦
	 * @param filePath 文件路径
	 */
	public void loadModelByDir(String filePath) {

		this.reportDir = filePath;
		File file = new File(filePath);

		File[] fileNames = file.listFiles();

		List<String> list = new ArrayList<String>();
		if (fileNames != null) {

			for (int i = 0; i < fileNames.length; i++) {
				loadFileNames(list, fileNames[i]);
			}
		}
		loadModel(list);
	}
	
	
	
	/**
	 * Description: 将 Flow 与显示 部分关联 。
	 * @author 李琦
	 */
	private static void checkModels() {

		Enumeration<String> keys = viewTable.keys();

		while (keys.hasMoreElements()) {

			String viewId = keys.nextElement();
			IViewModel vo = viewTable.get(viewId);
			if (vo == null) {
				continue;
			}

			vo.check();
		}

	}

	/**
	 * Description: 通过文件列表添加 模型 
	 * @author 李琦
	 * @param fileList 文件列表
	 */
	public synchronized void loadModel(List<String> fileList) {

		dsTable.clear();
		viewTable.clear();

		if (fileList == null || fileList.size() < 1) {
			return;
		}

		for (int i = 0; i < fileList.size(); i++) {
			addModel(fileList.get(i));
		}

		checkModels();

	}
	

	/**
	 * Description: 添加  一个文件模型 ,未关联数据源 和视图 
	 * @author 李琦
	 * @param fileName 要添加的模型文件
	 */
	private static synchronized void addModel(String fileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("addMode , fileName :" + fileName);
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(fileName);
		} catch (DocumentException e) {
			logger.error("read file failed ! file:" + fileName, e);
			return;
		}

		addDs(document);
		addViewModel(document);

	}
	

 
	@SuppressWarnings("unchecked")
	private static synchronized void addViewModel(Document document) {

		if (document == null) {
			return;
		}

		List<Node> list = document.selectNodes("/report/models/model");
		if (list == null) {
			logger.warn("/report/models is null . no model vies  config .");
			return;
		}

		for (int i = 0; i < list.size(); i++) {

			Element node = (Element) list.get(i);

			IViewModel vdm = new ViewDataModel();
			if (!vdm.load(node)) {
				continue;
			}

			viewTable.put(vdm.getId(), vdm);
		}

		return;

	}

	private static  synchronized void addDs(Document document){
		
		if(document==null){
			return ;
		}
 
		List<Node> list = document.selectNodes("/report/datas/data");
		if(list==null){
			logger.warn("/report/ds is null . no data source config .");
			return ;
		}
		
		OnQuery query = (OnQuery) ObjectManager.getSingleObjectInstance(ObjectManager.sqlQueryKey); //new DbOracleQuery();

		 		
		for(int i=0; i< list.size(); i++){
			
			Element node= (Element)list.get(i);
			String typeS = node.attributeValue("type");
			String id = node.attributeValue("id");
			
			//logger.info("load one data source: "+node.asXML());
			if(id==null){
				logger.error("id is null! couldn't be null! "+node.asXML());
				return ;
			}
			
			id = id.trim().toLowerCase();
			//默认为2， simpleDS
			int type=2;
			
			List<Node> ns = node.selectNodes("/data/ds");
			
			if(ns!=null&&ns.size()>=2){
				
				type = 3;
			}else{
			
				if(typeS !=null){
					try{
						type = Integer.parseInt(typeS.trim());
					}catch(Exception ex){
						logger.error("parse type error,",ex);
					}	
				}
			}
			
			IMerge  iMerge = new RsDataMerge();
			if (type == 1) {
				StaticDS sd = new StaticDS();
				sd.load(node);
				sd.setOnQuery(query);
				if (logger.isDebugEnabled()) {
					logger.debug(sd.toString());
				}
				dsTable.put(id, sd);
			} else if (type == 2) {
				SimpleDS sd = new SimpleDS();
				sd.load(node);
				sd.setOnQuery(query);
				if (logger.isTraceEnabled()) {
					logger.trace(sd.toString());
				}

				dsTable.put(id, sd);
			} else if (type == 3) {
				MultipleDS md = new MultipleDS();
				md.load(node);
				md.setiMerge(iMerge);
				if (logger.isTraceEnabled()) {
					logger.trace(md.toString());
				}
				dsTable.put(id, md);

			}
			
		}
		
		return ;
	}

	/**
	 * Description: 根据 dsId返回引用到的数据源 。
	 * @param dsId 模型名
	 * @return IDataSource
	 */
	public IDataSource getDSById(String dsId) {

		return dsTable.get(dsId);
	}


	/**
	 * Description: 根据 viewId返回 报表模型 
	 * @param viewId 模型名
	 * @return IViewModel
	 */
	public IViewModel getReportModelById(String viewId) {

		if (viewId == null) {
			return null;
		}

		return viewTable.get(viewId.trim().toLowerCase());
	}
	
	/**
	 * Description: 取得模型显示信息 
	 * @param reportId 模型名
	 * @param report 请求
	 * @return
	 */
	public Map<String, Object>  getModelShowInfoByTaskId(String reportId, IReportIn report){
		
		IViewModel ivm = getReportModelById(reportId);
		if (ivm == null) {
			return null;
		}

		return ivm.getModelShowInfo(report);
	}
	
 
	public static void main(String[] args){
		
     	String path = "F:\\workspace\\mspweb\\WebContent\\WEB-INF\\log4j.properties";
	 	//String path = "F:\\workspace\\mspweb\\WebContent\\WEB-INF\\log4j.properties";
		
	 	PropertyConfigurator.configureAndWatch(path);
		//AppConstant.useTestDb = true;
	 	AppConfigManager.loadDataFromXml("G:\\sqlxmll\\sqlxmltest\\trunk\\sqlXMLManager\\WebContent\\WEB-INF\\appConfig.xml");
//		String fileName="D:\\J2EE\\mspplus\\WebContent\\WEB-INF\\report\\reportModel.xml";
		String fileName="F:\\workspace\\mspweb\\WebContent\\WEB-INF\\report\\reportModel.xml";
		ReportModelManager rm = new ReportModelManager();
		//rm.addOneModelByFile(fileName);
		rm.loadModelByDir("F:\\sqlxml");
	//	rm.loadModelByDir("F:\\workspace\\mspweb\\WebContent\\WEB-INF\\report\\");
		String dsId = "taskinfo_inserttask";
		ReportRequest reportIn = new ReportRequest();
		//IDataSource ds = rm.getDSById(dsId);
		//IReportOut out = ds.getData(report );
		//out.output(System.out);
		
		//ReportModel rpm = rm.getReportModelById("operator_direction");
		IViewModel rpm = rm.getReportModelById("selectroles");
		//IViewModel rpm = rm.getReportModelById("doctor_sgsn_flow");
		//IViewModel rpm = rm.getReportModelById("comp_error_errorCodeAnalysis");
		
		if(rpm==null){
			logger.error("report model is null . ");
			return ;
	}
		//Map<String, Object> map1 = rpm.getModelShowInfo(reportIn);
		
		//logger.info(map1);
		 
		Map<String ,String> map = new HashMap();
		map.put("netWork","0");
		map.put("time","_hour");
		map.put("startDate", BaseUtil2.getCurrentTime("yyyy-MM-dd")+" 00:00:00");
		map.put("endDate", BaseUtil2.getCurrentTime("yyyy-MM-dd")+" 23:59:59");
		map.put("deviceType", "sgsn");
		map.put("groupName","sgsn");
		map.put("deviceName", "");
		map.put("deviceIp", "");
		map.put("orderKpi", "total");
		map.put("order", "desc");
		map.put("lac", "37274");
		map.put("ci", "12091");
		map.put("service_status", "2200");
		
		map.put("taskId", System.currentTimeMillis()+"");
		reportIn.map = map;
		 
		//rm.getDataListByDsId(dsId, map);
		
		JSONObject data = rpm.getReportData(reportIn);
		System.out.println(data);
		logger.debug(data);
		//rpm.doRequest(reportIn);
		
		
		
	}

	/**
	 * Description: 重新加载配置文件
	 * @author 李琦
	 * @return boolean 成功返回true 失败返回false
	 */
	public synchronized boolean reLoadConfig() {
		if (logger.isDebugEnabled()) {
			logger.debug("reLoad config be called !");
		}

		if (reportDir == null) {
			logger.error("reportDir is null . !");
			return false;
		}

		dsTable.clear();
		viewTable.clear();
		ObjectManager.clear();
		loadModelByDir(reportDir);

		return true;
	}


	
}
