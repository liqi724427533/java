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
	 * Description: ���һ���ļ�ģ��
	 * @author ����
	 * @param fileName Ҫ���ģ�͵��ļ�
	 */
	public void addOneModelByFile(String fileName);
	
	/**
	 * Description: ��ȡ��ѯģ��
	 * @author ����
	 * @param sourceId ģ����
	 * @return IDataSource
	 */
	public IDataSource getDataSourceById(String sourceId);
	
	/**
	 * Description: ��ȡ��ѯģ��
	 * @author ����
	 * @param id ģ����
	 * @return IReportOut
	 */
	public IReportOut getReportOutByDsId(String id);
	
	/**
	 *  Description: ִ�� ģ�� ��Ϣ ,ֱ��Ѱ������Դ���в�����
	 * @param dsId ģ����
	 * @param map ��ѯ��������
	 * @return IReportOut
	 */
	public IReportOut executeSqlByDsId(String dsId, Map<String, String> map);
	
	/**
	 * Description: ִ�� ģ�� ��Ϣ ,ֱ��Ѱ������Դ���в�����
	 * @param dsId
	 * @param reportIn
	 * @return
	 */
	public IReportOut executeSqlByDsId(String dsId, ReportRequest reportIn);
	
	/**
	 * Description: ����ģ������ȡ����
	 * @param idģ����
	 * @return ��װ�������
	 */
	public Map<Object, List<IData>> getDataMapByDsId(String id);
	
	public Map<Object, List<IData>> getDataMapByDsId(String id,Map<String,String> map);
	
	public List<List<IData>> getDataListByDsId(String id);
	
	/**
	 * ����dsId, �������� ��ȡ ����
	 * @param id
	 * @param map
	 * @return
	 */
	public List<List<IData>> getDataListByDsId(String dsId, Map<String, String> map);
	
	public void loadFileNames(List<String> fileList, File file);
	
	/**
	 * Description: ����Ŀ¼ �� ���������ļ�
	 * @author ����
	 * @param filePath �ļ�·��
	 */
	public void loadModelByDir(String filePath) ;
	
	/**
	 * Description: ͨ���ļ��б���� ģ�� 
	 * @author ����
	 * @param fileList �ļ��б�
	 */
	public void loadModel(List<String> fileList);
	
	/**
	 * Description: ���� dsId�������õ�������Դ ��
	 * @param dsId ģ����
	 * @return IDataSource
	 */
	public IDataSource getDSById(String dsId);
	
	/**
	 * Description: ���� viewId���� ����ģ�� 
	 * @param viewId ģ����
	 * @return IViewModel
	 */
	public IViewModel getReportModelById(String viewId);
	
	/**
	 * Description: ȡ��ģ����ʾ��Ϣ 
	 * @param reportId ģ����
	 * @param report ����
	 * @return
	 */
	public Map<String, Object>  getModelShowInfoByTaskId(String reportId, IReportIn report);
	
	/**
	 * Description: ���¼��������ļ�
	 * @author ����
	 * @return boolean �ɹ�����true ʧ�ܷ���false
	 */
	public boolean reLoadConfig();
}
