package  com.theta.jar.report.ver1.jiekou.control;
 
import java.sql.Connection;

import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;

 
public interface OnQuery {

	/**
	 * Description: �����ݿ��ѯ
	 * @author ����
	 * @param reportIn
	 * @return
	 */
	public  IReportOut  multiThreadQuery(IReportIn reportIn) ;
	/**
	 * Description: ��ͨ��ѯ����
	 * @author ����
	 * @param reportIn
	 * @return
	 */
	public IReportOut  query(IReportIn reportIn);
 
	/**
	 *Description:  insert, update ����
	 *@author ����
	 * @param reportIn
	 * @return
	 */
	public IReportOut  update(IReportIn reportIn);
	
	/**
	 * Description: ��ȡ������
	 * @author ����
	 * @param conn Connection
	 * @param sql sql���
	 * @param i
	 * @return int������
	 */
	public int getTotalNum(Connection conn, String sql, int i);

}
