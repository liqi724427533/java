package com.theta.jar.report.ver1.jiekou;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import com.theta.jar.report.ver1.dim1.model.data.FieldInfo;
import com.theta.jar.report.ver1.jiekou.data.IData;

public interface IReportOut {
	
 
	public static int  Type_List = 1;
	public static int  Type_Hash = 2;
	
	/**
	 * 取得存储数据 格式 。
	 * Type_List  链表
	 * Type_Hash  Hash表
	 * @return
	 */
	public int getDataType();
	
	/**
	 * 数据源个字段信息 ;
	 * @return
	 */
	public List<FieldInfo> getFieldList();
	public void setFiledList(List<FieldInfo> fieldList);
	
	/**
	 * 字段个数 。
	 * @param colCount
	 */
	public int getColumNumber();
	
	/**
	 * 记录数
	 * @param recordNumber
	 */
	public int 	getRecordNumber();	
	
	/**
	 * 返回查询到最大记录数;
	 * @return
	 */
	public int getTotalNumber();
	
	/**
	 * 总记录数是否正确 
	 * @return
	 */
	public boolean bRightTotalNumber();
	/**
	 *  数据源 数据信息 以列表信息返回
	 * @return
	 */
	public List<List<IData>> getDataList();
	public void setDataList(List<List<IData>> list);
	
	/**
	 *  数据源 数据信息 以 hash表形式返回 
	 * @return
	 */
	public Map<Object, List<IData>>  getDataMap();
	public void setDataMap(Map<Object, List<IData>> map);
	
	/**
	 *  取得数据是否成功。 
	 * @return
	 */
	public boolean getSuccess();
	public void setSuccess(boolean b);
	
	/**
	 * 提示信息 。
	 * @return 
	 */
	public String getTipInfo();
	public void setTipInfo(String message);

	/**
	 * 取得当前存储 数据的分组 字段列表 ; 分组字段用于建立索引时使用 
	 * @return
	 */
	public List<String> getGroupNameList();
	public void			setGroupNameList(List<String> groupNameList);
	
	/**
	 * 根据filedName值取得该字段在结果集中的位置.
	 * @param fieldName
	 * @return
	 */
	public Integer getIndexByFieldName(String fieldName);
	/**
	 * 取得 分组 字段在记录所处的索引  列表 。
	 * @return
	 */
	public List<Integer> getGroupIndexList();
 
	 
	/**
	 * 根据提供字段名列表 返回  在记录中对应的 位置 索引列表 。
	 * @param fieldNames
	 * @return
	 */
	public List<Integer> getIndexListByName(List<String> nameList);
	
	
	public void output(PrintStream os);

	/**
	 * 取得记录开始的序号
	 */
	public int getRecordStart();
}
