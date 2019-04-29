package com.theta.jar.jiekou;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IDBListManager {

	
	public Connection getMainConnection(String tableName) throws SQLException;
	
	public List<Integer> getDbIndexList(String tableName, int flag, Map<String, String> map2, boolean multiDb);
	
	public Connection getDbConnection(int dbIndex) ;

	public List<Integer> getValueWithEnd(String msisdn_end);
}
