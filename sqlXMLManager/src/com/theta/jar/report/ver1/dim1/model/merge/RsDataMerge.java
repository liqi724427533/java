package com.theta.jar.report.ver1.dim1.model.merge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

 
import com.theta.jar.report.ver1.dim1.model.data.FieldInfo;
import com.theta.jar.report.ver1.dim1.model.data.RNull;
import com.theta.jar.report.ver1.dim1.model.data.RsData;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.control.IMerge;
import com.theta.jar.report.ver1.jiekou.data.IData;

public class RsDataMerge implements IMerge{

	private static final Logger logger = Logger.getLogger(RsDataMerge.class);
	
	
	public RsDataMerge() {
		
	}

	/**
	 * 1. ���кϲ�����������Դ ��¼�ֶ�һ�� �� ֻ��������һ�� ��
	 */
	public IReportOut sameMerge(IReportOut data1, IReportOut data2 , IReportIn report) {
	
	
		if(data1==null){
			return data2;
		}
		
		if(data2==null){
			return data1;
		}
		
		Map<Object, List<IData>> map1 = data1.getDataMap();
		Map<Object, List<IData>> map2 = data2.getDataMap();
	
		
		
		Map<Object, List<IData>> map3  = new HashMap<Object, List<IData>>();
 
		Iterator<Entry<Object, List<IData>>> it = null;

		if(map1.size()> map2.size()){
			map3.putAll(map1);
			it = map2.entrySet().iterator();
		}else{
			map3.putAll(map2);
			it = map1.entrySet().iterator();
		}

		
		int grouIndexCount = data1.getGroupIndexList().size();
	
		
		while(it.hasNext()){
			
			Entry<Object, List<IData>> entry = it.next();
			
			Object key = entry.getKey();
			List<IData>   obj = entry.getValue();
			
			List<IData> temp = map3.get(key);
			
			if(temp==null){
				map3.put(entry.getKey(), entry.getValue());
			}else{
				
				//�����ֶη���ǰ�� ��
				for(int i=grouIndexCount; i< data1.getColumNumber(); i++){
					//�ϲ������
					
					 temp.get(i).add(obj.get(i));
					//temp.add(obj);
				}
			}
	 
		}
		
		IReportOut iOut = new RsData();
		iOut.setFiledList(data1.getFieldList());
		iOut.setGroupNameList(data1.getGroupNameList());
		
		iOut.setDataMap(map3);
		
		return iOut;
	}

	/**
	 *ͬ����,��ͬ�����ݺϲ�
	 */
	public IReportOut fieldMerge(IReportOut data, IReportOut groupData, List<String> dataGroupNameList,
			IReportIn report) {
		 
		if(data==null){
			return null;
		}
		
		if(groupData==null){
			return data;
		}
		
		//--------ȡ�� newFieldIndex------------ �ϲ�ԭ��¼ �ֶ� �ںϲ���¼�е�λ�� �б� ��
		List<Integer> newFieldIndex = new ArrayList<Integer>();
 
		/**
		 * �ºϳɼ�¼�ֶ��б� 
		 */
		List<FieldInfo> newFieldList = new ArrayList<FieldInfo>();
	
		//---------------------------------------------
		/**
		 * ȡ��������  �ֶ���Ϣ  ��
		 */
		for(int i = 0; i< data.getFieldList().size() ; i++){
			newFieldList.add(data.getFieldList().get(i));
		}
		
		
		//---------------groupData--------------------
		List<FieldInfo> gf = groupData.getFieldList();
		for(int i=0; i< gf.size(); i++){
			
			FieldInfo fi = gf.get(i);
			boolean find = false;
			for(int j=0; j<data.getFieldList().size(); j++){
					
					if(data.getFieldList().get(j).getName().equals(fi.getName())){
							newFieldIndex.add(j);	
							find = true;
					    	break;
					}
			}
			if(!find){
				
				newFieldList.add(fi);
				newFieldIndex.add((newFieldList.size()-1));
			}
		}
   
		///----------------------
		if(newFieldIndex==null|| newFieldIndex.size()<1){
	 
			return data;
		}
 
		Map<Object, List<IData>> map1 = data.getDataMap();
		Map<Object, List<IData>> map2 = groupData.getDataMap();
		
	 
		Map<Object, List<IData>> map3  = new HashMap<Object, List<IData>>();
		
		Set<Entry<Object, List<IData>>> set = map1.entrySet();
		Iterator<Entry<Object, List<IData>>> it = set.iterator();
		Object key = null;
	 
		List<IData>   value1 = null;
		List<IData>   value2 = null;
		List<IData>   value3 = null;
		
		List<Integer> groupIndex = groupData.getGroupIndexList();
		 
		if(groupIndex==null||groupIndex.size()<1){
			return data;
		}
 
		 List<IData> record = null;
		
		while(it.hasNext()){
			
			Entry<Object, List<IData>> entry = it.next();
			
			key =  entry.getKey();
			value1 = entry.getValue();
			//�ºϳ�Hash�����Ƿ� �Ѱ����˼�¼ ;
			value2 = map2.get(key);
			
			record = new ArrayList<IData>();
			
			/**
			 * ȡ�� ��ȥ�������¼ ��
			 */
			for(int i=0; i< value1.size(); i++){
				
				record.add(value1.get(i));
			}
			

			//�Ѿ������˷��� ��¼ ;��Ҫ�ϲ� ��¼ ����record�� value3�ϲ��� �� 
			int size = record.size();
			for(int i=0; i<newFieldIndex.size(); i++){
				
				int index = newFieldIndex.get(i);
				if(index<size){
					continue;
				}
				if(value2==null){
					
					record.add(new RNull());
					continue;
				}else{
					record.add(value2.get(i));
				}
			}
			
			map3.put(key, record);
		}
		
	

		set = map2.entrySet();
		it = set.iterator();

		int tempSize = data.getFieldList().size();
		
		List<List<IData>> tempList = new ArrayList<List<IData>>();
		while(it.hasNext()){
			
			Entry<Object, List<IData>> entry = it.next();
			key = entry.getKey();
			if(map3.get(key)!=null){
				continue;
			}
			
			value2 = entry.getValue();
			record = new ArrayList<IData>();	
			
			for(int i=0; i < tempSize; i++){ 
				record.add(new RNull());
			}
	 
			for(int i=0; i<newFieldIndex.size(); i++){
				
				if(newFieldIndex.get(i)<tempSize){
					record.set(newFieldIndex.get(i), value2.get(i));
				}else{
					record.add(value2.get(i));		
				}
				 
			}	 
			map3.put(key, record);
		}
		

			
		//----------------------------------------------
		RsData rd = new RsData();
		rd.setDataMap(map3);
		
		int total = data.getTotalNumber();
		if(total<groupData.getTotalNumber()){
			total = groupData.getTotalNumber();
		}
		
		if(total<map3.size()){
			total = map3.size();
		}
		rd.setFiledList(newFieldList);
		rd.setGroupNameList(dataGroupNameList);
		rd.setTotalNumber(total);
	 	
		return rd;
	}
	
