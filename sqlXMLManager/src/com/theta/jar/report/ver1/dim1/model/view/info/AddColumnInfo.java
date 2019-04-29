package com.theta.jar.report.ver1.dim1.model.view.info;

import org.apache.log4j.Logger;
import org.dom4j.Node;
 
import com.theta.jar.report.ver1.jiekou.ILoadXML;

public class AddColumnInfo implements ILoadXML{

	private static final Logger logger = Logger.getLogger(AddColumnInfo.class);
	
	public boolean load(Node node) {
		
		logger.info("load   info ."+node.toString());
		return true;
	}

}
