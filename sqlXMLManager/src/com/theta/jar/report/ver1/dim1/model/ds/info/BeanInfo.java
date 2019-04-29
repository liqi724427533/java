package com.theta.jar.report.ver1.dim1.model.ds.info;

/**
 * 
 * syc_column_list 对应的bean
 * 
 * @author han.kedang
 * 
 */

public class BeanInfo {

	private String tableName;
	private String reportid;
	private String cName;		//数据库字段名
	private String columnName;
	private String columnDesc;
	private String columnAlias;
	private String columnSql;
	private String columnRef;
	private String align;
	private String converter;
	private String para;
	private String renderer;
	private String width;
	private String bout;
	private String bvisible;
	private String sort_num;
	private String sortable;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	
	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDesc() {
		return columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

	public String getColumnAlias() {
		return columnAlias;
	}

	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}

	public String getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(String columnSql) {
		this.columnSql = columnSql;
	}

	public String getColumnRef() {
		return columnRef;
	}

	public void setColumnRef(String columnRef) {
		this.columnRef = columnRef;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getConverter() {
		return converter;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getBout() {
		return bout;
	}

	public void setBout(String bout) {
		this.bout = bout;
	}

	public String getBvisible() {
		return bvisible;
	}

	public void setBvisible(String bvisible) {
		this.bvisible = bvisible;
	}

	public String getSort_num() {
		return sort_num;
	}

	public void setSort_num(String sort_num) {
		this.sort_num = sort_num;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

}