	/**
	 * ��̬��  ��  ��̬����� ��
	 */
	public IReportOut groupMerge(IReportOut data, IReportOut groupData, List<String> dataGroupNameList,
			IReportIn report) {
		 
		if(data==null){
			return null;
		}
		
		if(groupData==null){
			return data;
		}
		
		//--------ȡ�� newGroupIndex------------ �ϲ����¼�¼ ���� �ֶ� ��ԭ��¼�е�λ�� �б� ��
		
		List<Integer> newGroupIndex = new ArrayList<Integer>();
		 
		///----------------------
	
		if(newGroupIndex==null|| newGroupIndex.size()<1){
			
			logger.error(" group index is null !");
			return null;
		}

		
		Map<Object, List<IData>> map1 = data.getDataMap();
		Map<Object, List<IData>> map2 = groupData.getDataMap();
		
	 
		Map<Object, List<IData>> map3  = new HashMap<Object, List<IData>>();
		
		Set<Entry<Object, List<IData>>> set = map1.entrySet();
		Iterator<Entry<Object, List<IData>>> it = set.iterator();
		Object key = null;
		String groupKey = null;
		List<IData>   value1 = null;
		List<IData>   value2 = null;
		List<IData>   value3 = null;
		
		List<Integer> groupIndex = groupData.getGroupIndexList();
		 
		if(groupIndex==null||groupIndex.size()<1){
			return data;
		}
		
		int keySize = data.getGroupIndexList().size();
		int newGroupSize = newGroupIndex.size();
		List<IData> record = null;
		
		while(it.hasNext()){
			
			Entry<Object, List<IData>> entry = it.next();
			
			key =  entry.getKey();
			value1 = entry.getValue();
			
			value2 = map2.get(key);
			
			record = new ArrayList<IData>();
			
			//��ӳ������Ҳ��� ��Ӧ key, ex: lac,ci ��Ӧ��Ϣ ��
			if(value2==null){
				groupKey = "null";
				for(int i=0; i<newGroupSize; i++){	
					record.add(new RNull());
				}
				
			}else{
				
				groupKey = "";
				//�����ֶ���ӵ�ĩβ ��
				for(int i=0; i<newGroupSize; i++){	
					record.add(value2.get(newGroupIndex.get(i)));
					groupKey +="/"+value2.get(newGroupIndex.get(i)).toStr();
				}
			}
			
			/**
			 * ȡ�� ��ȥ�������¼ ��
			 */
			for(int i=keySize; i< value1.size(); i++){
				
				record.add(value1.get(i));
			}
			
			//�ºϳ�Hash�����Ƿ� �Ѱ����˼�¼ ;
			value3 = map3.get(groupKey);
			
			//����ؼ�¼ ;
			if(value3==null){
				map3.put(groupKey, record);
			}else{
			
				//�Ѿ������˷��� ��¼ ;��Ҫ�ϲ� ��¼ ����record�� value3�ϲ��� �� 
				for(int i = newGroupSize; i < record.size(); i++){
					
					value3.get(i).add(record.get(i));
				}
			}
			
		}
		
		
		/**
		 * �ºϳɼ�¼�ֶ��б� 
		 */
		List<FieldInfo> newFieldList = new ArrayList<FieldInfo>();
	
		//---------------groupData--------------------
		List<FieldInfo> gf = groupData.getFieldList();
		for(int i=0; i< newGroupIndex.size(); i++){
			
			 newFieldList.add(gf.get(newGroupIndex.get(i)));
		}
  
		//---------------------------------------------
		/**
		 * ȡ��������  �ֶ���Ϣ  ��
		 */
		for(int i = keySize; i< data.getFieldList().size() ; i++){
			
			newFieldList.add(data.getFieldList().get(i));
		}
			
		//----------------------------------------------
		RsData rd = new RsData();
		rd.setDataMap(map3);
		
		rd.setFiledList(newFieldList);
		rd.setGroupNameList(dataGroupNameList);

		return rd;
	}

}
