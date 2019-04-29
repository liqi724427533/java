package com.theta.jar.report.ver1.dim1.model.view.plugin;

import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.click.util.Bindable;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.theta.jar.jiekou.IFileExportInsertAndUpdateSqlTextUtil;
import com.theta.jar.jiekou.IFileExportUtil;
import com.theta.jar.resource.LanguageResource;
import com.theta.jar.report.ver1.dim1.export.HeaderInfo;
import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.jiekou.IHeader;
import com.theta.jar.report.ver1.jiekou.IReportIn;
import com.theta.jar.report.ver1.jiekou.IReportOut;
import com.theta.jar.report.ver1.jiekou.data.IData;
import com.theta.jar.report.ver1.jiekou.data.IRTConverter;
import com.theta.jar.report.ver1.jiekou.view.IView;
import com.theta.jar.report.ver1.jiekou.view.grid.IViewGrid;

public class ExtGridInfo implements IView, IViewGrid{

	private static final Logger logger = Logger.getLogger(ExtGridInfo.class);
	
	@Bindable protected LanguageResource res = LanguageResource.getInstance() ; //
	
	private boolean showId = true;
 
	/**
	 * [{
            id: 'topic', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
            header: "Topic",
            dataIndex: 'title',
            width: 420,
            sortable: true
        },{
            id: 'last',
            header: "Last Post",
            dataIndex: 'lastpost',
            width: 150,
            sortable: true
        }]
	 */
	
	public JSONArray getHeader(List<ShowInfo> showList, IReportIn report) {
		
		if(showList==null){
			return null;
		}
		int width_a=res.getLang().equalsIgnoreCase("zh")?14:7;
	
		JSONArray al = new JSONArray();
	
		
		JSONObject obj = null;
		
		if(this.showId){
			
			 obj = new JSONObject();
				
			 obj.put("header",  (res.getString("seqnumber")==null ||"".equals(res.getString("seqnumber").trim()))?"seqnumber":res.getString("seqnumber").trim()   );//"序号"
			 obj.put("dataIndex", "id");
			 obj.put("width", 36);
			 obj.put("align", "center");
			 obj.put("sortable", true);
			 al.add(obj);
		}
		
		for(int i=0; i < showList.size(); i++){
			 
			ShowInfo show = showList.get(i);
			if(show.bOut<1){
				continue;
			}
			 obj = new JSONObject();
			 String str_header =(res.getString(show.getDesc())==null ||"".equals(res.getString(show.getDesc()).trim()))?show.getDesc():res.getString(show.getDesc()).trim() ;
			//obj.put("id", show.getName() );
           // obj.accumulate(key, value)		
   		//	obj.put("header", show.getDesc()   );
   			obj.put("header", str_header );
			obj.put("dataIndex", show.getName());
			if(show.getbVisible()<1){
				obj.put("hidden", true);		
			}
			
			
			Map<String, Object> map = show.getMap();
			
			if(map==null){
				 
				 obj.put("align", "center");
				 obj.put("sortable", true);
			}
			if(map!=null){
			
				if(map.get("align")==null){
					obj.put("align", "center");
				}	
				if(map.get("sortable")==null){
					obj.put("sortable", true);
				}
				Set<Entry<String, Object>> entry = map.entrySet();
				Iterator<Entry<String, Object>> it = entry.iterator();
				while(it.hasNext()){
					
					Entry<String, Object> en = it.next();
					
					obj.put(en.getKey(), en.getValue());
				}
				
				// --add 20111221 lhy
				if(!map.containsKey("width")){
					int clu_width=str_header.length()*width_a;
					clu_width=clu_width>100?100:clu_width;
					clu_width=clu_width<50?50:clu_width;
					if(map.containsKey("style1")){
						clu_width=clu_width+20;
					}
					obj.put("width", clu_width);
				}
				
			}else{
				int clu_width=str_header.length()*width_a;
				clu_width=clu_width>100?100:clu_width;
				clu_width=clu_width<50?50:clu_width;
				obj.put("width", clu_width);
			}
			
			al.add(obj);
		}
		
		return al;
	}

 
	/**
	 *   fields: [
            'title', 'forumtitle', 'forumid', 'author',
            {name: 'replycount', type: 'int'},
            {name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'},
            'lastposter', 'excerpt'
        ],
	 */
	public JSONArray getStoreFields(List<ShowInfo> showList, IReportIn report) {
		
		if(showList==null){
			return null;
		}
		JSONArray al = new JSONArray();
	
		if(showId){
			al.add("id");	
		}
		
		for(int i=0; i < showList.size(); i++){
			 
			ShowInfo show = showList.get(i);
			if(show.bOut<1){
				continue;
			}
			al.add(show.getName());
		}
		
		return al;
	 
	}

