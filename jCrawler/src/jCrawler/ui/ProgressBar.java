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
 * 进度条
 */
public class ProgressBar extends JDialog
{
	private Box bigBox = new Box(BoxLayout.X_AXIS);
	private Box rightBox = new Box(BoxLayout.Y_AXIS);
	
	JLabel info = new JLabel("请等待任务完成....");
	JLabel picture = new JLabel(ImageTool.makeImageIcon("images/dialog/find.png", 80, 80));
	JProgressBar bar = new JProgressBar(JProgressBar.HORIZONTAL);
	JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/nav-stop.png"));
	
	Timer timer;
	
	/**
	 * 构造进度条
	 * @param mf 主界面
	 * @param screen 屏幕的分辨参数
	 */
	public ProgressBar(MyFrame mf, Dimension screen)
	{
		super(mf, "正在处理", true);
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
	 *	构造进度条窗口的一部分组件 
	 */
	private void makeRightBox()
	{
		rightBox.add(info);
		
		bar.setStringPainted(true);
		bar.setBackground(Color.GREEN);
		rightBox.add(bar);
	}
	
	/**
	 * 供外面调用的进度条启动函数
	 */
	public void start()
	{
		button.setIcon(ImageTool.makeImageIcon("images/dialog/nav-stop.png"));
		info.setText("请等待任务完成....");
		
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
					info.setText("完成任务！");
				}
			}
		});
		timer.start();
		this.setVisible(true);
	}	
}
