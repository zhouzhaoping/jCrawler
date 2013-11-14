package jCrawler.ui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

/**
 * ʵ��ͼ����
 */
class ImageTool 
{

	/**
	 * ������Ӧ��С����ͼ��
	 * @param str ͼ��ĵ�ַ
	 * @param width Ҫ���ɵ���ͼ��Ŀ�
	 * @param height Ҫ���ɵ���ͼ��ĸ�
	 * @return һ����ͼ��
	 */
	public static ImageIcon makeImageIcon(String str, int width, int height)
	{
		return new ImageIcon(new ImageIcon(str).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}
	
	/**
	 * ����Ĭ�ϴ�С����ͼ��
	 * @param str ͼ��ĵ�ַ
	 * @return һ���µ�ͼ��
	 */
	public static ImageIcon makeImageIcon(String str)
	{
		return makeImageIcon(str, 50, 50);
	}
	
	/**
	 * ���ɽ����Сͼ��
	 * @param str ͼ��ĵ�ַ
	 * @return һ����ͼ��
	 */
	public static Image makeSmallIcon(String str)
	{
		return new ImageIcon(str).getImage();
	}
}

/**
 * ʵ���������
 */
class ComponentTool
{
	/**
	 * �����������nΪ�������м�
	 * @param cmp Ҫ���е����
	 * @param n Ҫ���еı���
	 * @return �����������JPanel�� 
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
	 * ���������Ĭ�ϵľ���λ��
	 * @param cmp Ҫ���е����
	 * @return �����������JPanel��
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