	/**
	 * 取得要输出到页面字段 的 名称 以及 输出方式 到代号 列表 ；
	 * @param reportIn 
	 * @return
	 */
	public List<String> getOutNamesList(List<ShowInfo> showList, IReportIn reportIn){
		

		List<String> tempList = new ArrayList<String>();
		if(showList==null){
			return tempList;
		}
		for(int i=0; i< showList.size(); i++){

			tempList.add(showList.get(i).getName());
				 
		}			
		
		return tempList; 
	}
	
	/**
	 * 取得要输出到页面字段 的 名称 以及 输出方式 到代号 列表 ；
	 * @param reportIn 
	 * @return
	 */
	public List<String> getOutNamesList(IReportIn reportIn, List<ShowInfo> showList){
		

		List<String> tempList = new ArrayList<String>();
		if(showList==null){
			return tempList;
		}
		for(int i=0; i< showList.size(); i++){
			ShowInfo info = showList.get(i);
			if(info.refName!=null){
				tempList.add(info.refName);
			}else{
				tempList.add(info.getName());			
			}
			if(logger.isTraceEnabled()){
				logger.debug("colName:"+tempList.get(i));
			}
		 
		}			
		
		return tempList; 
	}
	
	/**
	 * 导出数据。
	 * velocity contenxt :  1. 请求参数 , name_req, startDate_req,...;
	 * 					    2. 前面数据第一条记录[kpiName][_viewIndex]: total_1, bytes_1
	 * 						3. 其他view的数据  ,[ds][_viewIndex]: ds_1
	 */
	public JSONObject exportData(List<ShowInfo> showList , IReportOut reportData, IReportIn reportIn, Map<String, Object> map, String viewIndex) {
	
		if(reportData==null){
			return null ;
		}
		
		//logger.debug("start execute  exportData! ");
		List<List<IData>> list = reportData.getDataList();
	 
		
		//排序 
		//...........
		//
		//logger.debug("order data over ! ");
		VelocityContext context = new VelocityContext();
		 
		VelocityEngine ve = new VelocityEngine();

		if(map!=null){
			Set<Entry<String, Object>> set = map.entrySet();
			Iterator<Entry<String, Object>> it = set.iterator();
			while(it.hasNext()){
				Entry<String, Object> entry = it.next();
				context.put(entry.getKey(), entry.getValue());
			}
			
			map.put("ds_"+viewIndex, reportData);
		 
		}
		List<String>  outNameList = this.getOutNamesList(reportIn, showList);
		
		List<Integer> outNameIndexList = reportData.getIndexListByName(outNameList);
		
		List<IData> record = null;
		
		JSONObject json = new JSONObject();
		JSONArray al = new JSONArray();
		JSONArray foot = new JSONArray();
		
		/**
		 * 将当前结果存入 Velocity上下文 
		 */
		context.put("list_"+viewIndex, list);
		map.put("ds_"+viewIndex, reportData);
		//context.put("map", map);
		int tempIndex = 0;
		int recordStart = reportData.getRecordStart();
		Object tempObj = null;
		for(int i=0; list!=null&&i< list.size(); i++){
			
			JSONObject item = new JSONObject();
		
			if(showId){
				item.put("id", recordStart+i);
			}

			record = (List<IData>) list.get(i);
			
			ShowInfo show = null;
			IRTConverter cv = null;
			
			//item.put( show.getName(),(cv.convert(record.get(tempIndex), show.parameter)));
			
			//将所有字段可取记录 存入 velocity context;
			int j = 0;
			for(j=0; j< outNameIndexList.size(); j++){
				
				  tempIndex = outNameIndexList.get(j);
				  show = showList.get(j);

				  if(tempIndex!=-1){
					  
					  tempObj = record.get(tempIndex).getPrimitiveValue();
					  context.put(show.name, tempObj );	
					  if(i==0){
						  map.put(show.name+"_"+viewIndex, tempObj);
					  }
				  }
			}
			
			for(j=0; j< outNameIndexList.size(); j++){
				

				  tempIndex = outNameIndexList.get(j);
				  show = showList.get(j);
				 
				  //不输出 此字段 
				   if(show.bOut<1){
					   continue;
				   }		 
				  cv = show.getConverter();
				  
				  if(tempIndex!=-1){
			
					  if(cv!=null){
						 
						  tempObj= cv.convert(context, ve, record.get(tempIndex), show.parameter);
						  context.put(show.name, tempObj);	
						  if(i==0){
							  map.put(show.name+"_"+viewIndex, tempObj);
						  }
						  
						  if(tempObj==null){
							  item.put(show.getName()," ");					  
						  }else{
							  item.put(show.getName(),tempObj);	
						  }
			 
					  }else{
						 // System.out.println(show.getName()+";index:"+tempIndex);
						  tempObj = record.get(tempIndex).getFormatPrimitiveValue();
						  if(tempObj==null){
							  item.put(show.getName()," ");	  
						  }else{
							  item.put(show.getName(), tempObj);
						  }

					  }
					
				  }else{
					  
					  if(cv!=null){
						    tempObj= cv.convert(context, ve,show.parameter);
						    context.put(show.name, tempObj);
						    if(i==0){
								  map.put(show.name+"_"+viewIndex, tempObj);
							 }
						    
						     if(tempObj==null){
								  item.put(show.getName()," ");					  
							  }else{
								  item.put(show.getName(),tempObj);	
							  }
						    
					  }else{
							item.put(show.getName(),"");
					  }
					 
				  }
				
			}
			al.add(item);
			
			
		}
		json.put("total", reportData.getTotalNumber());
		json.put("rightTotalNumber", reportData.bRightTotalNumber()?1:0);
		json.put("records", al);
		

		if(logger.isDebugEnabled()){
			logger.debug("generate json object over ! ");
		}

		return json;
	}

