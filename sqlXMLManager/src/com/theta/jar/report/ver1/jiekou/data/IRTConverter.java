package com.theta.jar.report.ver1.jiekou.data;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public interface IRTConverter {

	/**
	 * ��ʼ��ת����,����ʵ��������á�
	 * @param param
	 * @return
	 */
	public boolean init(String param);
	/**
	 * ������¼ת�� �����ʽ 
	 * idata:��ǰ��Ԫ�� IData����
	 * @param idata
	 * @param para
	 * @return
	 */
	public  Object convert(VelocityContext context, VelocityEngine engine,IData idata, String para);
	
	/**
	 * ����velocity ģ�� ��� �����ֶ� ��
	 * @param context
	 * @param engine
	 * @param templet
	 * @return
	 */
	public String convert(VelocityContext context, VelocityEngine engine, String templet);
}
