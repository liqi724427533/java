package com.theta.jar.report.ver1.jiekou.view.tree;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.dim1.model.view.pluginmodel.tree.TreeNode;
import com.theta.jar.report.ver1.jiekou.IReportIn;

public interface IViewTree {
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
	
	public  final static  String  rootNode="root";
	/**
	 * 取得 头信息部分 
	 * 不能修改 showList,  reportData 值
	 * @param showList
	 * @param report
	 * @return
	 */
	public JSONArray getHeader(List<ShowInfo> showList, IReportIn report);
	
	/**
	 * 取得 fied信息部分 
	 * 不能修改 showList,  reportData 值
	 * @param showList
	 * @param report
	 * @return
	 */
	public JSONObject getTreeNodes(List<ShowInfo> showList, TreeNode node, IReportIn report);

	
}
