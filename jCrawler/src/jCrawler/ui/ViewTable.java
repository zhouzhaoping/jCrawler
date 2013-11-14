package jCrawler.ui;

import jCrawler.crawler.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * ��Դ�б���
 */
public class ViewTable extends JTable 
{
	
	int contentType = 0;
	String []info = {"��ַ", "����", "�绰", "�ļ�", "ͼƬ", "��Ƶ", "����", "����"};
	
	/**
	 * ���캯��
	 * @param tableData ����vector
	 * @param columnTitle ����vector
	 */
	public ViewTable(Vector tableData, Vector columnTitle)
	{
		super(tableData, columnTitle);
	}
	
	public ViewTable()
	{
		super();
	}
	
	public boolean isCellEditable(int row,int col)
	{
		return false;
	}
	
	/**
	 * ����ѡ��Ԫ�� 
	 */
	public void downLoad()
	{
		if (contentType == 1 || contentType == 2)
		{
			JOptionPane.showMessageDialog(null, info[contentType] + "���ܱ����أ�",
				    "�������ʹ���",
				    JOptionPane.WARNING_MESSAGE,
				    new ImageIcon("images/dialog/nav-stop.png"));
		}
		else
		{
			DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
			int[] iRowIndex = this.getSelectedRows();
			if (iRowIndex.length == 0)
			{
				JOptionPane.showMessageDialog(null, "�����ڱ���ѡ��" + info[contentType] + "�ٵ�����أ�",
					    "û��ѡ����������",
					    JOptionPane.WARNING_MESSAGE,
					    new ImageIcon("images/dialog/sign-alert.png"));
				return;
			}
			for (int i = iRowIndex.length - 1; i >= 0 ; i--) 
			{
				downLoadItem(tableModel.getValueAt(i, 0).toString());
			}
			JOptionPane.showMessageDialog(null, info[contentType] + "���سɹ����뵽��Դ������" + Configuration.savePath + "Downloads�鿴�������ݣ�",
				    "��ϲ��",
				    JOptionPane.INFORMATION_MESSAGE,
				    new ImageIcon("images/dialog/nav-prefs.png"));
		}
	}
	
	/**
	 * ��ԴԪ������
	 * @param Ҫ���ص���Դ�ĵ�ַ
	 * @return �����Ƿ����سɹ�
	 */
	private boolean downLoadItem(String downloadUrl)
	{
		try
		{
			URL url = new URL(downloadUrl);
			InputStream is = url.openStream();
			String name = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.length());
			System.out.println(downloadUrl + " TO " + name);
			File file = new File(Configuration.savePath + "Downloads/" + name);
			FileOutputStream fos = new FileOutputStream(file);
			int i;
			while((i = is.read()) != -1)
				fos.write(i);	
			is.close();
			return true;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
