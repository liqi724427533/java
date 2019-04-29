package com.theta.jar.report.ver1.dim1.model.data;

import java.math.BigInteger;

import com.theta.jar.report.ver1.jiekou.data.IData;

/**
 * Description: 存储长整数 。
 * @author 李琦
 *
 */
public class RBigInteger implements IData{

	private BigInteger  value = null;
	
	public RBigInteger(BigInteger big){
		this.value = big;
	}
	public void add(IData obj) {
		
		this.value.add(obj.getBigValue());
	}

	public double divide(Object obj) {
		
		BigInteger d = (BigInteger)obj;
		if(d==null||d.doubleValue()==0){
			return 0;
		}
		return this.value.divide(d).doubleValue();
	}

	public void concat(IData obj) {
		// TODO Auto-generated method stub
		
	}

	public Object getPrimitiveValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toStr() {
		
		return this.value.toString();
	}

	public Object getFormatPrimitiveValue() {
		
		return this.value;
	}

	public Long getLongPrimitiveValue() {
		
		return this.value.longValue();
	}

	public int comp(IData data2) {
		
		return this.value.compareTo(data2.getBigValue());
	}

	public BigInteger getBigValue() {
		
		return this.value;
	}

}
