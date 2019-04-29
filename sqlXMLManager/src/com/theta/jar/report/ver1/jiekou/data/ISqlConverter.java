package com.theta.jar.report.ver1.jiekou.data;

import java.util.Map;

public interface ISqlConverter {
	
	/**
	 * 取得运行 语句  SQL
	 * @param requestMap
	 * @return
	 */
	public String getRunSql(String sqlTemplate, Map<String, String> requestMap);
		
}
