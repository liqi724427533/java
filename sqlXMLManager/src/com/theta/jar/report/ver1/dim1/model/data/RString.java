package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

public class RString implements IData {

	private String value;
	 
	public RString() {
		 
	}

	public   RString(String str){
		this.value = str;
	}
	
	public void add(IData obj) {
	
	}
	
	public void concat(IData obj) {
		
		RString rs  = (RString)obj;
		if(rs==null){
			return ;
		}
		
		String temp = rs.getValue();
		 
		if(temp!=null){
			
			if(value==null){
				this.value = temp;
			}else{
				this.value +=temp;
			}
		}
	}

	public String getValue(){
		
		return value;
	}
	
	public void setValue(String str){
		
		 this.value = str;
	}
	
	
	public String toStr() {
		
		if(value==null){
			return "null";
		}
		return value+"";
	}

	@Override
	public String toString() {
		return "RString [str=" + value + "]";
	}

	public Object getPrimitiveValue() {
		 
		return this.value;
	}
	public double divide(Object obj) {
		 
		return 0;
		
	}

	public Object getFormatPrimitiveValue() {
		 
		return this.value;
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
		
	     return this.value.compareTo((String)obj);
	}
	
	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
}
