package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;
import java.util.Date;

import com.theta.jar.report.myutil.DataUtil;
import com.theta.jar.report.ver1.jiekou.data.IData;

public class RDouble implements IData{

	private Double value = 0.0;
	
	public RDouble() {
	 
	}

	public RDouble(Double d){
		this.value = d;
	}
	
	public void add(IData obj) {
	 
		RDouble temp = (RDouble)obj;
		if(temp==null){
			return ;
		}
		
		if(temp.getValue()!=null){
			
			this.value += temp.getValue();
		}
		
	}

	public void concat(IData obj) {
	 
		
	}
	
	
	public Double getValue(){
		
		return value;
	}

	public void   setValue(Double d){
		
		this.value = d;
	}
	
	public String toStr() {
		
		if(value==null){
			return "";
		}
		return DataUtil.formatDataDynamic(value)+"";
	 
	}

	@Override
	public String toString() {
		return "RDouble [value=" + value + "]";
	}

	public double divide(Object obj) {
		 
		double d = (Double) obj;
		if(d==0){
			return 0;
		}
		return this.value/d;
		
	}

	public Object getPrimitiveValue() {
		 
		return this.value;
	}

	public Object getFormatPrimitiveValue() {
		 
		if(value==null){
			return null;
		}
		if(value.longValue()==value){
			return value;
		}
		return DataUtil.formatDataDynamic(value);
	 
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
		
		return  this.value>(Double)obj?1:-1;
		 
	}

	public BigInteger getBigValue() {
		
		return new java.math.BigDecimal(this.value).toBigInteger();
	}
	
}
