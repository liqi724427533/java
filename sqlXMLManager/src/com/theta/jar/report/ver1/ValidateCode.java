package com.theta.jar.report.ver1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateCode extends HttpServlet {

	/**
	 * 定义图片的高度和宽度
	 */
	private static int HEIGHT = 20;
	private static int WIDTH = 80;
	Random random = new Random();

	/**
	 * Constructor of the object.
	 */
	public ValidateCode() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		HttpSession session = request.getSession();
		response.setContentType("image/jpeg");// 设置为图片格式的mime类型
		ServletOutputStream sos = response.getOutputStream();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);// 不让浏览器缓存
		// 建立缓存图像
		BufferedImage bufferImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferImage.getGraphics();
		g.setColor(new Color(252, 252, 252));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		String[] rands = getRandChar();
//		drawImage(g);
		drawRands(g, rands);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufferImage, "jpeg", baos);
		byte[] buf = baos.toByteArray();
		response.setContentLength(buf.length);
		sos.write(buf);
		baos.close();
		sos.close();

		session.setAttribute("code", Integer.parseInt(rands[0]) + Integer.parseInt(rands[2]));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// 输出背景图片
	private void drawRands(Graphics g, String[] rands) {
		// TODO Auto-generated method stub
		Color[] color = {new Color(31, 30, 23),new Color(208, 44, 47),new Color(42, 125, 56),new Color(139, 48, 54)};
		g.setFont(new Font(null, Font.ITALIC, 18));
		
		g.setColor(color[random.nextInt(4)]);
		g.drawString(rands[0], 3, 17);
		
		g.setColor(color[random.nextInt(4)]);
		g.drawString(rands[1], 18, 17);
		
		g.setColor(color[random.nextInt(4)]);
		g.drawString(rands[2], 34, 17);
		
		g.setColor(color[random.nextInt(4)]);
		g.drawString(rands[3], 48, 17);
		
		g.setColor(color[random.nextInt(4)]);
		g.drawString(rands[4], 65, 17);

	}

	// 输出干扰图像
//	private void drawImage(Graphics g) {
		// TODO Auto-generated method stub
//		g.setColor(Color.pink);
//		g.fillRect(0, 0, WIDHT, HEIGHT);
//		// 随即生成100个点
//		for (int i = 0; i < 30; i++) {
//			int x = random.nextInt(WIDHT);
//			int y = random.nextInt(HEIGHT);
//			g.setColor(new Color(50,155,204));
//			g.drawLine(x, y, x+10, y+10);
//		}
//	}

	// 产生验证码
	private String[] getRandChar() {
		// TODO Auto-generated method stub
//		String chars = "012345679ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String randChars[] = new String[5];
		randChars[0] = "" + random.nextInt(9);
		randChars[1] = "+";
		randChars[2] = "" + random.nextInt(9);
		randChars[3] = "=";
		randChars[4] = "?";
		
		return randChars;
	}

}
