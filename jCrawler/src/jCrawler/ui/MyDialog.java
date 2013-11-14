package jCrawler.ui;

import jCrawler.crawler.Configuration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 帮助窗口
 */
class HelpDialog extends JDialog
{
	private JTextArea info = new JTextArea("\n使用说明：\n" +
			"\n一、资源预加载流程：\n" +
			"1.点击“保存”，设置资源下载的保存文件夹。\n" +
			"2.点击“添加”，设置爬取的源网页。\n" +
			"3.点击“设置”，设置爬取网页时的参数。\n" +
			"4.点击“开始”，开始爬取网页及相关资源。\n" +
			"\n二、资源分析：\n" +
			"1.查看资源模式——点击左边一栏的相应按钮。\n" +
			"2.分析模式——点击“分析”，出现相关分析结果。\n" +
			"3.下载资源模式——在表单中选择需要下载的项目,\n然后点击工具栏中的“下载”按钮。\n" + 
			"\n三、其他说明：\n" +
			"1.点击“帮助”出现此窗口。\n" +
			"2.点击“关于”出现作者联系信息。\n");
	public HelpDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "帮助");
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/button-info.png"));
		
		info.setEditable(false);
		add(info);
		int width = 300;
		int height = 400;
		setBounds(screen.width / 2, 0, width, height);
	}
}

/**
 * 关于窗口
 */
class AboutDialog extends JDialog
{
	private JTextArea info = new JTextArea("\n                                   Java Crawler\n\n" +
			"Version： Java Crawler Server Release 3\n" +
			"(c) Copyright： Z氏公司，2013.  All rights reserved.\n" +
			"Author：周钊平（渣吉） &  赵天雨（赵挺）");
	public AboutDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "关于 jCrawler", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/call-progress.png"));
		
		this.setLayout(new GridLayout(2, 1));
		this.add(new JLabel(ImageTool.makeImageIcon("images/dialog/web.png", 70,70)));
		info.setEditable(false);
		this.add(info);
		
		int width = 300;
		int height = 400;
		setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}
}

/**
 * 设置窗口
 */
class SettingDialog extends JDialog
{
	private JLabel []label = {new JLabel("     [" + jCrawler.crawler.Configuration.maxThread + "]     "), 
			new JLabel("     [" + jCrawler.crawler.Configuration.maxHtmlDownloaded + "]     ")};
	private Box sliderBox = new Box(BoxLayout.Y_AXIS);
	private JSlider []slider = new JSlider[2];
	private JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/nav-prefs.png"));	
	
	/**
	 * 设置窗口右边显示选取数字 
	 */
	private ChangeListener listener = new ChangeListener()
	{
		public void stateChanged(ChangeEvent event)
		{
			JSlider source = (JSlider) event.getSource();
			if (source == slider[0])
				label[0].setText("     [" + source.getValue() + "]     ");
			else
				label[1].setText("     [" + source.getValue() + "]     ");
		}
	};
	
	
	/**
	 * 构造设置窗口
	 * @param mf 主窗口
	 * @param screen 屏幕的分辨参数
	 */
	public SettingDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "设置", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/tools.png"));
		
		slider[0] = new JSlider(0, 10);
		slider[0].setValue(jCrawler.crawler.Configuration.maxThread);
		addSlider(slider[0], 1, 1, "        线程数         ", label[0]);
		
		slider[1] = new JSlider(0, 500);
		slider[1].setValue(jCrawler.crawler.Configuration.maxHtmlDownloaded);
		addSlider(slider[1], 50, 25, "最大下载网页数", label[1]);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				String warning = "";
				if (slider[0].getValue() == 0)
					warning += "线程数不能为零！\n";	
				if (slider[1].getValue() == 0)
					warning += "最大下载网页数不能为零！";
				
				if (warning == "")
				{
					jCrawler.crawler.Configuration.setConfiguration(slider[0].getValue(), slider[1].getValue());
					SettingDialog.this.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(SettingDialog.this, warning,
						    "参数错误",
						    JOptionPane.WARNING_MESSAGE,
						    new ImageIcon("images/dialog/sign-alert.png"));
				}
			}
		});
		
		this.add(sliderBox, BorderLayout.CENTER);
		this.add(ComponentTool.makeMid(button, 5), BorderLayout.SOUTH);
		int width = 700;
		int height = 200;
		setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
}
	
	/**
	 * 添加slider
	 * @param slider 要添加的slider
	 * @param maxN 主轴的间距
	 * @param minN 附轴的间距
	 * @param description slider的信息
	 * @param label slider的显示数据
	 */
	private void addSlider(JSlider slider, int maxN, int minN, String description, JLabel label)
	{
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(maxN);
		slider.setMinorTickSpacing(minN);
		slider.setPaintLabels(true);
		
		slider.addChangeListener(listener);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel(description + "："));
		box.add(slider);
		box.add(label);
		sliderBox.add(box);
	}
}

/**
 * 分析窗口
 */
class AnalyseDialog extends JDialog
{
	private JLabel mainLabel = new JLabel("分析结果如下：");
	private String []info = {"网址", "邮箱", "电话", "文件", "图片", "音频", "种子"};
	private JLabel []label = new JLabel[7];
	
	private String []filename = {"_pageRank", "_email", "_phone", "_doc", "_pic", "_audio", "_torrent"};
	
	/**
	 * 构造分析窗口
	 * @param mf 主窗口
	 * @param screen 屏幕分辨率参数
	 */
	public AnalyseDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "分析统计");
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/chart.png"));
		
		this.setLayout(new GridLayout(label.length + 2, 1));
		this.add(new JLabel(ImageTool.makeImageIcon("images/tool/chart.png")));
		this.add(mainLabel);
		for (int i = 0; i < label.length; ++i)
		{
			label[i] = new JLabel(info[i] + "：" + "未知");
			this.add(label[i]);
		}
		
		int width = 150;
		int height = 350;
		setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}
	
	/**
	 * 更新分析信息
	 */
	public void updateLabel()
	{
		File file = null;
		BufferedReader reader = null;
		
		for (int i = 0; i < info.length; ++i)
		{
			try
			{
				file = new File(Configuration.savePath + "INFOs/[sumary]" + filename[i]);
				reader = new BufferedReader(new FileReader(file));
				int m = Integer.parseInt(reader.readLine());
				if (i != 0)
					m /= 2;
				label[i].setText(info[i] + "：" + m);
			}
			catch (IOException e)
			{
				System.out.println("--IOException in input new file: " + e.getMessage());
				label[i].setText(info[i] + "：未知");
			}
		}
	}
}