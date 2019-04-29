package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

/**
 * 空对象 ;
 * +任何对象 等于 空对象 ；
 * concat任何对象等于   对象 
 * @author Administrator
 *
 */
public class RNull  implements IData{
	
	public void add(IData obj) {
		
	}

	public void concat(IData obj) {
		  
	}

	public String toStr() {
		
		return "null";
	}

	@Override
	public String toString() {
		return "RNull []";
	}

	public Object getPrimitiveValue() {
		 
		return null;
	}

	public double divide(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object getFormatPrimitiveValue() {
		 
		return "null";
	}

	public Long getLongPrimitiveValue() {
	 
		return null;
	}

	public int comp(IData data2) {
		
		Object obj = data2.getPrimitiveValue();
		if(obj==null){
			return 1;
		}
	 
		return -1;
	 
	}

	public BigInteger getBigValue() {
		
		return null;
	}
}
