package com.theta.jar.report.ver1.dim1.model.data;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

 
import com.theta.jar.report.ver1.dim1.model.comparator.IDataComparator;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.data.IData;

/**
 *
 * �Զ�������;
 * ��¼�����ݿ��ѯ���ļ�¼ ;
 * 
 * @author Administrator
 *
 */
public class RsData implements IReportOut{
	
	private static final Logger logger = Logger.getLogger(RsData.class);
	 
	/**
	 * record start 
	 */
	private int 	  recordStart = 0;

	
	private  List<FieldInfo> fieldList  = null;
	private  List<String> 	 groupNameList = null;
	private  List<Integer>   groupIndexList = null;
	private  List<List<IData>>  	 dataList = null;
	private  Map<Object, List<IData>> dataMap = null;
	
	//�Ƿ�ɹ� 
	private boolean success=true;
	private String tipInfo=null;
	
	/**
	 * �Ƿ�ȷ���ܼ�¼����ȷ
	 * 
	 */
	private boolean rightTotalNumber = false;
	/**
	 * �ܼ�¼��
	 */
	private int     totalNumber = 0;
	private int  dataType = IReportOut.Type_List;
	
	public RsData() {
		super();
		 
	}

	public int getDataType() {
		
		return dataType;
	}

	public List<FieldInfo> getFieldList() {
		 
		return fieldList;
	}

	public void setFiledList(List<FieldInfo> fieldList) {
		 
		this.fieldList = fieldList;
	}


	public int getColumNumber() {
		
		if(this.fieldList==null){
			return 0;
		}
		return this.fieldList.size();
	}
	

	public List<List<IData>> getDataList() {
		 
	 
		if(this.dataList!=null){
			return this.dataList;
		}
		if(this.dataMap == null){
			
			return null;
		}
	
		List<List<IData>> tempList = new ArrayList<List<IData>>(this.dataMap.size());	 
		Set<Entry<Object, List<IData>>> set = this.dataMap.entrySet();	
		Iterator<Entry<Object, List<IData>>> it = set.iterator();
		 
		while(it.hasNext()){
			tempList.add(it.next().getValue());
		}
		
		Comparator<List<IData>> cop = new IDataComparator<List<IData>>();
		Collections.sort(tempList, cop);
		this.dataList = tempList;
		return this.dataList;
		
	}

	/**
	 * ���� �б����� Դ;
	 * 
	 */
	public void setDataList(List list) {
		 
		this.dataType = Type_List;
		this.dataList = list;
		this.dataMap = null;
	}

	public Map<Object, List<IData>> getDataMap() {
		 
		if(this.dataMap!=null){
			return this.dataMap;
		}
		if(this.dataList == null){
			return null;
		}
 
	 
		Map<Object, List<IData>> tempMap = new HashMap<Object, List<IData>>();
		List<IData> temp = null;
		List<Integer> tempList = this.getGroupIndexList();
		boolean keyLargeOne = tempList.size()>1?true:false;
		for(int i=0; i < this.dataList.size(); i++){
			
			temp = this.dataList.get(i);
			
			String key="";
			if(keyLargeOne){
					
					for(int j=0; j<tempList.size(); j++){
						
						key = key+"/"+temp.get(tempList.get(j)).toStr();
						tempMap.put(key, temp);
						//logger.debug("key:"+key);
						
					}
			}else{
	
				tempMap.put(temp.get(tempList.get(0)).getPrimitiveValue(), temp);
				//logger.debug("key:"+temp.get(tempList.get(0)).getPrimitiveValue());
				
			}
	
		}
		
		this.dataMap = tempMap;
		return this.dataMap;
	}

	public void setDataMap(Map<Object, List<IData>> dataMap) {
		 
		this.dataType =  Type_Hash;
		this.dataMap = dataMap;
		//this.dataList = null;
	}

	public boolean getSuccess() {

		return this.success;
	}

	public void setSuccess(boolean success) {
		 
		this.success = success;
	}

	public String getTipInfo() {

		return this.tipInfo;
	}

	public void setTipInfo(String message) {
		 
		this.tipInfo = message;
		
	}


	/**
	 * ��������Դ��Ŀ
	 */
	public int getRecordNumber() {
	 
		
		if(this.getDataType()==IReportOut.Type_List){
			if(this.dataList==null){
				return 0;
			}else{
				return this.dataList.size();		
			}
		}else{
			
			if(this.dataMap ==null){
				return 0;
			}else{
				return this.dataMap.size();
			}
		}
	 
	}


	/**
	 * ���� ���� �ֶ� �����б� ��
	 */
	public List<String> getGroupNameList() {
	
		return this.groupNameList;
		
	}
 
	/**
	 * ͨ�� groupName List �Լ� fieldList���� groupIndexList;
	 * Ĭ�Ϸ��ص�һ�� �ֶ���Ϊ ���������� 
	 */
	public List<Integer> getGroupIndexList() {
	
		if(this.groupIndexList!=null&&this.groupIndexList.size()>0){
			return this.groupIndexList;
		}

		this.groupIndexList = getIndexListByName(this.groupNameList);
		
		return this.groupIndexList;

	 
	}

	public void setGroupNameList(List<String> groupNameList) {
		
		this.groupNameList  = groupNameList;
		this.groupIndexList = null;
	}

	public Integer getIndexByFieldName(String fieldName){
		
		if(fieldName==null||this.getFieldList()==null){
			
			 return 0;
		}
		
	 
			for(int j=0; j<this.getFieldList().size(); j++){
				
				if(fieldName.equals(this.getFieldList().get(j).name)){
					
					return j;
				}
			}
	 
		
		return 0;
	}
	
	/**
	 * Ĭ�� ����  ���� ��һ��Ԫ�� ��
	 */
	public List<Integer> getIndexListByName(List<String> fieldNameList) {
		
		List<Integer> indexList = new ArrayList<Integer>();
	 
		if(fieldNameList==null||this.getFieldList()==null){
			
			indexList.add(0);
			
			return indexList;
		}
		//���� groupIndexList
		
		for(int i=0; i< fieldNameList.size(); i++){
			
			String temp = fieldNameList.get(i);
			
			boolean find = false;
			for(int j=0; j<this.getFieldList().size(); j++){
				
				if(temp.equals(this.getFieldList().get(j).name)){
					indexList.add(j);
					find = true;
					break;
				}
			}
			
			if(!find){
				indexList.add(-1);
			}
			
		}
		return indexList;
	
	}

  
	public void output(PrintStream os) {
		
		 List<List<IData>> list = this.getDataList();
		 os.println(this.fieldList.toString());
		 if(list==null){
			 return ;
		 }
		 
		 for(int i=0; i<list.size(); i++){
			 
			 os.println(list.get(i));
		 }
	}

	public int getTotalNumber() {
		 
		return this.totalNumber;
	}

	public boolean bRightTotalNumber() {
		 
		return this.rightTotalNumber;
	}

	/**
	 * ȡ�ü�¼��ʼ���
	 * @return
	 */
	public int getRecordStart() {
		return recordStart;
	}

	public void setRecordStart(int recordStart) {
		this.recordStart = recordStart;
	}

	public boolean isRightTotalNumber() {
		return rightTotalNumber;
	}

	public void setRightTotalNumber(boolean rightTotalNumber) {
		this.rightTotalNumber = rightTotalNumber;
	}

	
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	
	
}
