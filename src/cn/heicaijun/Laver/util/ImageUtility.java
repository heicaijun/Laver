package cn.heicaijun.Laver.util;

import java.awt.Image;

import javax.swing.ImageIcon;

import cn.heicaijun.Laver.view.MainFrm;

/**
 * @author chenshihao3
 *
 */
public class ImageUtility {
	/**
	 * 利用getScaledInstance方法对图片进行缩放，缩放至指定尺寸
	 * @param path	图片的地址
	 * @param width	缩放后的宽度
	 * @param height	缩放后的高度
	 * @return	缩放后的ImageIcon对象
	 */
	public static ImageIcon getImageIcon(String path, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(MainFrm.class.getResource(path));
		if (width == 0 || height == 0) {
			 return imageIcon;
		 }
		 imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT));
		 return imageIcon;
	}
}