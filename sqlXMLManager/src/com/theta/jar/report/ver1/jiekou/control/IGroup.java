package com.theta.jar.report.ver1.jiekou.control;

import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;
import com.theta.jar.report.ver1.jiekou.view.IViewModel;

/**
 * �ϲ������ֶ� 
 * @author Administrator
 *
 */
public interface IGroup {
	
	public IReportOut  group(IDataSource data1, IDataSource data2, IViewModel view );
	
}
