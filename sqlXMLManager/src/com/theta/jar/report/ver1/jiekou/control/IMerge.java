package com.theta.jar.report.ver1.jiekou.control;


import java.util.List;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;


/**
 * �ϲ������������á� ���� 2G�� 3G ���ű� ���ݺϲ�  .
 * @author Administrator
 *
 */
public interface IMerge {
	
	/**
	 * ��������Դ �ṹ��ͬ �ĺϲ� ��
	 * @param data1
	 * @param data2
	 * @param report
	 * @return
	 */
	public IReportOut  sameMerge(IReportOut data1, IReportOut data2, IReportIn report );
	
	/**
	 * ��������Դ �ṹ��ͬ �ϲ� ��
	 * @param data1
	 * @param data2
	 * @param report
	 * @return
	 */
	public IReportOut  groupMerge(IReportOut data, IReportOut groupData, List<String> dataNameList,
			IReportIn report );
	
	/**
	 *ͬ����,��ͬ�����ݺϲ�
	 */
	public IReportOut fieldMerge(IReportOut data, IReportOut groupData, List<String> dataGroupNameList,
			IReportIn report) ;
	
}
