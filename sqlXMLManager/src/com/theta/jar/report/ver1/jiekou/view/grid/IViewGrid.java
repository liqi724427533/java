package com.theta.jar.report.ver1.jiekou.view.grid;

import java.util.List;

import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.jiekou.IReportIn;

import net.sf.json.JSONArray;

public interface IViewGrid{
	
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
	public	final static  String  columns ="columns";
	/**
	 * 
         fields: [
            'title', 'forumtitle', 'forumid', 'author',
            {name: 'replycount', type: 'int'},
            {name: 'lastpost', mapping: 'lastpost', type: 'date', dateFormat: 'timestamp'},
            'lastposter', 'excerpt'
        ]
	 */
	public  final static  String  fields="fields";
	/**
	 * ȡ�� ͷ��Ϣ���� 
	 * �����޸� showList,  reportData ֵ
	 * @param showList
	 * @param report
	 * @return
	 */
	public JSONArray getHeader(List<ShowInfo> showList, IReportIn report);
	
	/**
	 * ȡ�� fied��Ϣ���� 
	 * �����޸� showList,  reportData ֵ
	 * @param showList
	 * @param report
	 * @return
	 */
	public JSONArray getStoreFields(List<ShowInfo> showList, IReportIn report);

	
 
	
}
