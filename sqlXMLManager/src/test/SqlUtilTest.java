package test;

import com.theta.jar.report.myutil.SqlUtil;

public class SqlUtilTest {

	public static void main(String[] args) {
		System.out.println(SqlUtil.getTableNameFromSql("select * from test"));
	}
}
