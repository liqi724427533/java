package com.theta.jar.report.ver1.dim1.model.view.pluginmodel;

import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;

import com.theta.jar.report.ver1.dim1.model.ObjectManager;
import com.theta.jar.report.ver1.dim1.model.view.ViewDataModel;
import com.theta.jar.report.ver1.dim1.model.view.info.ShowInfo;
import com.theta.jar.report.ver1.dim1.model.view.pluginmodel.tree.TreeNode;
import com.theta.jar.report.ver1.jiekou.ILoadXML;
import com.theta.jar.report.ver1.jiekou.IReportIn;

import com.theta.jar.report.ver1.jiekou.view.ISubView;
import com.theta.jar.report.ver1.jiekou.view.IView;

public class TreeViewModelInfo implements ILoadXML, ISubView {

	private static final Logger logger = Logger.getLogger(ViewDataModel.class);

	private String outClass = null;
	private List<ShowInfo> showList = null;
	private String outAttr = null;

	/**
	 * 默认为1,2,3.... 输出数据 的 后缀索引。 jsonobject.(view)(index)
	 */
	public String index = "1";

	/**
	 * 是否输出 模型值。
	 */
	private boolean outModel = true;

	/**
	 * 显示插件接口.
	 */
	public IView view;
	TreeNode root;

	public boolean load(Node node) {

		if (node == null) {
			logger.error("node is null");
			return false;
		}
		this.outModel = true;
		Element el = (Element) node;

		this.outAttr = el.attributeValue("outAttr");
		if (this.outAttr == null) {
			this.outAttr = "records";
			logger.warn("outAttr is null, use default value records .");
		}

		String indexStr = el.attributeValue("index");
		if (indexStr != null && indexStr.trim().length() > 0) {

			this.index = indexStr.trim();
		}

		String outModel = el.attributeValue("out");
		if (outModel != null && outModel.trim().length() > 1) {
			this.outModel = false;
		}

		this.outClass = el.attributeValue("outclass");
		if (this.outClass == null || this.outClass.trim().length() < 2) {
			this.outClass = "com.theta.jar.report.ver1.dim1.model.view.plugin.ExtTreeInfo";
			this.view = (IView) ObjectManager.getSingleObjectInstance(this.outClass);

		} else {

			this.view = (IView) ObjectManager.getSingleObjectInstance(this.outClass);
			if (this.view == null) {
				logger.error("use default view class: ExtGridInfo . viewClass load error ! viewclass:" + this.outClass);
				this.outClass = "com.theta.jar.report.ver1.dim1.model.view.plugin.ExtTreeInfo";
				this.view = (IView) ObjectManager.getSingleObjectInstance(this.outClass);

			}

		}

		if (!parseShowColsInfo(node)) {

			return false;
		}

		if (!parseTreeInfo(node)) {
			return false;
		}
		return true;
	}

	/**
	 * 解析属性部分 信息
	 * 
	 * @param node
	 * @return
	 */
	private boolean parseTreeInfo(Node node) {

		root = null;
		Node treeRoot = node.selectSingleNode("tree");

		if (treeRoot == null) {
			logger.error("no config show info . views/view/tree !");
			return false;
		}

		TreeNode treeNode = new TreeNode();
		treeNode.depth = 1;
		treeNode.load(treeRoot);

		root = treeNode;
		if (logger.isDebugEnabled()) {
			logger.debug("treeNode:" + root.getTreeInfo());
		}
		return true;
	}

	/**
	 * 解析 /model/show部分
	 * 
	 * @param node
	 * @return
	 */
	private boolean parseShowColsInfo(Node viewNode) {

		List<Node> list = viewNode.selectNodes("show/col");

		if (this.showList != null) {
			this.showList.clear();
		} else {
			this.showList = new ArrayList<ShowInfo>();
		}
		if (list == null) {
			logger.error("no config show info . views/view/show !");
			return false;
		}

		for (int i = 0; i < list.size(); i++) {

			Node node = list.get(i);

			ShowInfo show = new ShowInfo();

			if (show.load(node)) {
				this.showList.add(show);
			} else {
				return false;
			}

		}

		return true;
	}

	public void getShowModelInfo(Map<String, Object> showModelMap, IReportIn report) {

		if (!this.outModel) {
			return;
		}

		this.view.getShowModelInfo(showList, this.root, report, showModelMap, this.index);
	}

	/**
	 * 
	 * @param reportIn
	 * @param json
	 *            :返回对象父对象
	 * @param map
	 *            :前面视图 ,数据记录
	 * @param viewIndex
	 *            ：视图index号
	 * @return
	 */
	public JSONObject exportData(IReportIn reportIn, JSONObject json, Map<String, Object> map, int viewIndex) {

		return null;
	}

	public boolean check() {

		return true;
	}

	public void exportFile(JSONObject json, Map<String, Object> showMap, OutputStream out, Writer writer,
			IReportIn report, int fileType, String fileName) {

	}

	public String getRef() {
		return null;
	}

	public void exportFile(JSONObject json, Map<String, Object> showMap,
			OutputStream out, Writer writer, IReportIn report, int fileType,
			String fileName, String groupHeaders) {
		
	}

}