	public void getShowModelInfo(List<ShowInfo> showList, Object obj, IReportIn report,
			Map<String, Object> showModelMap, String viewIndex) {
		
		if(showModelMap==null){
			logger.error("showModelMap is null \n");
			return ;
		}
		JSONObject json = new JSONObject();
		JSONArray al1 = getHeader(showList, report);
		JSONArray al2 = getStoreFields(showList, report);
		json.put(IViewGrid.columns, al1);
		json.put(IViewGrid.fields, al2);
		
		showModelMap.put("grid"+viewIndex, json);
		 
	}

	/**
	 * json.view[index].grid[viewIndex]
	 * showMap.grid[index].columns
	 * json格式如下:
	 * {"request":
	 * {"limit":"20","startDate":"2013-05-14 00:00:00","fileType":"0","nullShow":"1","endDate":"2013-05-15 00:00:00",
	 * "orderKpi":"totalbytes","reportId":"mobile_model_srv","network":"","time":"_hour",
	 * "pageCommand":"exportFile","order":"desc","start":"0","language":"zh"},
	 * "view1":{"total":3439,"rightTotalNumber":1,"records":[{"id":1,"mobile_type":"PC","total_number":8583,"success":6938,"successrate":80.83,"failed":1645,"upbytes":13.93,"downbytes":104.89,"totalbytes":118.82,"avbytes":0,"pagedelay":3449.61,"totalspeed":60.96,"fragmentrate":0,"ood":0.49,"retrans":4.35},
	 * 														 {"id":2,"mobile_type":"iPhone","total_number":508,"success":485,"successrate":95.47,"failed":23,"upbytes":2.18,"downbytes":76.21,"totalbytes":78.39,"avbytes":0,"pagedelay":3830.16,"totalspeed":539.99,"fragmentrate":0,"ood":4.96,"retrans":10.2}
	 * 														]
	 *         }
	 *  }
	 * 
	 * columns基本 格式 如下: export="0"或"false"代表不输出,其他取值代表输出;不包含 export，以默认值为准; type代表数据类型, type="string","date","int","double","long"几种格式;
	 * showMap.get("grid"+index):
	 * {"columns":[
	 * {"header":"序号","dataIndex":"id","width":36,"align":"center","sortable":true,"export":"1","type":"int"},
	 * {"header":"终端型号","dataIndex":"mobile_type","hidden":true,"sortable":"true","align":"left","width":160},
	 * {"header":"终端型号","dataIndex":"mobile_typename","sortable":"true","align":"left","width":160,"style1":"rClickICons"}
	 * 
	 */
	public void exportFile(JSONObject json, Map<String, Object> showMap,  OutputStream out,
			Writer writer, IReportIn report, int fileType,
			String fileName, String index) {
		
		JSONObject obj = (JSONObject) json.get("view"+index);
		if(obj==null){
			logger.error("find view failed.   viewIndex:"+index );
			return ;
		}
		JSONArray records = (JSONArray) obj.get("records");
		if(records==null){
			logger.error("records is null.");
			return ;
		}
		JSONObject gridShow = (JSONObject) showMap.get("grid"+index);
		if(gridShow==null){
			logger.error("find grid show failed.   viewIndex:"+index );
			return ;
		}
		
		JSONArray colums = (JSONArray) gridShow.get(this.columns);
		if(colums==null){
			logger.error("headers is null.  "+this.columns);
			return ;
		}
		String[] headers = new String[colums.size()];
		boolean[] hiddens = new boolean[colums.size()];
		IHeader[] heads = new HeaderInfo[colums.size()];
		String[] keys = new String[colums.size()];
		
		for(int i=0; i<headers.length; i++){
			boolean bl=false;
//			if(((JSONObject)colums.get(i)).has("hidden")&& "true".equalsIgnoreCase(((JSONObject)colums.get(i)).getString("hidden")) ){
//				bl=true;
//			}
			HeaderInfo temp = new HeaderInfo();
			JSONObject col = colums.getJSONObject(i);
			headers[i] = colums.getJSONObject(i).getString("header");
			String dataIndex = col.getString("dataIndex");
			keys[i] = dataIndex;
			temp.setDataIndex(dataIndex);
			if(dataIndex.equalsIgnoreCase("min_report")||dataIndex.equalsIgnoreCase("max_report")){
				bl=true;
			}
			
 			String hnl = headers[i].toLowerCase();
			if(headers[i].indexOf("%")>=0||headers[i].indexOf("率")>=0||hnl.indexOf("bps")>=0){
			   
				temp.setType(IHeader.Type_Double);
				temp.setAlign(IHeader.Align_Right);
			}else if(hnl.indexOf("report_sec")>=0){
				
				temp.setType(IHeader.Type_Date);
				temp.setAlign(IHeader.Align_Left);
			}else if(hnl.indexOf("ms")>=0){
				if(hnl.toLowerCase().indexOf("imei")>=0||hnl.toLowerCase().indexOf("imsi")>=0||hnl.toLowerCase().indexOf("msisdn")>=0){
					temp.setType(IHeader.Type_Long);
					temp.setAlign(IHeader.Align_Right);
				}else{
					temp.setType(IHeader.Type_String);
					temp.setAlign(IHeader.Align_Right);
				}
				
			}else if(headers[i].indexOf("序号")>=0){
				
				temp.setType(IHeader.Type_Int);
				temp.setAlign(IHeader.Align_Right);
			}else if(headers[i].indexOf("lac")>=0){
				
				temp.setType(IHeader.Type_Int);
				temp.setAlign(IHeader.Align_Left);
			}else if(headers[i].indexOf("次")>=0 || headers[i].indexOf("数")>=0 ||headers[i].indexOf("码")>=0){
				temp.setType(IHeader.Type_String);
				temp.setAlign(IHeader.Align_Right);				
			}else if(headers[i].indexOf("占比")>=0){
				temp.setType(IHeader.Type_Double);
				temp.setAlign(IHeader.Align_Right);			
			}else{
				temp.setType(IHeader.Type_String);
				temp.setAlign(IHeader.Align_Left);
			}
			
			//默认 hidden 时不输出；
			if(col.containsKey("hidden")){
				if(col.getBoolean("hidden")){
					bl = true;
				}
			}
			//若包含export配置，则 根据export确实是否导出 该数据项;
			/**
			 * 此导出属性暂时屏蔽
			 */
//			if(col.containsKey("export")){
//				String export = col.getString("export");
//				if("0".equalsIgnoreCase(export)||"false".equalsIgnoreCase(export)){
//					bl = true;
//				}else{
//					bl = false;
//				}
//			}
			
			if(col.containsKey("width")){
				try{
					int width = col.getInt("width");
					if(width>0){
						temp.setWidth(width);						
					}
				}catch(Exception ex){
					logger.error("width format error! width:"+col.getString("width"), ex);
				}
			}
			
			if(col.containsKey("align")){
				String align = col.getString("align");
				if("left".equalsIgnoreCase("left")){
					temp.setAlign(IHeader.Align_Left);
				}else if("center".equalsIgnoreCase("center")){
					temp.setAlign(IHeader.Align_Center);
				}else if("right".equalsIgnoreCase("right")){
					temp.setAlign(IHeader.Align_Right);
				}
			}
			
	
			//
			if(col.containsKey("type")){
				
				String type=col.getString("type");
				if("int".equalsIgnoreCase(type)){
					temp.setType(IHeader.Type_Int);
				}else if("string".equalsIgnoreCase(type)){
					temp.setType(IHeader.Type_String);
				}else if("date".equalsIgnoreCase(type)){
					temp.setType(IHeader.Type_Date);
				}else if("double".equalsIgnoreCase(type)){
					temp.setType(IHeader.Type_Double);
				}else if("long".equalsIgnoreCase(type)){
					temp.setType(IHeader.Type_Long);
				}
				
			}
			hiddens[i] = bl;
			temp.setbExport(!bl);
			temp.setHeaderName(headers[i]);
			heads[i] = temp;
		}
		IFileExportUtil fileExportUtil = (IFileExportUtil) ObjectManager.createObjectInstance(ObjectManager.FileExportUtil);
		IFileExportInsertAndUpdateSqlTextUtil fileExportInsertAndUpdateSqlTextUtil =  (IFileExportInsertAndUpdateSqlTextUtil) ObjectManager.createObjectInstance(ObjectManager.FileExportInsertAndUpdateSqlTextUtil);
		if(fileType==IFileExportUtil.ExcelType){
			fileExportUtil.exportExcel(out, fileName,  heads, records);		
		}else if(fileType==IFileExportUtil.PdfType){
			fileExportUtil.exportPdf(out, fileName, headers, keys, records,hiddens);		
			
		}else if(fileType==IFileExportUtil.XmlType){
			fileExportUtil.exportXml(writer, fileName, headers, keys, records,hiddens);		
			
		}else if(fileType==IFileExportUtil.TxtType){
			fileExportUtil.exportTxt(writer, fileName, headers, keys, records,hiddens);	
		}else if(fileType==IFileExportUtil.HtmlType){
			fileExportUtil.exportHtml(writer, fileName, headers, keys, records,hiddens);		

		}else if(fileType==IFileExportUtil.ImsiProperties){
			fileExportUtil.exportImeiProperties(writer, fileName, headers, records,hiddens);		
		}else if(fileType==IFileExportUtil.TxtTypeNoIndex){
			fileExportUtil.exportImei(writer, fileName, headers, records,hiddens);		
		}else if(fileType==IFileExportUtil.ImeiInsertSql){
			fileExportUtil.exportImeiSql(writer, fileName, headers, records,hiddens);		
		}else if(fileType==IFileExportInsertAndUpdateSqlTextUtil.InsertTxtType){
			fileExportInsertAndUpdateSqlTextUtil.exportSiteClassInsertSqlTxt(writer, fileName, headers, keys, records,hiddens);
			
		}else if(fileType==IFileExportInsertAndUpdateSqlTextUtil.UpdateTxtType){
			fileExportInsertAndUpdateSqlTextUtil.exportSiteClassUpdateSqlTxt(writer, fileName, headers, keys, records, hiddens);

		}else if(fileType == IFileExportUtil.MODULELISTSql ){
			fileExportUtil.exportTxt3(writer, fileName, headers, keys, records, hiddens);

		}else{
			logger.error("unknow fileType:"+fileType);
		}
		
		return ;
	}


	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report, int fileType,
			String fileName, String index, String groupHeaders) {
		JSONObject obj = (JSONObject) json.get("view" + index);
		if (obj == null) {
			logger.error("find view failed.   viewIndex:" + index);
			return;
		}
		JSONArray records = (JSONArray) obj.get("records");
		if (records == null) {
			logger.error("records is null.");
			return;
		}
		JSONObject gridShow = (JSONObject) showMap.get("grid" + index);
		if (gridShow == null) {
			logger.error("find grid show failed.   viewIndex:" + index);
			return;
		}

		JSONArray colums = (JSONArray) gridShow.get(columns);
		if (colums == null) {
			logger.error("headers is null.  " + columns);
			return;
		}
		String[] headers = new String[colums.size()];
		String[] imeIHeaders = new String[colums.size()];
		boolean[] hiddens = new boolean[colums.size()];
		IHeader[] heads = new HeaderInfo[colums.size()];
		String[] keys = new String[colums.size()];

		for (int i = 0; i < headers.length; i++) {
			boolean bl = false;
			HeaderInfo temp = new HeaderInfo();
			JSONObject col = colums.getJSONObject(i);
			headers[i] = colums.getJSONObject(i).getString("header");
			String dataIndex = col.getString("dataIndex");
			if (fileType == IFileExportUtil.ImsiProperties) {
				if ("classname".equalsIgnoreCase(dataIndex))
					imeIHeaders[i] = "mobile_class";
				else if ("netmodename".equalsIgnoreCase(dataIndex))
					imeIHeaders[i] = "network_mode";
				else
					imeIHeaders[i] = dataIndex;
			}
			keys[i] = dataIndex;
			temp.setDataIndex(dataIndex);
			if (dataIndex.equalsIgnoreCase("min_report")
					|| dataIndex.equalsIgnoreCase("max_report")) {
				bl = true;
			}

			String lowerCaseHeader = headers[i].toLowerCase();
			if (lowerCaseHeader.indexOf("%") >= 0
					|| lowerCaseHeader.indexOf("率") >= 0
					|| lowerCaseHeader.indexOf("bps") >= 0
					|| (lowerCaseHeader.indexOf("b") >= 0 && lowerCaseHeader
							.indexOf("enodeb") < 0)) {
				temp.setType(IHeader.Type_Double);
				temp.setAlign(IHeader.Align_Right);
			} else if (lowerCaseHeader.indexOf("report_sec") >= 0) {

				temp.setType(IHeader.Type_Date);
				temp.setAlign(IHeader.Align_Left);
			} else if (lowerCaseHeader.indexOf("ms") >= 0) {
				if (lowerCaseHeader.indexOf("imsi") >= 0|| lowerCaseHeader.indexOf("msisdn") >= 0) {
					temp.setType(IHeader.Type_String);
					temp.setAlign(IHeader.Align_Right);
				} else {
					temp.setType(IHeader.Type_Double);
					temp.setAlign(IHeader.Align_Right);
				}

			} else if (headers[i].indexOf("序号") >= 0) {

				temp.setType(IHeader.Type_Int);
				temp.setAlign(IHeader.Align_Right);
			} else if (headers[i].indexOf("次") >= 0
					|| headers[i].indexOf("数") >= 0
					|| headers[i].indexOf("码") >= 0) {
				temp.setType(IHeader.Type_Long);
				temp.setAlign(IHeader.Align_Right);
			} else if (headers[i].indexOf("占比") >= 0) {
				temp.setType(IHeader.Type_Double);
				temp.setAlign(IHeader.Align_Right);
			} else {
				temp.setType(IHeader.Type_String);
				temp.setAlign(IHeader.Align_Left);
			}

			// 默认 hidden 时不输出；
			if (col.containsKey("hidden")) {
				if (col.getBoolean("hidden")) {
					bl = true;
				}
			}

			if (col.containsKey("width")) {
				try {
					int width = col.getInt("width");
					if (width > 0) {
						temp.setWidth(width);
					}
				} catch (Exception ex) {
					logger.error(
							"width format error! width:"
									+ col.getString("width"), ex);
				}
			}

			if (col.containsKey("align")) {
				String align = col.getString("align");
				if (align.equalsIgnoreCase("left")) {
					temp.setAlign(IHeader.Align_Left);
				} else if (align.equalsIgnoreCase("center")) {
					temp.setAlign(IHeader.Align_Center);
				} else if (align.equalsIgnoreCase("right")) {
					temp.setAlign(IHeader.Align_Right);
				}
			}

			if (col.containsKey("type")) {

				String type = col.getString("type");
				if ("int".equalsIgnoreCase(type)) {
					temp.setType(IHeader.Type_Int);
				} else if ("string".equalsIgnoreCase(type)) {
					temp.setType(IHeader.Type_String);
				} else if ("date".equalsIgnoreCase(type)) {
					temp.setType(IHeader.Type_Date);
				} else if ("double".equalsIgnoreCase(type)) {
					temp.setType(IHeader.Type_Double);
				} else if ("long".equalsIgnoreCase(type)) {
					temp.setType(IHeader.Type_Long);
				}

			}
			hiddens[i] = bl;
			temp.setbExport(!bl);
			temp.setHeaderName(headers[i]);
			heads[i] = temp;
		}
		IFileExportUtil fileExportUtil = (IFileExportUtil) ObjectManager.createObjectInstance(ObjectManager.FileExportUtil);
		IFileExportInsertAndUpdateSqlTextUtil fileExportInsertAndUpdateSqlTextUtil =  (IFileExportInsertAndUpdateSqlTextUtil) ObjectManager.createObjectInstance(ObjectManager.FileExportInsertAndUpdateSqlTextUtil);
		if (fileType == IFileExportUtil.ExcelType) {
			fileExportUtil.exportExcel(out, fileName, heads, records,groupHeaders);
		} else if (fileType == IFileExportUtil.PdfType) {
			fileExportUtil.exportPdf(out, fileName, headers, keys, records,
					hiddens);

		} else if (fileType == IFileExportUtil.XmlType) {
			fileExportUtil.exportXml(writer, fileName, headers, keys, records,
					hiddens);

		} else if (fileType == IFileExportUtil.TxtType) {
			fileExportUtil.exportTxt(writer, fileName, headers, keys, records,
					hiddens);
		} else if (fileType == IFileExportUtil.HtmlType) {
			fileExportUtil.exportHtml(writer, fileName, headers, keys, records,
					hiddens);

		} else if (fileType == IFileExportUtil.ImsiProperties) {
			fileExportUtil.exportImeiProperties(writer, fileName, imeIHeaders,
					records, hiddens);
		} else if (fileType == IFileExportUtil.TxtTypeNoIndex) {
			fileExportUtil.exportImei(writer, fileName, headers, records,
					hiddens);
		} else if (fileType == IFileExportUtil.ImeiInsertSql) {
			fileExportUtil.exportImeiSql(writer, fileName, headers, records,
					hiddens);
		} else if (fileType == IFileExportInsertAndUpdateSqlTextUtil.InsertTxtType) {
			fileExportInsertAndUpdateSqlTextUtil.exportSiteClassInsertSqlTxt(
					writer, fileName, headers, keys, records, hiddens);

		} else if (fileType == IFileExportInsertAndUpdateSqlTextUtil.UpdateTxtType) {
			fileExportInsertAndUpdateSqlTextUtil.exportSiteClassUpdateSqlTxt(
					writer, fileName, headers, keys, records, hiddens);

		} else if (fileType == IFileExportUtil.MODULELISTSql) {
			fileExportUtil.exportTxt3(writer, fileName, headers, keys, records,
					hiddens);

		} else {
			logger.error("unknow fileType:" + fileType);
		}

		return;
	}
	
}
