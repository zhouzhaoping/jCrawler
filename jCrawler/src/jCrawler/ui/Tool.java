package jCrawler.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

/**
 * 实用图像处理
 */
class ImageTool 
{

	/**
	 * 按照相应大小生成图像
	 * @param str 图像的地址
	 * @param width 要生成的新图像的宽
	 * @param height 要生成的新图像的高
	 * @return 一个新图像
	 */
	public static ImageIcon makeImageIcon(String str, int width, int height)
	{
		return new ImageIcon(new ImageIcon(str).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
	/**
	 * 按照默认大小生成图像
	 * @param str 图像的地址
	 * @return 一个新的图像
	 */
	public static ImageIcon makeImageIcon(String str)
	{
		return makeImageIcon(str, 50, 50);
	}
	
	/**
	 * 生成界面的小图标
	 * @param str 图像的地址
	 * @return 一个新图像
	 */
	public static Image makeSmallIcon(String str)
	{
		return new ImageIcon(str).getImage();
	}
}

/**
 * 实用组件处理
 */
class ComponentTool
{
	/**
	 * 把组件放在以n为比例的中间
	 * @param cmp 要居中的组件
	 * @param n 要居中的比例
	 * @return 组件居中在新JPanel上 
	 */
	public static JPanel makeMid(Component cmp, int n)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, n));
		for (int i = 0; i <= n / 2; ++i)
			panel.add(new JPanel());
		panel.add(cmp);
		for (int i = 0; i < n - n / 2; ++i)
			panel.add(new JPanel());
		return panel;		
	}
	

	/**
	 * 把组件放在默认的居中位置
	 * @param cmp 要居中的组件
	 * @return 组件剧中在新JPanel上
	 */
	public static JPanel makeMid(Component cmp)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		panel.add(new JPanel());
		panel.add(cmp);
		panel.add(new JPanel());
		return panel;		
	}
}
