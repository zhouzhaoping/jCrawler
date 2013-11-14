package jCrawler.ui;



import jCrawler.crawler.Configuration;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * ��ʾ��ťȺ
 */
public class ButtonPanel extends JPanel
{
	String []info = {"��ַ", "����", "�绰", "�ļ�", "ͼƬ", "��Ƶ", "����", "����"};
	String []imageName = {"web-server", "mail", "phone", "docs", "pictures", "music", "noatun", "program-small-icons"};
	String []filename = {"_pageRank", "_email", "_phone", "_doc", "_pic", "_audio", "_torrent", "_other"};
	JButton []button = new JButton[info.length];

	private final static int UP = 10000;
	Vector tableData = new Vector();
	Vector columnTitle = new Vector();
	
	private ViewTable table;
	
	/**
	 * ������ʾ��ťȺ 
	 */
	public ButtonPanel()
	{
		super();
		this.setLayout(new GridLayout(info.length, 1));
		createButton();
		for (int i = 0; i < info.length; ++i)
			this.add(button[i]);
	}
	
	
	/**
	 * ����button 
	 */
	private void createButton()
	{
		for (int i = 0; i < info.length; ++i)
			button[i] = new JButton(info[i], ImageTool.makeImageIcon("images/choise/" + imageName[i] + ".png", 60, 60));
		
		button[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				pageRank();
			}
		});
		button[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(1);
			}
		});
		button[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(2);
			}
		});
		button[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(3);
			}
		});
		button[4].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(4);
			}
		});
		button[5].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(5);
			}
		});
		button[6].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(6);
			}
		});
		button[7].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)	{
				doThings(7);
			}
		});
	}
	
	/**
	 * button����Ӧ������������ʾ��
	 * @param n
	 */
	public void doThings(int n)
	{
		File file;
		BufferedReader reader = null;
		
		tableData.clear();
		columnTitle.clear();
		columnTitle.add(info[n]);
		columnTitle.add("��Դ��ַ");
		
		try
		{
			file = new File(Configuration.savePath + "INFOs/[sumary]" + filename[n]);
			reader = new BufferedReader(new FileReader(file));
			int m = Integer.parseInt(reader.readLine()) / 2;
			if (m > UP) m = UP;
			for (int i = 0; i < m; ++i)
			{
				Vector tempV = new Vector();
				tempV.add(reader.readLine());
				tempV.add(reader.readLine());
				tableData.add(tempV);
			}
		}
		catch (IOException e)
		{
			System.out.println("--IOException in input new file: " + e.getMessage());
		}
		
		MyFrame.downloadTable.contentType = n;
		DefaultTableModel tableModel = (DefaultTableModel) MyFrame.downloadTable.getModel();
		tableModel = new DefaultTableModel(tableData, columnTitle);
		MyFrame.downloadTable.setModel(tableModel);
		MyFrame.downloadTable.repaint();
	}
	
	/**
	 * button[0]����Ӧ������������ʾ�� 
	 */
	private void pageRank()
	{
		File file;
		BufferedReader reader = null;
		
		tableData.clear();
		columnTitle.clear();
		columnTitle.add("����");
		columnTitle.add("��ַ");
		columnTitle.add("����ϵ��");
		
		try
		{
			file = new File(Configuration.savePath + "INFOs/[sumary]_pageRank");
			reader = new BufferedReader(new FileReader(file));
			int n = Integer.parseInt(reader.readLine());
			if (n > UP)
				n = UP;
			for (int i = 0; i < n; ++i)
			{
				String str = reader.readLine();
				String[] arr = str.split(" ");
				Vector tempV = new Vector();
				tempV.add(arr[0]);
				tempV.add(arr[3]);
				tempV.add(arr[1]);
				tableData.add(tempV);
			}
		}
		catch (IOException e)
		{
			System.out.println("--IOException in input new file: " + e.getMessage());
		}
		
		MyFrame.downloadTable.contentType = 0;
		DefaultTableModel tableModel = (DefaultTableModel) MyFrame.downloadTable.getModel();
		tableModel = new DefaultTableModel(tableData, columnTitle);
		MyFrame.downloadTable.setModel(tableModel);
		MyFrame.downloadTable.repaint();
	}
}
