package  com.theta.jar.report.ver1.jiekou.control;
 
import java.sql.Connection;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;

 
public interface OnQuery {

	/**
	 * Description: 多数据库查询
	 * @author 李琦
	 * @param reportIn
	 * @return
	 */
	public  IReportOut  multiThreadQuery(IReportIn reportIn) ;
	/**
	 * Description: 普通查询操作
	 * @author 李琦
	 * @param reportIn
	 * @return
	 */
	public IReportOut  query(IReportIn reportIn);
 
	/**
	 *Description:  insert, update 操作
	 *@author 李琦
	 * @param reportIn
	 * @return
	 */
	public IReportOut  update(IReportIn reportIn);
	
	/**
	 * Description: 获取总条数
	 * @author 李琦
	 * @param conn Connection
	 * @param sql sql语句
	 * @param i
	 * @return int总条数
	 */
	public int getTotalNum(Connection conn, String sql, int i);

}
