package com.theta.jar.report.ver1.jiekou.ds;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;

import com.theta.jar.report.ver1.jiekou.control.IShowOut;
import com.theta.jar.report.ver1.jiekou.control.OnQuery;

public interface IDataSource {

	/**
	 * ��̬����Դ������һ�κ��������仯������ ���¼��� ��
	 */
	public final  static int TYPE_STATIC = 1;
	public final  static int TYPE_SIMPLE = 2;
	public final  static int TYPE_Multiple = 3;
	
	
	public IReportOut reLoad(IReportIn report);
	public OnQuery getOnQuery();
	public void setOnQuery(OnQuery query);
	
	public IShowOut getShowOut();
	public void setShowOut(IShowOut showOut);
	
	
	public IReportOut getData(IReportIn report);
	
	/**
	 * ȡ������Դ���� ��
	 * @return
	 */
	public int getDSType();
}
