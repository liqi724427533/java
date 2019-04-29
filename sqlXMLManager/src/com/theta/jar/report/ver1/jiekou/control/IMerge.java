package com.theta.jar.report.ver1.jiekou.control;


import java.util.List;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;


/**
 * 合并两个表数据用。 比如 2G和 3G 两张表 数据合并  .
 * @author Administrator
 *
 */
public interface IMerge {
	
	/**
	 * 两个数据源 结构相同 的合并 ；
	 * @param data1
	 * @param data2
	 * @param report
	 * @return
	 */
	public IReportOut  sameMerge(IReportOut data1, IReportOut data2, IReportIn report );
	
	/**
	 * 两个数据源 结构不同 合并 ；
	 * @param data1
	 * @param data2
	 * @param report
	 * @return
	 */
	public IReportOut  groupMerge(IReportOut data, IReportOut groupData, List<String> dataNameList,
			IReportIn report );
	
	/**
	 *同类型,不同列数据合并
	 */
	public IReportOut fieldMerge(IReportOut data, IReportOut groupData, List<String> dataGroupNameList,
			IReportIn report) ;
	
}
