package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;
 

public class RFloat implements IData {

	private Float value;
	
	public RFloat() {
	 
	}

	public RFloat(Float f){
		
		this.value = f;
	}
	
	public void add(IData obj) {
		
		RFloat rf = (RFloat)obj;
		
		this.value += rf.getValue();
	}

	public void concat(IData obj) {
	 
	}
	
	public Float getValue(){
		
		return value;
	}

	public void setValue(Float f){
		
		this.value = f;
	}
	
	public String toStr() {
		
		if(value==null){
			return "";
		}
		return value+"";
	}

	@Override
	public String toString() {
		return "RFloat [f=" + value + "]";
	}

	public Object getPrimitiveValue() {
		 
		return this.value;
	}

	public double divide(Object obj) {
		 
		float f = (Float) obj;
		if(f==0){
			return 0;
		}
		return this.value/f;
		
	}

	public Object getFormatPrimitiveValue() {

		return this.value.doubleValue();
	}

	public Long getLongPrimitiveValue() {
		 
		if(this.value==null){
			return null;
		}
		
		return this.value.longValue();
	}

	public int comp(IData data2) {
		
		Object obj = data2.getPrimitiveValue();
		if(obj==null){
			return 1;
		}
		if(this.value==null){
			return -1;
		}
		
		return  this.value>(Float)obj?1:-1;
	}
	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
	
}
