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
 * �����ҳ����
 */
public class AddDialog extends JDialog
{
	//���
	private Box bigBox = new Box(BoxLayout.X_AXIS);
	private Box choiseBox = new Box(BoxLayout.Y_AXIS);
	private Box directBox = new Box(BoxLayout.Y_AXIS);
	JTextField inputUrl = new JTextField("�ڴ�������ַ");
	JTextField inputKey = new JTextField("�ڴ�����ؼ���");
	
	//�ұ�
	String []defaultUrl = {"http://www.baidu.com", "http://its.pku.edu.cn/", 
			"http://news.163.com", "http://www.hao123.com",	"http://www.sina.com.cn", "http://www.douban.com", "http://www.pku.edu.cn"};
	Vector tableData = new Vector();
	Vector columnTitle = new Vector();
	Vector defaultData = new Vector();
	private UrlTable urlTable;
	
	//����
	private JButton checkButton = new JButton(ImageTool.makeImageIcon("images/dialog/nav-prefs.png"));	
	
	/**
	 * ���������ҳ����
	 * @param mf ������
	 * @param screen ��Ļ�ķֱ���Ϣ
	 */
	public AddDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "���Դ��ַ", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/web.png"));
		
		createChoiseBox();
		createDirectBox();
		bigBox.add(choiseBox);
		bigBox.add(directBox);
		
		createVector();
		urlTable = new UrlTable(tableData, columnTitle);
		JScrollPane tablePanel = new JScrollPane(urlTable);
		bigBox.add(tablePanel);
		
		checkButton.setToolTipText("ȷ��");
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
	 * ���ѡ��ģʽ
	 */
	private void createChoiseBox()
	{	
		JTextField textField = new JTextField("Normal����ͨģʽ����");
		textField.setEditable(false);
		choiseBox.add(textField);
		choiseBox.add(inputUrl);
		
		textField = new JTextField("I Feel Lucky!�����ģʽ����");
		textField.setEditable(false);
		choiseBox.add(textField);
		
		JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/games.png"));
		button.setToolTipText("���ѡ��");
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
		
		textField = new JTextField("Hack�����ڿ�ģʽ����");
		textField.setEditable(false);
		choiseBox.add(textField);
		choiseBox.add(inputKey);
		
		Box tempBox = new Box(BoxLayout.X_AXIS);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/baidu.png"));
		button.setToolTipText("�ٶ�һ��");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				inputUrl.setText("http://www.baidu.com/s?wd=" + inputKey.getText());
			}
		});
		tempBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/google_search.png"));
		button.setToolTipText("�ȸ�һ��");
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
	 * ��Ӳ�����ť 
	 */
	private void createDirectBox()
	{
		JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/button-foward.png"));
		button.setToolTipText("���");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				String str = inputUrl.getText();
				if (urlTable.addUrl(str) == false)
				{
					str = "��" + str + "��" + "������ַ��";
					JOptionPane.showMessageDialog(AddDialog.this, str,
						    "��ʽ����",
						    JOptionPane.WARNING_MESSAGE,
						    new ImageIcon("images/dialog/sign-alert.png"));
				}
			}
		});
		directBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/button-rewind.png"));
		button.setToolTipText("ɾ��ѡ��");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				urlTable.removeUrl();
			}
		});
		directBox.add(button);
		
		button = new JButton(ImageTool.makeImageIcon("images/dialog/trash.png"));
		button.setToolTipText("ɾ������");
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
	 * ���ɱ�����ʾ�����ݵĶ�Ӧ���� 
	 */
	private void createVector()
	{
		columnTitle.add("��ַ");
		for (String str : defaultUrl)
		{
			Vector tempV = new Vector();
			tempV.add(str);
			defaultData.add(tempV);
		}
	}
}

/**
 * ѡ������
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
	 * �������ַ
	 * @param str ����ַ
	 * @return ����Ƿ�ɹ�
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
	 * ɾ����ַ 
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
	 * ɾ��������ַ 
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