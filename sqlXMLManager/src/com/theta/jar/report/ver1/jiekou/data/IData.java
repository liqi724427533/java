package com.theta.jar.report.ver1.jiekou.data;

import java.math.BigInteger;

/**
 * ��װ ���ݿ��ѯ�������ݡ�
 * @author Administrator
 *
 */
public interface IData {

	/**
	 * �ϲ����� ; ��ֵ��������ӣ��ַ��������;
	 * @param obj
	 */
	public void add(IData obj);
	
	/**
	 * this/obj;
	 * @param obj
	 */
	public double divide(Object obj);
	
	/**
	 * ���Ӷ��� 
	 * @param obj
	 */
	public void concat(IData obj);
	
	/**
	 * ȡ�� ԭ������ , Integer, Double, String , Long ,.....
	 * @return
	 */
	public Object getPrimitiveValue();
	
	/**
	 * ���ض����ַ�����ʾ��ʽ ;
	 * null���� ""; �ַ������� "null"
	 * @return
	 */
	public String toStr();

	/**
	 * ȡ�ø�ʽ�����ԭʼ����double ������λС��
	 * @return
	 */
	public Object getFormatPrimitiveValue();

	/**
	 * ���س�������
	 * @return
	 */
	public Long getLongPrimitiveValue();

	/**
	 * �����㷨��
	 * @param data2
	 * @return
	 */
	public int comp(IData data2);

	/**
	 * ȡ�ö�Ӧbig integer ֵ
	 * @return
	 */
	public BigInteger getBigValue();
	
}
