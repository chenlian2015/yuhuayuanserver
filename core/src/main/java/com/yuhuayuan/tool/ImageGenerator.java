package com.yuhuayuan.tool;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageGenerator {

	public static BufferedImage scaleImage(BufferedImage src, int newWidth, int newHeight)
	{
		 Image image = src.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT); 
		 BufferedImage sacledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);     
         Graphics2D g = sacledImage.createGraphics();   
         g.drawImage(image, 0, 0, null); // 绘制缩小后的图   
         g.dispose();   
         
         return sacledImage;
	}
	
	public static File generateImage(String headUrl, String qrCodeUrl, String nickName, String openid)
	{

		Resource resourceHaiBao = new ClassPathResource("images/haibao.jpg");	

		try {
			
			URL urlHeader = new URL(headUrl);
			InputStream isHeader = urlHeader.openStream();
			
			URL urlQRCodeUrl = new URL(qrCodeUrl);
			InputStream isQRCode = urlQRCodeUrl.openStream();
			
			InputStream risHaiBao = resourceHaiBao.getInputStream();
			
			BufferedImage ibHaiBao = ImageIO.read(risHaiBao);
			BufferedImage imageHeader = scaleImage(ImageIO.read(isHeader), 105, 105);
			BufferedImage imageQRCode = scaleImage(ImageIO.read(isQRCode), 180, 180);
			// create the new image, canvas size is the max. of both image sizes
			BufferedImage combined = new BufferedImage(ibHaiBao.getWidth(), ibHaiBao.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			// paint both images, preserving the alpha channels
			Graphics g = combined.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, combined.getWidth(), combined.getHeight());
			
			boolean success = g.drawImage(ibHaiBao, 0, 0, null);
			boolean sub = g.drawImage(imageHeader, 200, 220, null);
			
			 sub = g.drawImage(imageQRCode, 162, 391, null);
			g.drawChars(nickName.toCharArray(), 0, nickName.length(), 220, 345);
			g.dispose();
			
			// Save as new image
			File combinedIMG =  new File("/home/cl/workingsoft/apache-tomcat-7.0.70/webapps/data/", openid+"combined.png");
			ImageIO.write(combined, "PNG", combinedIMG);
			return combinedIMG;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String CreateTime = "" + System.currentTimeMillis();
		String template = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[%4$s]]></MediaId></Image></xml>";

		String result = String.format(template, "ToUserName", "FromUserName", System.currentTimeMillis(),
				"2cjk8UDYVvkl1sZLSmbqoFCkf0hxAT8uscn_fCpDzLxP0S2mjWwCGCykuXlpmko9");
		System.out.println(result);
	}
}
