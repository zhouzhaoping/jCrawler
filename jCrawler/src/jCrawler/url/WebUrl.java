package jCrawler.url;

import jCrawler.crawler.Configuration.*;

/**
 * URL相关信息
 */
public class WebUrl
{
	private String urlString;
	private int id;
	private String charSet;
	private static int num = 0;

	/**
	 * 获得URL字符串
	 * @return URL字符串
	 */
	public String getUrlString()
	{
		return urlString;
	}
	/**
	 * 获得当前URL的ID
	 * @return 当前URL的ID
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * 获得URL的字符集
	 * @return 当前URL的字符集
	 */
	public String getCharSet()
	{
		return charSet;
	}
	/**
	 * 设置字符集
	 * @param charSet 该字符的字符集
	 */
	public void setCharSet(String charSet)
	{
		this.charSet = charSet;
	}
	/**
	 * 将URL字符串中的‘。’替换为‘_’
	 * @return 替换后的字符串
	 */
	public String getFiltedUrlString()
	{
		return urlString.replaceAll("/", "");
	}
	/**
	 * 构造函数
	 * @param Url URL字符串
	 */
	public WebUrl(String Url)
	{
		urlString = Url;
		id = ++num;
	}
}
