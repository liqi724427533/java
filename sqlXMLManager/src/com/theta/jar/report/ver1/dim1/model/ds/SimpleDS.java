package com.theta.jar.report.ver1.dim1.model.ds;

import java.sql.Connection;
import org.apache.log4j.Logger;
import org.dom4j.Node;

import com.theta.jar.jiekou.IDBListManager;
import com.theta.jar.report.myutil.SqlUtil;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.ds.info.SqlInfo;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.ds.IDataSource;

public class SimpleDS extends BaseDS {

	private static final Logger logger = Logger.getLogger(SimpleDS.class);

	/**
	 * 接收请求串，处理后返回。
	 */
	public IReportOut getData(IReportIn reportIn) {
		if (this.onQuery == null) {
			logger.error("SimpleDS not init query util .");
			return null;
		}
		int queryType = 0;
		boolean multiDb = false;
		if (this.sqlInfo != null) {
			multiDb = this.sqlInfo.multidb;
			reportIn.setAutoPage(this.sqlInfo.isAutoPage());
			reportIn.setSql(this.sqlInfo.getRunSql(reportIn.getRequestMap()));
			reportIn.setAutoQueryTotal(this.sqlInfo.autoQueryTotal1);
			queryType = this.sqlInfo.getQueryType();
		}

		if (tableName != null && !"".equals(tableName) && tableName.length() > 2) {
			reportIn.setTableName(tableName, multiDb);
		} else {
			String sql = reportIn.getSql();
			String sqltablename = SqlUtil.getTableNameFromSql(sql);
			reportIn.setTableName(sqltablename, multiDb);
		}

		reportIn.setGroupNameList(this.groupNameList);
		/**
		 * result data .
		 */
		IReportOut out = null;

		if (logger.isDebugEnabled()) {
			logger.debug("queryType:" + queryType + "; sql:" + reportIn.getSql());
		}
		//List<Integer> dsList = reportIn.getDbIndexList();

		int dsLen = 1;
	/*	if (dsList != null && dsList.size() > 1) {
			dsLen = dsList.size();
		}*/
		Connection conn = null;
		int dbIndex = 0;

		if (dsLen == 1) {
			try {
				dbIndex = 0;
			/*	if (dsList != null) {
					dbIndex = dsList.get(0);
				}*/
				if (reportIn.needSetConnect(dbIndex)) {
					//李琦原
					IDBListManager  idbListManager = (IDBListManager)ObjectManager.getSingleObjectInstance(ObjectManager.DBListManager); 
					conn = idbListManager.getDbConnection(dbIndex);
					reportIn.setConnection(conn, dbIndex);
				}

			} catch (NumberFormatException e) {
				logger.error("number format exeception !", e);
			}
		}

		if (queryType > 0) {
			out = this.onQuery.update(reportIn);
		} else {
			if (dsLen == 1) {
				out = this.onQuery.query(reportIn);
			} else if (this.multidb && dsLen != 1) {
				out = this.onQuery.multiThreadQuery(reportIn);
			}
		}
		if (out == null) {
			logger.warn("find no record !" + reportIn.toString());
			return null;
		}
		return out;
	}

	/**
	 * <data id="RT_WIRELESS_2G" tableName="RT_WIRELESS_2G" desc="无线小区质量动态表"
	 * type="2"> <sql> <![CDATA[SELECT lac, ci,Sum(uplink_bytes) as uplink_bytes
	 * , Sum(Attach_Number) AS Attach_Number, Sum(Attach_Success) AS
	 * Attach_Success, Sum(Attach_Delay) AS Attach_Delay FROM
	 * RT_WIRELESS_2G_HOUR WHERE report_sec>=To_Date('2011-03-4 00:00:00',
	 * 'YYYY-MM-DD HH24:MI:SS') AND report_sec<To_Date('2011-03-5 00:00:00',
	 * 'YYYY-MM-DD HH24:MI:SS') GROUP BY lac,ci ]]> </sql> <key>
	 * <![CDATA[lac,ci]]></key> </data>
	 */
	public boolean load(Node node) {

		/**
		 * 调用父类加载公用 指标。
		 */
		if (!super.load(node)) {
			return false;
		}

		return true;
	}

	public SqlInfo getSqlInfo() {
		return sqlInfo;
	}

	public void setSqlInfo(SqlInfo sqlInfo) {
		this.sqlInfo = sqlInfo;
	}

	@Override
	public String toString() {
		return "DSInfo [tableName=" + tableName + ", dsId=" + dsId + ", sqlInfo=" + sqlInfo + "]";
	}

	public int getDSType() {

		return IDataSource.TYPE_SIMPLE;
	}

}
