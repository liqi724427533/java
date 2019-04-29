package com.theta.jar.report.myutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//lihongya add 20120116
public class SqlUtil {

	/*
	 * 分析sql解析出查询用到的表
	 */
	public static String getTableNameFromSql(String sql){
		String tablename=null;
		
		String regex="from{1}\\s+[\\w]+\\s+where{1}";
		Pattern pattern= Pattern.compile(regex,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher matcher= pattern.matcher(sql);
		while(matcher.find()){
			String str_mar=matcher.group().trim();
			
			str_mar=str_mar.substring(5,str_mar.length() -5);
			str_mar=str_mar.trim();
			str_mar=str_mar.toUpperCase();
			if(str_mar.startsWith("ERROR")||str_mar.startsWith("FR")||str_mar.startsWith("SESSION")||str_mar.startsWith("RT_ALL_IUPS_LAC")||str_mar.startsWith("SYS")||str_mar.startsWith("WEB"))
			{
				tablename=str_mar;
				break;
			}
		}
		//regex="from{1}\\s+[\\w]+(\\s+|\\)|,)";
		regex="((from)|(join))\\s+[\\w_]+((\\s+)|#|\\))";
		pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		matcher=pattern.matcher(sql);
		while(matcher.find()){
			String str_mar=matcher.group().trim();
			
			str_mar=str_mar.trim();
			str_mar=str_mar.toUpperCase();
			str_mar=str_mar.replace("FROM", "");
			str_mar=str_mar.replace("JOIN", "");
			str_mar=str_mar.replace(")", "");
			str_mar=str_mar.replace(" ", "");
			
			regex="\\w+";
			pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
			matcher=pattern.matcher(str_mar);
			while(matcher.find()){
				str_mar=matcher.group().trim().toUpperCase();
				//System.out.println("mar1="+str_mar);
				if(str_mar.startsWith("ERROR")||str_mar.startsWith("FR")||str_mar.startsWith("SESSION")||str_mar.startsWith("RT_ALL_IUPS_LAC")||str_mar.startsWith("SYS")||str_mar.startsWith("WEB"))
			//		if((!str_mar.toUpperCase().startsWith("FROM"))&&(str_mar.startsWith("ERROR")||str_mar.startsWith("FR")||str_mar.startsWith("SESSION")||str_mar.startsWith("RT_ALL_IUPS_LAC")||str_mar.startsWith("SYS")||str_mar.startsWith("WEB")||str_mar.startsWith("GONG")))
				{
					tablename=str_mar;
					break;
				}
			}
			if(tablename!=null){
				break;
			}
		}
		return tablename;
	}
	
	public static void main(String[] args){
		
	//	String tablename="from FRT_CELL_WAPGW a where";
	//	String tablename="SELECT Sum(stat_success)+ Sum(stat_failed) AS total , Sum(stat_success) AS success , Sum(stat_failed) AS failed , Sum(delay_total)/Greatest(1, Sum(delay_number)) AS avg_delay, Min(delay_min) AS delay_min, Max(delay_max) AS delay_max ,  Sum(delay_lt10) AS delay_lt10, Sum(delay_l10t20) AS delay_l10t20 , Sum(delay_l20t30) AS delay_l20t30 , Sum(delay_l30t50) AS delay_l30t50 , Sum(delay_l50t70) AS delay_l50t70 , Sum(delay_l70t100) AS delay_l70t100, Sum(delay_l100t200) AS delay_l100t200, Sum(delay_l200t1000) AS delay_l200t1000 , Sum(delay_gt1000) AS delay_gt1000 , Max(report_sec)+1/24  as max_report, Min(report_sec) as min_report		 	 FROM frt_gtp_apn_hour  where   report_sec >=To_Date('2012-06-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS') and report_sec < To_Date('2012-06-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS')    and gtp_command=5 	";
	//	String tablename="  select decode(rat_type,1,1,2) rat_type,                 round(sum(case when gtp_command=5 then stat_success+stat_failed else 0 end )/10000/(To_Date('2012-08-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS')-To_Date('2012-08-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS')),2) createpdptotal,               round(100*sum(case when gtp_command=5 then stat_success else 0 end )/greatest(1,sum(case when gtp_command=5 then stat_success+stat_failed else 0 end )),2) createpdpsuccrate,              '' stat_faile_reason,              round(sum(case when gtp_command=6 then stat_success+stat_failed else 0 end )/10000/(To_Date('2012-08-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS')-To_Date('2012-08-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS')),2) updatepdptotal,               round(100*sum(case when gtp_command=6 then stat_success else 0 end )/greatest(1,sum(case when gtp_command=6 then stat_success+stat_failed else 0 end )),2) updatepdpsuccrate,              '' stat_update_sucess_reason,              round(sum(case when gtp_command=7 then stat_success+stat_failed else 0 end )/10000/(To_Date('2012-08-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS')-To_Date('2012-08-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS')),2) delpdptotal,               round(100*sum(case when gtp_command=7 then stat_success else 0 end )/greatest(1,sum(case when gtp_command=7 then stat_success+stat_failed else 0 end )),2) delpdpsuccrate,              '' stat_update_sucess_reason               from FRT_GTP_GGSN_day               where report_sec >=To_Date('2012-08-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS') and                        report_sec < To_Date('2012-08-21 00:00:00', 'YYYY-MM-DD HH24:MI:SS')               group by decode(rat_type,1,1,2)                           order by  rat_type   ";
		String tablename="from session_abnormal t";
 		System.out.println("tablename="+getTableNameFromSql(tablename));
	}
	
}
