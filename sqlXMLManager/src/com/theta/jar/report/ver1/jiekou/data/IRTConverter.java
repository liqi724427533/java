package com.theta.jar.report.ver1.jiekou.data;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public interface IRTConverter {

	/**
	 * 初始化转换器,对象实例化后调用。
	 * @param param
	 * @return
	 */
	public boolean init(String param);
	/**
	 * 单条记录转换 输出格式 
	 * idata:当前列元素 IData类型
	 * @param idata
	 * @param para
	 * @return
	 */
	public  Object convert(VelocityContext context, VelocityEngine engine,IData idata, String para);
	
	/**
	 * 利用velocity 模板 输出 单个字段 。
	 * @param context
	 * @param engine
	 * @param templet
	 * @return
	 */
	public String convert(VelocityContext context, VelocityEngine engine, String templet);
}
