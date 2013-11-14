package jCrawler.url;

import jCrawler.crawler.Configuration.*;

/**
 * URL�����Ϣ
 */
public class WebUrl
{
	private String urlString;
	private int id;
	private String charSet;
	private static int num = 0;

	/**
	 * ���URL�ַ���
	 * @return URL�ַ���
	 */
	public String getUrlString()
	{
		return urlString;
	}
	/**
	 * ��õ�ǰURL��ID
	 * @return ��ǰURL��ID
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * ���URL���ַ���
	 * @return ��ǰURL���ַ���
	 */
	public String getCharSet()
	{
		return charSet;
	}
	/**
	 * �����ַ���
	 * @param charSet ���ַ����ַ���
	 */
	public void setCharSet(String charSet)
	{
		this.charSet = charSet;
	}
	/**
	 * ��URL�ַ����еġ������滻Ϊ��_��
	 * @return �滻����ַ���
	 */
	public String getFiltedUrlString()
	{
		return urlString.replaceAll("/", "");
	}
	/**
	 * ���캯��
	 * @param Url URL�ַ���
	 */
	public WebUrl(String Url)
	{
		urlString = Url;
		id = ++num;
	}
}
