package com.theta.jar.report.ver1.jiekou.ds;

import java.util.List;

/**
 *  数据生成顺序控制 
 * @author Administrator
 *
 */
public interface IFlow {

	 
	/**
	 * 按顺序返回DataSource 列表.
	 * @param reportId
	 * @return
	 */
	public List<IDataSource> getDataSourceList();
 
}
