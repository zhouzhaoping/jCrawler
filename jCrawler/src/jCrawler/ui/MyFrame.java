package jCrawler.ui;

import jCrawler.crawler.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



/**
 * ������
 */
public class MyFrame extends JFrame
{
	//ϵͳ����
	private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	//������
	private JToolBar toolBar = new JToolBar();
	
	private Action startCrawler = new AbstractAction("��ʼ", ImageTool.makeImageIcon("images/tool/button-play.png")){
		public void actionPerformed(ActionEvent e) {
			startCrawler();
		}
	};
	
	private Action saveFloder = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/folder.png")) {
		public void actionPerformed(ActionEvent e) {
			saveFloder();
		}
	};
	
	private Action addWeb = new AbstractAction("���", ImageTool.makeImageIcon("images/tool/web.png")) {
		public void actionPerformed(ActionEvent e) {
			addWeb();
		}
	};
	
	private Action setting = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/tools.png")) {
		public void actionPerformed(ActionEvent e) {
			setting();
		}
	};
	
	private Action analyse = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/chart.png")) {
		public void actionPerformed(ActionEvent e) {
			analyse();
		}
	};
	
	private Action help = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/button-info.png")) {
		public void actionPerformed(ActionEvent e) {
			help();
		}
	};
	
	private Action about = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/call-progress.png")) {
		public void actionPerformed(ActionEvent e) {
			about();
		}
	};
	
	private Action downItem = new AbstractAction("����", ImageTool.makeImageIcon("images/tool/nav-down.png")) {
		public void actionPerformed(ActionEvent e) {
			downLoad();
		}
	};
	
	//һЩ��ʾ����
	private HelpDialog helpDialog = new HelpDialog(this, screen);
	private AboutDialog aboutDialog = new AboutDialog(this, screen);
	private SettingDialog settingDialog = new SettingDialog(this, screen); 
	private AddDialog addDialog = new AddDialog(this, screen);
	private ProgressBar progressBar = new ProgressBar(this, screen);
	private AnalyseDialog analyseDialog = new AnalyseDialog(this, screen);
	JFileChooser saveDialog = new JFileChooser();
	
	//���ڷ���
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
	 * ���ɹ����� 
	 */
	private void createToolBar() 
	{
		this.toolBar.setFloatable(false);
		this.toolBar.add(this.startCrawler).setToolTipText("��ʼ");
		this.toolBar.add(this.saveFloder).setToolTipText("����");
		this.toolBar.add(this.addWeb).setToolTipText("���");
		this.toolBar.add(this.setting).setToolTipText("����");
		this.toolBar.add(this.analyse).setToolTipText("����");
		this.toolBar.add(this.downItem).setToolTipText("����");
		this.toolBar.add(this.help).setToolTipText("����");
		this.toolBar.add(this.about).setToolTipText("����");
		this.toolBar.setMargin(new Insets(5, 10, 5, 5));
		this.add(this.toolBar, BorderLayout.NORTH);
	}
	
	//��������ť������һЩ��Ϊ
	/**
	 *	��ʼ�������� 
	 */
	public void startCrawler()	
	{
		if (Configuration.srcWebUrl.size() == 0)
		{
			JOptionPane.showMessageDialog(this, "Դ��ҳ��Ϊ��\n" + "���������ҳ��",
				    "��������",
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
	 *	ѡ�񱣴��Ŀ¼
	 */
	public void saveFloder()
	{
		saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		saveDialog.setDialogTitle("ѡ����Ҫ�����������ݵ�·����");
		int result = saveDialog.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			Configuration.savePath = saveDialog.getSelectedFile().getAbsolutePath() + "/";
		}
	}
	
	/**
	 * ���Դ��ҳ
	 */
	public void addWeb()
	{
		this.addDialog.setVisible(true);
	}
	
	/**
	 * ���� 
	 */
	public void setting()
	{
		this.settingDialog.setVisible(true);
	}
	
	/**
	 * ���� 
	 */
	public void analyse()
	{
		this.analyseDialog.updateLabel();
		this.analyseDialog.setVisible(true);
	}
	
	/**
	 * ���� 
	 */
	public void help()	{
		this.helpDialog.setVisible(true);
	}
	
	/**
	 * ���� 
	 */
	public void about() {
		this.aboutDialog.setVisible(true);
	}
	
	/**
	 * ���� 
	 */
	public void downLoad(){
		this.downloadTable.downLoad();
	}
}