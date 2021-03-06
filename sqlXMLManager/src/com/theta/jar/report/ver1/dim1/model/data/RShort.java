package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

public class RShort implements IData {

	private Short value;
	
	public RShort() {
		super();
		
	}

	public RShort(Short v) {
		super();
		this.value = v;
	}

	public void add(IData obj) {
		 
		RShort rs = (RShort) obj;
		if(rs==null){
			return ;
		}
		 
		this.value = (short) (this.value + rs.getValue());
	}

	public void concat(IData obj) {
	 

	}

	public Short getValue(){
		
		return value;
	}
	
	public void setValue(Short v){
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
		return "RShort [value=" + value + "]";
	}

	public Object getPrimitiveValue() {

		return this.value;
	}

	public double divide(Object obj) {
		 
		short f = (Short) obj;
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
		
		return  this.value>(Short)obj?1:-1;
	}
	
	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
}
