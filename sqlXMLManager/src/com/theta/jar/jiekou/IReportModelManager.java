package com.theta.jar.jiekou;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.theta.jar.report.ver1.dim1.model.ReportRequest;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.data.IData;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;
import com.theta.jar.report.ver1.jiekou.view.IViewModel;

public interface IReportModelManager {

	public String reportDir = "";

	/**
	 * Description: 添加一个文件模型
	 * @author 李琦
	 * @param fileName 要添加模型的文件
	 */
	public void addOneModelByFile(String fileName);
	
	/**
	 * Description: 获取查询模型
	 * @author 李琦
	 * @param sourceId 模型名
	 * @return IDataSource
	 */
	public IDataSource getDataSourceById(String sourceId);
	
	/**
	 * Description: 获取查询模型
	 * @author 李琦
	 * @param id 模型名
	 * @return IReportOut
	 */
	public IReportOut getReportOutByDsId(String id);
	
	/**
	 *  Description: 执行 模型 信息 ,直接寻找数据源进行操作。
	 * @param dsId 模型名
	 * @param map 查询参数条件
	 * @return IReportOut
	 */
	public IReportOut executeSqlByDsId(String dsId, Map<String, String> map);
	
	/**
	 * Description: 执行 模型 信息 ,直接寻找数据源进行操作。
	 * @param dsId
	 * @param reportIn
	 * @return
	 */
	public IReportOut executeSqlByDsId(String dsId, ReportRequest reportIn);
	
	/**
	 * Description: 根据模块名获取数据
	 * @param id模块名
	 * @return 包装后的数据
	 */
	public Map<Object, List<IData>> getDataMapByDsId(String id);
	
	public Map<Object, List<IData>> getDataMapByDsId(String id,Map<String,String> map);
	
	public List<List<IData>> getDataListByDsId(String id);
	
	/**
	 * 根据dsId, 所给参数 提取 数据
	 * @param id
	 * @param map
	 * @return
	 */
	public List<List<IData>> getDataListByDsId(String dsId, Map<String, String> map);
	
	public void loadFileNames(List<String> fileList, File file);
	
	/**
	 * Description: 加载目录 下 所有配置文件
	 * @author 李琦
	 * @param filePath 文件路径
	 */
	public void loadModelByDir(String filePath) ;
	
	/**
	 * Description: 通过文件列表添加 模型 
	 * @author 李琦
	 * @param fileList 文件列表
	 */
	public void loadModel(List<String> fileList);
	
	/**
	 * Description: 根据 dsId返回引用到的数据源 。
	 * @param dsId 模型名
	 * @return IDataSource
	 */
	public IDataSource getDSById(String dsId);
	
	/**
	 * Description: 根据 viewId返回 报表模型 
	 * @param viewId 模型名
	 * @return IViewModel
	 */
	public IViewModel getReportModelById(String viewId);
	
	/**
	 * Description: 取得模型显示信息 
	 * @param reportId 模型名
	 * @param report 请求
	 * @return
	 */
	public Map<String, Object>  getModelShowInfoByTaskId(String reportId, IReportIn report);
	
	/**
	 * Description: 重新加载配置文件
	 * @author 李琦
	 * @return boolean 成功返回true 失败返回false
	 */
	public boolean reLoadConfig();
}
