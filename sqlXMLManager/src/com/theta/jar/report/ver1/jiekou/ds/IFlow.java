package com.theta.jar.report.ver1.jiekou.ds;

import java.util.List;

/**
 *  ��������˳����� 
 * @author Administrator
 *
 */
public interface IFlow {

	 
	/**
	 * ��˳�򷵻�DataSource �б�.
	 * @param reportId
	 * @return
	 */
	public List<IDataSource> getDataSourceList();
 
}
