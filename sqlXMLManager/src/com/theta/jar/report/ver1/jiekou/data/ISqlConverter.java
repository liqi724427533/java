package com.theta.jar.report.ver1.jiekou.data;

import java.util.Map;

public interface ISqlConverter {
	
	/**
	 * ȡ������ ���  SQL
	 * @param requestMap
	 * @return
	 */
	public String getRunSql(String sqlTemplate, Map<String, String> requestMap);
		
}
