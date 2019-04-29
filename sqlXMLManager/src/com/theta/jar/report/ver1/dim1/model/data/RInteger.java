package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

public class RInteger implements IData {

	private Integer value;
	
	public RInteger() {
		super();
		
	}

	public RInteger(Integer v) {
		super();
		this.value = v;
	}

	public void add(IData obj) {
		 
		RInteger rs = (RInteger) obj;
		if(rs==null){
			return ;
		}
		 
		this.value =  (this.value + rs.getValue());
	}

	public void concat(IData obj) {
	 

	}

	public Integer getValue(){
		
		return value;
	}
	
	public void setValue(Integer v){
		this.value = v;
	}
	
	public String toStr() {
		
		if(value==null){
			return "";
		}
		return value+"";
	}

	@Override
	public String toString() {
		return "RInteger [v=" + value + "]";
	}

	public Object getPrimitiveValue() {

		
		return this.value;
	}
	
	public double divide(Object obj) {
		 
		Integer f = (Integer) obj;
		if(f==0){
			return 0;
		}
		return this.value*1.0/f;
		
	}

	public Object getFormatPrimitiveValue() {
		
		return this.value;
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
		
		return  this.value>(Integer)obj?1:-1;
	}
	
	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
}
