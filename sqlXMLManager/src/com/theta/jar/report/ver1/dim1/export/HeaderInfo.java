package com.theta.jar.report.ver1.dim1.export;

import com.theta.jar.report.ver1.jiekou.IHeader;
/**
 * 文件导出头信息;
 * @author lizhiyang
 *
 */
public class HeaderInfo implements IHeader{
	
	private String headerName;
	private int width = 0;
	private boolean bExport = true;
 	//存储数据类型; int , string, date, double
	private int 	type = 0;
	private int     align =  0 ;
	//数据取值索引 
	private String dataIndex;
 	public String getHeaderName() {
		
		return headerName;
	}

	public int getHeaderWidth() {
		
		return width;
	}

	public boolean isExport() {
 
		return bExport;
	}

	public int getHeaderType() {
		 
		return this.type;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isbExport() {
		return bExport;
	}
	

	public boolean isSetWidth() {
 
		return this.width>0;
	}


	public void setbExport(boolean bExport) {
		this.bExport = bExport;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public void setAlign(int align) {
		
		this.align  = align;
	}
	
	public int getAlign(){
		
		return this.align;
	}

	public boolean isDate() {
		
		return type == this.Type_Date;
	}

	public boolean isDouble() {
	
		return this.type == Type_Double;
	}

	public boolean isInt() {
		
		return this.type == Type_Int;

	}

	public boolean isLong() {
		return this.type == Type_Long;
	}
	

	public void setHeadType(int type) {
		this.type = type;
	}

	/**
	 * 设置该 data对应 索引 
	 * @param dataIndex
	 */
	public void setDataIndex(String dataIndex) {
		
		this.dataIndex = dataIndex;
	}

	public String getDataIndex() {
		
		return this.dataIndex;
	}

}
