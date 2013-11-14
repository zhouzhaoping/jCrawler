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
 * ��������
 */
class HelpDialog extends JDialog
{
	private JTextArea info = new JTextArea("\nʹ��˵����\n" +
			"\nһ����ԴԤ�������̣�\n" +
			"1.��������桱��������Դ���صı����ļ��С�\n" +
			"2.�������ӡ���������ȡ��Դ��ҳ��\n" +
			"3.��������á���������ȡ��ҳʱ�Ĳ�����\n" +
			"4.�������ʼ������ʼ��ȡ��ҳ�������Դ��\n" +
			"\n������Դ������\n" +
			"1.�鿴��Դģʽ����������һ������Ӧ��ť��\n" +
			"2.����ģʽ�����������������������ط��������\n" +
			"3.������Դģʽ�����ڱ���ѡ����Ҫ���ص���Ŀ,\nȻ�����������еġ����ء���ť��\n" + 
			"\n��������˵����\n" +
			"1.��������������ִ˴��ڡ�\n" +
			"2.��������ڡ�����������ϵ��Ϣ��\n");
	public HelpDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "����");
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/button-info.png"));
		
		info.setEditable(false);
		add(info);
		int width = 300;
		int height = 400;
		setBounds(screen.width / 2, 0, width, height);
	}
}

/**
 * ���ڴ���
 */
class AboutDialog extends JDialog
{
	private JTextArea info = new JTextArea("\n                                   Java Crawler\n\n" +
			"Version�� Java Crawler Server Release 3\n" +
			"(c) Copyright�� Z�Ϲ�˾��2013.  All rights reserved.\n" +
			"Author������ƽ�������� &  �����꣨��ͦ��");
	public AboutDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "���� jCrawler", true);
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
 * ���ô���
 */
class SettingDialog extends JDialog
{
	private JLabel []label = {new JLabel("     [" + jCrawler.crawler.Configuration.maxThread + "]     "), 
			new JLabel("     [" + jCrawler.crawler.Configuration.maxHtmlDownloaded + "]     ")};
	private Box sliderBox = new Box(BoxLayout.Y_AXIS);
	private JSlider []slider = new JSlider[2];
	private JButton button = new JButton(ImageTool.makeImageIcon("images/dialog/nav-prefs.png"));	
	
	/**
	 * ���ô����ұ���ʾѡȡ���� 
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
	 * �������ô���
	 * @param mf ������
	 * @param screen ��Ļ�ķֱ����
	 */
	public SettingDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "����", true);
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/tools.png"));
		
		slider[0] = new JSlider(0, 10);
		slider[0].setValue(jCrawler.crawler.Configuration.maxThread);
		addSlider(slider[0], 1, 1, "        �߳���         ", label[0]);
		
		slider[1] = new JSlider(0, 500);
		slider[1].setValue(jCrawler.crawler.Configuration.maxHtmlDownloaded);
		addSlider(slider[1], 50, 25, "���������ҳ��", label[1]);

		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)	
			{
				String warning = "";
				if (slider[0].getValue() == 0)
					warning += "�߳�������Ϊ�㣡\n";	
				if (slider[1].getValue() == 0)
					warning += "���������ҳ������Ϊ�㣡";
				
				if (warning == "")
				{
					jCrawler.crawler.Configuration.setConfiguration(slider[0].getValue(), slider[1].getValue());
					SettingDialog.this.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(SettingDialog.this, warning,
						    "��������",
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
	 * ���slider
	 * @param slider Ҫ��ӵ�slider
	 * @param maxN ����ļ��
	 * @param minN ����ļ��
	 * @param description slider����Ϣ
	 * @param label slider����ʾ����
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
		box.add(new JLabel(description + "��"));
		box.add(slider);
		box.add(label);
		sliderBox.add(box);
	}
}

/**
 * ��������
 */
class AnalyseDialog extends JDialog
{
	private JLabel mainLabel = new JLabel("����������£�");
	private String []info = {"��ַ", "����", "�绰", "�ļ�", "ͼƬ", "��Ƶ", "����"};
	private JLabel []label = new JLabel[7];
	
	private String []filename = {"_pageRank", "_email", "_phone", "_doc", "_pic", "_audio", "_torrent"};
	
	/**
	 * �����������
	 * @param mf ������
	 * @param screen ��Ļ�ֱ��ʲ���
	 */
	public AnalyseDialog(MyFrame mf, Dimension screen)
	{
		super(mf, "����ͳ��");
		this.setIconImage(ImageTool.makeSmallIcon("images/tool/chart.png"));
		
		this.setLayout(new GridLayout(label.length + 2, 1));
		this.add(new JLabel(ImageTool.makeImageIcon("images/tool/chart.png")));
		this.add(mainLabel);
		for (int i = 0; i < label.length; ++i)
		{
			label[i] = new JLabel(info[i] + "��" + "δ֪");
			this.add(label[i]);
		}
		
		int width = 150;
		int height = 350;
		setBounds((screen.width - width) / 2, (screen.height - height) / 2, width, height);
	}
	
	/**
	 * ���·�����Ϣ
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
				label[i].setText(info[i] + "��" + m);
			}
			catch (IOException e)
			{
				System.out.println("--IOException in input new file: " + e.getMessage());
				label[i].setText(info[i] + "��δ֪");
			}
		}
	}
}