package com.theta.jar.report.myutil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

public class DataUtil {

	 private static Logger logger = Logger.getLogger(DataUtil.class);
	  
	/**
	 * 排序 
	 * @param list
	 * @param comp
	 */
	public static void sortList(List list , Comparator comp ){
		
		if(list==null){
			return ;
		}

		 for(int i=0; i< list.size(); i++){
		
			 Object obj1 = list.get(i);
			 Object temp = obj1;
			 int flag = i;
			 for(int j=i; j<list.size(); j++){
				Object obj2 = list.get(j);
				
				if(comp.compare(obj2, temp)< 0){
					temp = obj2;
					flag = j;
				}
			 }
			 
			 if(flag!=i){
				 
				 list.set(i, temp);
				 list.set(flag,  obj1);
			 }
		 }
	}
	
	public static List getSplitData2(int analysisType,List list,int topType){
		
		if((Integer)analysisType==null||list==null){		
			return list;
		}
		int showLen;
		if(topType==1)
			showLen = list.size();
		else if(topType==2){
			if(list.size()<=10)
				showLen = list.size();
			else
				showLen = 10;
		}else if(topType==3){
			if(list.size()<=20)
				showLen = list.size();
			else 
				showLen = 20;
		}else if(topType==4){
			if(list.size()<=30)
				showLen = list.size();
			else
				showLen = 30;
		}else{
			if(list.size()<=40)
				showLen = list.size();
			else
				showLen = 40;
		}
		ArrayList list1 = new ArrayList();
		
		for(int i=0; i< showLen; i++){
			list1.add(list.get(i));
		}
		
		return list1;
	}
	
	public static List getSplitData3(int type,List list){
		
		if((Integer)type==null||list==null){		
			return list;
		}		
		ArrayList list1 = new ArrayList();
		
		for(int i=0; i< list.size(); i++)
			list1.add(list.get(i));
		
		return list1;
	}
		
	public static int  ipRevers(int ip){
	
		int lip = 0x00000000;
		lip|=((ip<<24)&0xFF000000);
		lip|=((ip<<8)&0xFF0000);
		lip|=(ip>>8&0xFF00);
		lip|=((ip>>24)&0xFF);
		return lip;
	
	}

	/**
	 *  1.2.3.4
	 * 0x04030201
	 * @param ipStr
	 * @return
	 */
	public static long  ipStrToLong(String ipStr){
		
		if(ipStr==null){
			return 0;
		}		
		//int index = ipStr.indexOf(".");
	     String[] ip = ipStr.split("\\.");
	     int len = ip.length;
         long lip = 0;
	     try{

		     for(int i=0;i<len;i++){
		    	 long temp = Long.parseLong(ip[i]);
		    	 if(temp<0&&temp>255){
		    		 lip = 0;
		    		 return 0;
		    	 }
		    	 lip = lip|(temp<<(i*8));
		     }    	 
	     }catch(Exception ex){
	    	 lip =0;
	    	 logger.error("ipStr:"+ipStr,ex);
	     }
	
	 
		return lip;
	}
	
	public static Integer  ipStrToInt(String ipStr, int maskNumber){
		
		if(ipStr==null){
			return 0;
		}		
		
	    Integer lip = 0;
		//int index = ipStr.indexOf(".");
	     String[] ip = ipStr.split("\\.");
	     int len = ip.length;
	     if(len!=4){
	    	 lip = 0;
	    	 return lip;
	     }

	     for(int i=0;i<len;i++){
	    	 Integer temp = Integer.parseInt(ip[i]);
	    	 lip = lip|(temp<<(24-i*8));
	     }
	     
	     int unMaskNumber = 24-maskNumber;
	     int i = 0;
	     while(i < unMaskNumber){
	    	 lip = lip&(0xFFFFFFFF^(1<<i));
	    	 i++;
	     }
		return lip;
	}
	public static String[] pareseIpStr(String ipStr){
		
		if(ipStr==null){
			return null;
		}
		int index = ipStr.indexOf("/");
		if(index<=0){
			return null;
		}
	 
		String ipPart =  ipStr.substring(0, index);
		String maskPart = ipStr.substring(index+1);
		
		String[] row = new String[2];
		row[0] = ipPart;
		row[1] = maskPart;
		
		return row;
		
	}

	/**
	 * 低位字节作为高位ip
	 * @param ip
	 * @return
	 */
	public static String getIpStr(Long ip){
		
		if(ip==null){
			return " ";
		}
		int l = ip.intValue();
		
		
		String str =""+(short)(l&0xFF);
		str +="."+(short)((l>>8)&0xFF);		
		str +="."+(short)((l>>16)&0xFF);
		str += "."+(short)((l>>24)&0xFF);

		return str;

	}
	
	
	/**
	 * 高位字节作为高位ip
	 * @param ip
	 * @return
	 */
	public static String getIpStrHL(Long ip){
		
		if(ip==null){
			return " ";
		}
		int l = ip.intValue();
		
		
		String str = ""+(short)((l>>24)&0xFF);
		str +="."+(short)((l>>16)&0xFF);
		str +="."+(short)((l>>8)&0xFF);		
		str +="."+(short)(l&0xFF);
		
		return str;

	}
	
	
	public static  String maskNumToMaskStr(int maskNum){
		
		if(maskNum<0&&maskNum>32){
			return "";
		}
		
		long lip = 0xffffffff;
		
		   int unMaskNumber = 32-maskNum;
		   int i = 0;
		    while(i < unMaskNumber){
		    	 lip = lip&(0xFFFFFFFF^(1<<i));
		    	 i++;
		    }
		
		String str =getIpStrHL(lip);
		
		return str;
	}
	
