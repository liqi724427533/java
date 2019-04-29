package com.theta.jar.report.ver1.dim1.model.comparator;

import java.util.Comparator;
import java.util.List;
import com.theta.jar.report.ver1.jiekou.data.IData;

public class IDataComparator<T> implements Comparator<List<IData>> {

	private int sortIndex=0;
	//0.desc 1=asc
	private int direction=1;
	
	public int compare(List<IData> o1, List<IData> o2) {
		
		IData data1 = o1.get(this.sortIndex);
		IData data2 = o2.get(this.sortIndex);
		
		int re = data1.comp(data2);		
		if(this.direction!=0){
			return -re;
		}else{
			return re;
		}
		
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		
		if("desc".equalsIgnoreCase(direction))
			this.direction = 0;
		else
			this.direction = 1;
	}
	
}
