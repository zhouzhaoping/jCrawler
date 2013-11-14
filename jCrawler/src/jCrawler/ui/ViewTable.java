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
 * 资源列表类
 */
public class ViewTable extends JTable 
{
	
	int contentType = 0;
	String []info = {"网址", "邮箱", "电话", "文件", "图片", "音频", "种子", "其他"};
	
	/**
	 * 构造函数
	 * @param tableData 数据vector
	 * @param columnTitle 表项vector
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
	 * 下载选中元素 
	 */
	public void downLoad()
	{
		if (contentType == 1 || contentType == 2)
		{
			JOptionPane.showMessageDialog(null, info[contentType] + "不能被下载！",
				    "下载类型错误",
				    JOptionPane.WARNING_MESSAGE,
				    new ImageIcon("images/dialog/nav-stop.png"));
		}
		else
		{
			DefaultTableModel tableModel = (DefaultTableModel) this.getModel();
			int[] iRowIndex = this.getSelectedRows();
			if (iRowIndex.length == 0)
			{
				JOptionPane.showMessageDialog(null, "请先在表单里选择" + info[contentType] + "再点击下载！",
					    "没有选定下载内容",
					    JOptionPane.WARNING_MESSAGE,
					    new ImageIcon("images/dialog/sign-alert.png"));
				return;
			}
			for (int i = iRowIndex.length - 1; i >= 0 ; i--) 
			{
				downLoadItem(tableModel.getValueAt(i, 0).toString());
			}
			JOptionPane.showMessageDialog(null, info[contentType] + "下载成功！请到资源保存区" + Configuration.savePath + "Downloads查看下载内容！",
				    "恭喜您",
				    JOptionPane.INFORMATION_MESSAGE,
				    new ImageIcon("images/dialog/nav-prefs.png"));
		}
	}
	
	/**
	 * 资源元素下载
	 * @param 要下载的资源的地址
	 * @return 返回是否下载成功
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
