package com.theta.jar.report.ver1.jiekou;

import net.sf.json.JSONObject;

/**
 * 输出数据 到页面 接口 
 * @author Administrator
 *
 */
public interface IGridData {

	/**
	 * 取得JSON格式输出数据到页面
	 * @return
	 */
	public JSONObject getGridData();
}
