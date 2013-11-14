package jCrawler.ui;

import jCrawler.crawler.Configuration;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * 添加网页窗口
 */
public class AddDialog extends JDialog
{
	//左边
	private Box bigBox = new Box(BoxLayout.X_AXIS);
	private Box choiseBox = new Box(BoxLayout.Y_AXIS);
	private Box directBox = new Box(BoxLayout.Y_AXIS);
	JTextField inputUrl = new JTextField("在此输入网址");
	JTextField inputKey = new JTextField("在此输入关键词");
	
	//右边
	String []defaultUrl = {"http://www.baidu.com", "http://its.pku.edu.cn/", 
			"http://news.163.com", "http://www.hao123.com",	"http://www.sina.com.cn", "http://www.douban.com", "http://www.pku.edu.cn"};
	Vector tableData = new Vector();
	Vector columnTitle = new Vector();
	Vector defaultData = new Vector();
	private UrlTable urlTable;
	
	//下面
	private JButton checkButton = new JButton(ImageTool.makeImageIcon("images/dialog/nav-prefs.png"));	
	
	/**
	 * 构造添加网页窗口
	 * @param mf 主窗口
	 * @param screen 屏幕的分辨信息
	 */
	public AddDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "添加源网址", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/web.png"));
		
		createChoiseBox();
		createDirectBox();
		bigBox.add(choiseBox);
		bigBox.add(directBox);
		
		createVector();
		urlTable = new UrlTable(tableData, columnTitle);
		JScrollPane tablePanel = new JScrollPane(urlTable);
		bigBox.add(tablePanel);
		
		checkButton.setToolTipText("确定");
		checkButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				for (int i = 0; i < tableData.size(); ++i)
						Configuration.srcWebUrl.add((String)((((Vector)tableData.get(i)).get(0))));
				AddDialog.this.dispose();
			}
		});
		
		this.add(bigBox, BorderLayout.CENTER);
		this.add(ComponentTool.makeMid(checkButton, 5), BorderLayout.SOUTH);
		int width = 800;
		int height = 600;
		setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}
	
	
	/**
	 * 添加选择模式
	 */
	private void createChoiseBox()
	{	
		JTextField textField = new JTextField("Normal（普通模式）：");
		textField.setEditable(false);
		choiseBox.add(textField);
		choiseBox.add(inputUrl);
		
		textField = new JTextField("I Feel Lucky!（随机模式）：");
		textField.setEditable(false);
		choiseBox.add(textField);
		
		JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/games.png"));
		button.setToolTipText("随机选定");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				int n = (int)(Math.random() * defaultUrl.length);
				inputUrl.setText(defaultUrl[n]);
			}
		});
		choiseBox.add(button);
		//choiseBox.add(ComponentTool.makeMid(button));
		
		textField = new JTextField("Hack！（黑客模式）：");
		textField.setEditable(false);
		choiseBox.add(textField);
		choiseBox.add(inputKey);
		
		Box tempBox = new Box(BoxLayout.X_AXIS);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/baidu.png"));
		button.setToolTipText("百度一下");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				inputUrl.setText("http://www.baidu.com/s?wd=" + inputKey.getText());
			}
		});
		tempBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/google_search.png"));
		button.setToolTipText("谷歌一下");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				inputUrl.setText("http://www.google.com.hk/search?q=" + inputKey.getText());
			}
		});
		tempBox.add(button);
		
		choiseBox.add(tempBox);
		//choiseBox.add(ComponentTool.makeMid(tempBox));
	}
	
	
	/**
	 * 添加操作按钮 
	 */
	private void createDirectBox()
	{
		JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/button-foward.png"));
		button.setToolTipText("添加");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				String str = inputUrl.getText();
				if (urlTable.addUrl(str) == false)
				{
					str = "”" + str + "“" + "不是网址！";
					JOptionPane.showMessageDialog(AddDialog.this, str,
						    "格式错误",
						    JOptionPane.WARNING_MESSAGE,
						    new ImageIcon("images/dialog/sign-alert.png"));
				}
			}
		});
		directBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/button-rewind.png"));
		button.setToolTipText("删除选定");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				urlTable.removeUrl();
			}
		});
		directBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/trash.png"));
		button.setToolTipText("删除所有");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				urlTable.removeAllUrl();
			}
		});
		directBox.add(button);
	}

	
	/**
	 * 生成表单所显示的数据的对应向量 
	 */
	private void createVector()
	{
		columnTitle.add("网址");
		for (String str : defaultUrl)
		{
			Vector tempV = new Vector();
			tempV.add(str);
			defaultData.add(tempV);
		}
	}
}

/**
 * 选择结果表单
 */
class UrlTable extends JTable
{
	
	Pattern p = Pattern.compile(Configuration.urlReg, Pattern.CASE_INSENSITIVE);
    Matcher m;
    
	public UrlTable(Vector tableData, Vector columnTitle)
	{
		super(tableData, columnTitle);
	}
	
	/**
	 * 添加新网址
	 * @param str 新网址
	 * @return 添加是否成功
	 */
	boolean addUrl(String str)
	{
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		if (str.startsWith("http://www.baidu.com/s?wd=") || str.startsWith("http://www.google.com.hk/search?q="))
			tableModel.addRow(new Object[]{str});
		else
		{
			m = p.matcher(str);
			if(m.find())
				tableModel.addRow(new Object[]{m.group(0)});
			else
				return false;
		}
		return true;
	}
	
	/**
	 * 删除网址 
	 */
	void removeUrl()
	{
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		int[] iRowIndex = this.getSelectedRows();
		for (int i = iRowIndex.length - 1; i >= 0 ; i--) 
		{
			tableModel.removeRow(iRowIndex[i]); 
		}
	}
	
	/**
	 * 删除所有网址 
	 */
	void removeAllUrl()
	{
		DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
		tableModel.setRowCount(0);
	}
	
	public boolean isCellEditable(int row,int col)
	{
		return false;
	}
}