package jCrawler.ui;

import jCrawler.crawler.Configuration;
import jCrawler.crawler.WebCrawler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * ������
 */
public class ProgressBar extends JDialog
{
	private Box bigBox = new Box(BoxLayout.X_AXIS);
	private Box rightBox = new Box(BoxLayout.Y_AXIS);
	
	JLabel info = new JLabel("��ȴ��������....");
	JLabel picture = new JLabel(ImageTool.makeImageIcon("images/dialog/find.png", 80, 80));
	JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
	JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/nav-stop.png"));
	
	Timer timer;
	
	/**
	 * ���������
	 * @param mf ������
	 * @param screen ��Ļ�ķֱ����
	 */
	public ProgressBar(MyFrame mf, Dimension screen)
	{
		super(mf, "���ڴ���", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/dialog/sign-alert.png"));
		
		makeRightBox();
		bigBox.add(picture);
		bigBox.add(rightBox);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				WebCrawler.flag = false;
				timer.stop();
				ProgressBar.this.dispose();
			}
		});
		
		this.add(bigBox, BorderLayout.CENTER);
		this.add(ComponentTool.makeMid(button, 3), BorderLayout.SOUTH);
		int width = 400;
		int height = 200;
		setBounds(100, 100, width, height);
	}
	
	/**
	 *	������������ڵ�һ������� 
	 */
	private void makeRightBox()
	{
		rightBox.add(info);
		
		bar.setStringPainted(true);
		bar.setBackground(Color.GREEN);
		rightBox.add(bar);
	}
	
	/**
	 * ��������õĽ�������������
	 */
	public void start()
	{
		button.setIcon(ImageTool.makeImageIcon("images/dialog/nav-stop.png"));
		info.setText("��ȴ��������....");
		
		bar.setMinimum(0);
		bar.setMaximum(Configuration.maxHtmlDownloaded);
		bar.setValue(0);
		
		timer = new Timer(300 , new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int n = WebCrawler.getDownLoadN();
				bar.setValue(n);
				
				if (n >= Configuration.maxHtmlDownloaded)
				{
					timer.stop();
					button.setIcon(ImageTool.makeImageIcon("images/dialog/nav-prefs.png"));
					info.setText("�������");
				}
			}
		});
		timer.start();
		this.setVisible(true);
	}	
}
