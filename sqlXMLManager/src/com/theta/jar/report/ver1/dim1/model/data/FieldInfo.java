package com.theta.jar.report.ver1.dim1.model.data;

public class FieldInfo {

	/**
	 * �ֶ� ��ʾ���� 
	 */
	public String desc;
	/**
	 * �ֶ� ���� 
	 */
	public String name;
	
	/**
	 * �ֶ� �������� 
	 */
	public int    type;
	
	
	
	public FieldInfo() {
	}


	public FieldInfo(String desc, String name, int type) {
		super();
		this.desc = desc;
		this.name = name;
		this.type = type;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public String toString() {
		return "FieldInfo [desc=" + desc + ", name=" + name + ", type=" + type
				+ "]";
	}

 
	
}
