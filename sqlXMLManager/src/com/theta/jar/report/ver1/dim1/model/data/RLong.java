package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

public class RLong implements IData{

	private Long value = (long) 0;
	
	
	public RLong() {
		super();
	}

	
	public RLong(Long v) {
		super();
		this.value = v;
	}


	public void add(IData obj) {
		
		RLong temp = (RLong)obj;
		if(temp==null){
			return ;
		}
		
		if(temp.getValue()!=null){
			
			this.value += temp.getValue();
		}
 
	}

	
	public void concat(IData obj) {
		 
	}

	
	public Long getValue() {
		return value;
	}


	public void setValue(Long value) {
		this.value = value;
	}


	public String toStr() {
		 
		if(value==null){
			return "";
		}
		return value+"";
 
	}


	public double divide(Object obj) {
		 
		Long f = (Long)obj;
	 
		if(f==0){
			return 0;
		}
		return this.value*1.0/f;
		
	}


	public Object getPrimitiveValue() {
		 
		return this.value;
	}


	public Object getFormatPrimitiveValue() {
		return this.value;
	}


	public Long getLongPrimitiveValue() {
		
		return this.value;
	}


	public int comp(IData data2) {
		
		Object obj = data2.getPrimitiveValue();
		if(obj==null){
			return 1;
		}
		if(this.value==null){
			return -1;
		}
		
		return  this.value>(Long)obj?1:-1;
	}
	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
}
