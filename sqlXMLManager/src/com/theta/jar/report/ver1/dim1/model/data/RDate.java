package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;
import java.util.Date;

 
import com.theta.jar.report.myutil.BaseUtil2;
import com.theta.jar.report.ver1.jiekou.data.IData;

public class RDate implements IData{

	private Date value;
	
	public RDate(Date date) {
 
		this.value = date;
	}

	public RDate() {
 
	}

	public void add(IData obj) {
		 
	}

	public void concat(IData obj) {
 
	}

	public Date getValue(){
		
		return value;
	}
	
	public void setValue(Date date1){
		this.value = date1;
	}

	public String toStr() {
		
		if(value==null){
			return "";
		}
		
		//return value.toLocaleString();
		return BaseUtil2.getMyTime(value, "yyyy-MM-dd HH:mm:ss");
		 
	}

	@Override
	public String toString() {
		return "RDate [date=" + value + "]";
	}

	public Object getPrimitiveValue() {
		 
		return this.value;
	
	}

	public double divide(Object obj) {
		
		return 0;
	}

	public Object getFormatPrimitiveValue() {
		
		return BaseUtil2.getMyTime(value, "yyyy-MM-dd HH:mm:ss");
 	
	}

	public Long getLongPrimitiveValue() {
		 
		return null;
	}

	public int comp(IData data2) {
	    
		Object obj = data2.getPrimitiveValue();
		if(obj==null){
			return 1;
		}
		if(this.value==null){
			return -1;
		}
		
		return this.value.before((Date)obj)?1:-1;
		 
	}

	public BigInteger getBigValue() {
		
		return null;
	}
	
	
}
