package com.theta.jar.report.ver1.dim1.model;

import java.util.List;

import org.apache.velocity.app.VelocityEngine;

/**
 * 缓冲 VelocityEngine , VelocityContext; 个数有限。
 * @author Administrator
 *
 */
public class VelocityEngineCache {

	private static List<VelocityEngine> al = null;

}
