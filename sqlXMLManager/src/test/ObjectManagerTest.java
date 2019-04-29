package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.theta.jar.report.ver1.dim1.model.ObjectManager;

public class ObjectManagerTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String sqlQueryKey = "com.theta.jar.report.util.DButil1";
		IDButil1 idButil1=(IDButil1) ObjectManager.createObjectInstance(sqlQueryKey);
		Connection conn = null;
		conn = idButil1.getconnection();
		Statement stm = null;
		stm = conn.createStatement();
		String sql = "SELECT * FROM syu_role_list";
		ResultSet rs = stm.executeQuery(sql);
		while (rs.next()) { // 判断是否还有下一个数据

			// 根据字段名获取相应的值
			int name = rs.getInt("role_id");
			String age = rs.getString("name");
			String sex = rs.getString("description");
			String address = rs.getString("creator");
			int depart = rs.getInt("isenabled");

			// 输出查到的记录的各个字段的值
			System.out.println(name + " " + age + " " + sex + " " + address
					+ " " + depart);

		}
		conn.close();
	}
}