	public static void main(String[] args){
		String str =""+(short)(471632851&0xFF);
		System.out.println(str);
	}

	/**
	 * d1/d2 *100
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static  Double percent(Double d1, Double d2){
		
	    double per  = 0;
		if(d2==null||d2.doubleValue()==0){
			return 0.0;
		}
		if(d1==null||d1.doubleValue()==0){
			return 0.0;
		}
		
		per = d1*100/d2;
		per = formatDouble(per,2 );
		
		return per;
		
	}
	
	public static  Double percent(BigDecimal d1, BigDecimal d2){
		
	    double per  = 0;
		if(d2==null||d2.doubleValue()==0){
			return null;
		}
		if(d1==null||d1.doubleValue()==0){
			return 0.0;
		}
		
		per = d1.doubleValue()*100/d2.doubleValue();
		per = formatDouble(per,2 );
		
		return per;
		
	}

	public static  Double avgRate(BigDecimal bytes, BigDecimal delay){
		
		double rate = 0;
		if(delay==null||delay.doubleValue()==0){
			return null;
		}
		if(bytes==null||bytes.doubleValue()==0){
			return 0.0;
		}
		rate = bytes.doubleValue()*1000/1024/delay.doubleValue();
		
		rate = formatDouble(rate,2 );
		return rate;
		
	}

	
	/**
	 *  (d2-d1)/d1
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static  double  changePercent(Double d1, Double d2){
		
		
		if(d1==null||d2==null||d1==0||d2==0){
			
			return 0;
		}
	
		return  formatDouble((d1 - d2)*100/d2 ,2);
		  
	}
	
	/**
	 *  (d2-d1)*100/d1
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static  String changePercent(BigDecimal d1, BigDecimal d2){
		
		
		if(d1==null||d2==null||d1.doubleValue()==0||d2.doubleValue()==0){
			
			return "--";
		}
	
		Double per = (d1.doubleValue()-d2.doubleValue())/d2.doubleValue();
		per = per *100;
		
		return formatDouble(per,2, 500);
	}

    public static String formatDoublePer(Double value, double maxValue){
    	if(value==null){
    		return "--";
    	}
    	if(value.doubleValue()>maxValue){
    		return "--";
    	}
    	return formatDouble(value*100, 2)+"";
    }
    
    public static String formatDoublePer(Double value){
    	if(value==null){
    		return "--";
    	}
    	return formatDouble(value*100, 2)+"";
    }

    /**
     * 小于等于0返回 --
     * @param value
     * @return
     */
    public static String formatDoublePer2(Double value){
    	if(value==null){
    		return "--";
    	}
    	if(value<=0){
    		return "--";
    	}
    	return formatDouble(value*100, 2)+"%";
    }

    /**
     * 小于等于0返回 -- 其他返回 double  * 2 保留2位小数
     * @param value
     * @return
     */
    public static String formatDoublePer3(Double value){
    	if(value==null||value<=0){
    		return "--";
    	}
   
    	return formatDouble(value*100, 2)+"";
    }
    
    public static String formatDoubleOrNull(Double value, double maxValue){
    	if(value==null){
    		return "--";
    	}
    	if(value>maxValue){
    		return "--";
    	}
    	return formatDouble(value, 2)+"";
    }
    
    public static String formatDoubleOrNull2(Double value){
    	
    	if(value==null||value<=0){
    		return "--";
    	}
    	return formatDouble(value, 2)+"";
    }
    
    public static String formatDoubleOrNull(Double value){
    	if(value==null){
    		return "--";
    	}
    	return formatDouble(value, 2)+"";
    }
    
    public static String formatDouble(Double value, int scale, double maxValue){
    	
    	if(value==null){
    		return "--";
    	}
    	if(value>maxValue){
    		return "--";
    	}
    	return formatDouble(value, scale)+"";
    }
    
    //
    public static double formatDataDynamic(Double d){
    	
    	if(Math.abs(d)>0.01){
    		d = formatDouble(d,2);
    	}else if(Math.abs(d)>0.0001){
    		d = formatDouble(d,4);
    	}else {
    		
    		d = formatDouble(d,6);
    	}
    	return d;
    }
    
