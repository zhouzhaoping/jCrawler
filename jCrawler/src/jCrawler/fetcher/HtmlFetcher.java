package jCrawler.fetcher;

import jCrawler.queue.*;
import jCrawler.url.*;
import jCrawler.crawler.*;
import jCrawler.parser.*;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.util.regex.*;

/**
 * ��ҳHTML����������
 */
public class HtmlFetcher
{
	/**
	 * Ԥ����ҳ���charset
	 * @param webUrl ��ҳURL��Ϣ
	 * @return charset
	 */
	public String preReadHtmlFromUrl(WebUrl webUrl)
	{
		// PRE WORK: TRY TO GET CHARSET
		String charSet = "UTF-8";
		StringBuffer preContent = new StringBuffer();
		try
		{
			URL url = new URL(webUrl.getUrlString());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			String str;
			for (int i = 50; i >= 0; i--)
			{
				if((str = in.readLine()) != null)
				{
					preContent.append(str);
				}
			}
			charSet = getCharSetFromContent(preContent.toString());
			webUrl.setCharSet(charSet);

			in.close();
			return charSet;
		}
		catch (MalformedURLException e)
		{
			System.out.println("--[pre]Malformed URL: " + webUrl.getUrlString());
			return null;
		}
		catch (Exception e)
		{
			System.out.println("--[pre]Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * ��ʽ��ȡ����
	 * @param webUrl ��ҳURL��Ϣ
	 * @return ��ҳHTML����
	 */
	public String readHtmlFromUrl(WebUrl webUrl)
	{
		// PRE READ, TRY TO GET CHARSET
		// if null returned, it fails to read this url, return content as ""
		String charSet = preReadHtmlFromUrl(webUrl);
		if (charSet == null)
		{
			return "";
		}

		// READ CONTENT BY RIGHT CHARSET NOW
		// return content as "" if any error happens
		StringBuffer htmlContent = new StringBuffer();
		try
		{
			URL url = new URL(webUrl.getUrlString());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), webUrl.getCharSet()));
			String str;
			while((str = in.readLine()) != null)
			{
				htmlContent.append(str + "\r\n");
			}
			return htmlContent.toString();
		}
		catch (MalformedURLException e)
		{
			System.out.println("--Malformed URL: " + webUrl.getUrlString());
			return "";
		}
		catch (Exception e)
		{
			System.out.println("--Error: " + e.getMessage());
			return "";
		}
	}

	/**
	 * ��һ�����������ƥ���charset
	 * @param content ���
	 * @return charset
	 */
	public String getCharSetFromContent(String content)
	{
		String charSet = "UTF-8";

		Pattern p = Pattern.compile(Configuration.charSetReg);
		Matcher m = p.matcher(content);
		if(m.find())
		{
			charSet = m.group(1);
		}

		return charSet;
	}

	/**
	 * ���캯��
	 */
	public HtmlFetcher()
	{
	}
}
