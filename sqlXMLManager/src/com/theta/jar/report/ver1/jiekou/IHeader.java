package com.theta.jar.report.ver1.jiekou;
/**
 * �洢ÿ�� ͷ��Ϣ;�������ݵ���;
 * ����ϢҲ����ͨ�� ���������ļ��õ�;
 * @author lizhiyang
 *
 */
public interface IHeader {

	public static final int Align_Left = 1;
	public static final int Align_Center = 2;
	public static final int Align_Right = 3;

	
	public static int Type_Int = 1; 
	public static int Type_String = 2; 
	public static int Type_Double = 3; 
	public static int Type_Date = 4; 
	public static int Type_Long = 5; 
	
	/**
	 * ȡ��header���� ;
	 * @return
	 */
	public String getHeaderName();
	/**
	 * ȡ��header ���� ��
	 * ����ȷ�����ȣ��򷵻� 0;
	 * @return
	 */
	public int  getHeaderWidth();
	
	/**
	 * �Ƿ������˿��
	 * @return
	 */
	public boolean isSetWidth();
	
	/**
	 * �Ƿ񵼳� ���� ; Ĭ�� hiden�в����� ;
	 * @return
	 */
	public boolean isExport();
	
	/**
	 * ���ض�Ӧ��������;
	 * 0,��ȷ�� ; 1.�ı� ��2.����  3.����;
	 * @return
	 */
	public int  getHeaderType();
 
	
	/**
	 * ���� align type;
	 * @return
	 */
	public int getAlign();
	/**
	 * �Ƿ������ڸ�ʽ 
	 * @return
	 */
	public boolean isDate();
	/**
	 * �Ƿ�Ϊ double������ 
	 * @return
	 */
	public boolean isDouble();
	/**
	 *  �Ƿ�Ϊint������
	 * @return
	 */
	public boolean isInt();
	/**
	 *  �Ƿ�Ϊlong������
	 * @return
	 */
	public boolean isLong();
	
	/**
	 * ������������;
	 * @param type
	 */
	public void setHeadType(int type);
	/**
	 * ȡ���������� 
	 * @return
	 */
	public String getDataIndex();
}