    public static double formatDouble(Double doubleValue, int scale){
		   
    	    if(doubleValue==null){
		    	return 0;
		    }
    	    
    	    
    	    int num = 1;
    	    for(int i=0;i<scale;i++){
    	    	num = num*10;
    	    }
    	    
    	    doubleValue = doubleValue*num+0.5;
    	      
//    	    int tt = new Double(doubleValue).intValue();
    	    
    	    long tt = new Double(doubleValue).longValue();
    	    
    	    double d = (tt*1.0)/num;
    	    
    	    return d;
    	   /* String str = doubleValue.toString();
    	    int   index = str.indexOf(".");
    	    if(index<0){
    	    	return doubleValue;
    	    }
    	    int len = str.length();
    	    
    	    if(len<=index+1+scale){
    	    	return doubleValue ;
    	    }
    	    
    	    double value = Double.valueOf(str.substring(0, index+scale));
    	
    	    
    	    return value;
    	         */
            
    	
	}
	
	/**
	 **
	 *	/**
	 * 	 dimension 定义：
	 *   0.访问次数  total
	 *   1.访问流量  bytes
	 *   2.成功率    
	 *   3.乱序率
	 *   4.重传率
	 *   5.访问次数环比增速
	 *   6.访问次数同比增速
	 *   
	 *   7.平均时延
	 *   8.下载速率
	 *  指标指端名称约定：
	 *  success ,  failed ,  total,  bytes,  page_delay, packets,
	 *   fragment,retrans, ood, success_tb , failed_tb , total_tb ,
	 *  bytes_tb , success_hb, failed_hb,  total_hb , bytes_hb , 
	 *  success/Greatest(1,total) as successPer, ood/Greatest(1,packets) as oodPer
	 *  retrans/Greatest(1,total) as retransPer,
	 *  page_delay/Greatest(1,total) as page_delayPer 
	 *  bytes/Greatest(1,page_delay) as dowloadPer, 
	 *  (total-total_tb)/Greatest(1,total_tb) AS tbTotalPer, 
	 *  (total-total_hb)/Greatest(1,total_hb) AS hbTotalPer , 
	 *  (bytes - bytes_tb)/greatest(1,bytes) AS tbBytesPer,
	 *  (bytes-bytes_hb)/Greatest(1,bytes) AS hbBytesPer 
	 */
	public  static String getDeviceOrderField(int dimension){
		
		if(dimension==0){
			return "total";
		}else if(dimension==1){
			return "bytes";
		}else if(dimension==2){
			return "successPer";
		}else if(dimension==3){
			return "oodPer";
		}else if(dimension==4){
			return "retransPer";
		}else if(dimension==5){
			return "tbTotalPer";
		}else if(dimension==6){
			return "hbTotalPer";
		}else if(dimension==7){
			return "page_delayPer";
		}else if(dimension==8){
			return "downPer";
		}
		
		return "total";
	}
	
	/**
	 * 
	 * @param sx
	 * @return
	 */
	public static String getOrderStr(int sx){
		
		if(sx==0){
			return "desc";
		}else{
			return "asc";
		}
	}
	
	public static String getSiteTypeName(Integer siteType){
	
		if(siteType==null){
			return "";
		}
		
		switch(siteType){
		case 1:
		{
			return "小说阅读";
		
		}
		case 2:
		{
			return "流媒体";
		
		}
		case 3:
		{
			return "即时通信";
		
		}
		case 4:
		{
			return "搜索";
		
		}
		case 5:
		{
			return "浏览";
		
		}
		case 6:
		{
			return "下载";
		
		}
		case 7:
		{
			return "在线应用";
	
		}
		case 8:
		{
			return "交友";
	
		}
		case 9:
		{
			return "企业网";
	
		}
		case 10:
		{
			return "滔滔";
	
		}
		case 11:
		{
			return "游戏";
	
		}
		case 12:
		{
			return "综合";
		}

		default:
			return "其他";
		}
	}
	
	public static String maskMsisdn(String msisdn){
		
		if(msisdn==null){
			return "";
		}
		String str="";
		for(int i=0; i<msisdn.length();i++){
			
			if(i<3||i>6){
				str +=msisdn.charAt(i);
			}else{
				str +="*";
			}
			
		}

		return str;
	}
	
	/**
	 * 网络传过来数据中4个字节提取整数 
	 * @param buff
	 * @param from
	 * @return
	 */
  public static int getInt(byte[] buff, int from){
		
		int re = 0;
		
 

		re =  0xff&buff[from];
		re +=((0xff&buff[from+1])<<8);
		re += ((0xff&buff[from+2])<<16);
		re += ((0xff&buff[from+3])<<24);
		
		return re;
	}
	
  /**
   *  
   * @param to
   * @param start1
   * @param from
   * @param start2
   * @param len
   */
  public static void copyByte(byte[] to, int start1, byte[] from , int start2, int len){
	  
	  int toLen = to.length;
	  int frLen = from.length;
	  for(int i=0; i< len ; i++){
		  
		  if(start1+i>=toLen){
			  break;
		  }
		  if(start2+i>=frLen){
			  break;
		  }
		  to[start1+i] = from[start2+i];
	  }
	  
  }
}


