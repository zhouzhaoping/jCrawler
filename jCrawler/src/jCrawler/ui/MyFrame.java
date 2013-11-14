package jCrawler.ui;

import jCrawler.crawler.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



/**
 * 主窗口
 */
public class MyFrame extends JFrame
{
	//系统数据
	private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	//工具栏
	private JToolBar toolBar = new JToolBar();
	
	private Action startCrawler = new AbstractAction("开始", ImageTool.makeImageIcon("images/tool/button-play.png")){
		public void actionPerformed(ActionEvent e) {
			startCrawler();
		}
	};
	
	private Action saveFloder = new AbstractAction("保存", ImageTool.makeImageIcon("images/tool/folder.png")) {
		public void actionPerformed(ActionEvent e) {
			saveFloder();
		}
	};
	
	private Action addWeb = new AbstractAction("添加", ImageTool.makeImageIcon("images/tool/web.png")) {
		public void actionPerformed(ActionEvent e) {
			addWeb();
		}
	};
	
	private Action setting = new AbstractAction("设置", ImageTool.makeImageIcon("images/tool/tools.png")) {
		public void actionPerformed(ActionEvent e) {
			setting();
		}
	};
	
	private Action analyse = new AbstractAction("分析", ImageTool.makeImageIcon("images/tool/chart.png")) {
		public void actionPerformed(ActionEvent e) {
			analyse();
		}
	};
	
	private Action help = new AbstractAction("帮助", ImageTool.makeImageIcon("images/tool/button-info.png")) {
		public void actionPerformed(ActionEvent e) {
			help();
		}
	};
	
	private Action about = new AbstractAction("关于", ImageTool.makeImageIcon("images/tool/call-progress.png")) {
		public void actionPerformed(ActionEvent e) {
			about();
		}
	};
	
	private Action downItem = new AbstractAction("下载", ImageTool.makeImageIcon("images/tool/nav-down.png")) {
		public void actionPerformed(ActionEvent e) {
			downLoad();
		}
	};
	
	//一些提示窗口
	private HelpDialog helpDialog = new HelpDialog(this, screen);
	private AboutDialog aboutDialog = new AboutDialog(this, screen);
	private SettingDialog settingDialog = new SettingDialog(this, screen); 
	private AddDialog addDialog = new AddDialog(this, screen);
	private ProgressBar progressBar = new ProgressBar(this, screen);
	private AnalyseDialog analyseDialog = new AnalyseDialog(this, screen);
	JFileChooser saveDialog = new JFileChooser();
	
	//窗口分栏
	public static  ViewTable downloadTable = new ViewTable();
	private ButtonPanel viewButtonPanel = new ButtonPanel();

	
	public MyFrame()
	{		
		createToolBar();
		JScrollPane downloadPanel = new JScrollPane(this.downloadTable);
		
		JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewButtonPanel, downloadPanel);
		mainPanel.setDividerSize(10);
		mainPanel.setDividerLocation((int)(screen.width/10));
		
		
		this.add(mainPanel);
		this.setPreferredSize(new Dimension(screen.width / 2, screen.height - 30));
		this.setVisible(true);
		this.setTitle("jCrawler");
		this.setIconImage(ImageTool.makeSmallIcon("images/nav/web.png"));
		this.pack();
	}
	
	/**
	 * 生成工具栏 
	 */
	private void createToolBar() 
	{
		this.toolBar.setFloatable(false);
		this.toolBar.add(this.startCrawler).setToolTipText("开始");
		this.toolBar.add(this.saveFloder).setToolTipText("保存");
		this.toolBar.add(this.addWeb).setToolTipText("添加");
		this.toolBar.add(this.setting).setToolTipText("设置");
		this.toolBar.add(this.analyse).setToolTipText("分析");
		this.toolBar.add(this.downItem).setToolTipText("下载");
		this.toolBar.add(this.help).setToolTipText("帮助");
		this.toolBar.add(this.about).setToolTipText("关于");
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));
		this.add(this.toolBar, BorderLayout.NORTH);
	}
	
	//工具栏按钮激发的一些行为
	/**
	 *	开始进行爬虫 
	 */
	public void startCrawler()	
	{
		if (Configuration.srcWebUrl.size() == 0)
		{
			JOptionPane.showMessageDialog(this, "源网页数为零\n" + "请先添加网页！",
				    "参数错误",
				    JOptionPane.WARNING_MESSAGE,
				    new ImageIcon("images/dialog/sign-alert.png"));
		}
		else
		{
			WebCrawler.test();
			this.progressBar.start();
		}
	}
	
	/**
	 *	选择保存的目录
	 */
	public void saveFloder()
	{
		saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveDialog.setDialogTitle("选择你要保存下载内容的路径名");
		int result = saveDialog.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			Configuration.savePath = saveDialog.getSelectedFile().getAbsolutePath() + "/";
		}
	}
	
	/**
	 * 添加源网页
	 */
	public void addWeb()
	{
		this.addDialog.setVisible(true);
	}
	
	/**
	 * 设置 
	 */
	public void setting()
	{
		this.settingDialog.setVisible(true);
	}
	
	/**
	 * 分析 
	 */
	public void analyse()
	{
		this.analyseDialog.updateLabel();
		this.analyseDialog.setVisible(true);
	}
	
	/**
	 * 帮助 
	 */
	public void help()	{
		this.helpDialog.setVisible(true);
	}
	
	/**
	 * 关于 
	 */
	public void about() {
		this.aboutDialog.setVisible(true);
	}
	
	/**
	 * 下载 
	 */
	public void downLoad(){
		this.downloadTable.downLoad();
	}
}