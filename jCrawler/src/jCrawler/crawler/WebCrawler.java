package jCrawler.crawler;

import jCrawler.url.*;
import jCrawler.fetcher.*;
import jCrawler.queue.*;
import jCrawler.parser.*;

import java.io.*;
import java.util.ArrayList;

/**
 * 爬虫内核
 */
public class WebCrawler extends Thread
{
	//flag
	public static boolean flag = false;
	
	// components
	static UrlQueue urlQueue = new UrlQueue();
	static Parser parser = new Parser(urlQueue);
	HtmlFetcher fetcher = new HtmlFetcher();

	// url & html properties
	private static ArrayList<String> srcWebUrl = Configuration.srcWebUrl;
	private static int htmlDownloaded = 0;

	// Thread properties
	int id;
	private static int threadNum = 0;
	static Thread[] threads = new WebCrawler[Configuration.maxThread];

	/**
	 * 获得线程数
	 * @return 线程数
	 */
	public static Thread[] getThread()
	{
		return threads;
	}
	
	/**
	 * 获得已下载数	
	 * @return 已下载数
	 */
	public static int getDownLoadN()
	{
		return htmlDownloaded;
	}
	
	/**
	 * 初始化	
	 */
	static public void init()
	{
		htmlDownloaded = 0;//!!!
		threadNum=0;
		WebCrawler.urlQueue.init();
		WebCrawler.parser.init();
		
		String path = Configuration.savePath;
		File pathFile = new File(path);
		if(pathFile.exists() && pathFile.isDirectory())
		{
			File files[] = pathFile.listFiles();
			for (File tmpFile: files)
			{
				if (tmpFile.getName() == "HTMLs" || tmpFile.getName() ==  "INFOs" || tmpFile.getName() ==  "Downloads")
					tmpFile.delete();
			}
		}
		else
		{
			if (pathFile.exists() && pathFile.isFile())
				pathFile.delete();
			pathFile = new File(path);
			pathFile.mkdirs();
		}
		
		File htmlsDir = new File(path + "HTMLs/");
		if(htmlsDir.exists())
		{
			File files[] = htmlsDir.listFiles();
			for (File tmpFile: files)
			{
				tmpFile.delete();
			}
			htmlsDir.delete();
		}
		htmlsDir = new File(path + "HTMLs");
		htmlsDir.mkdirs();

		File infosDir = new File(path +"INFOs/");
		if(infosDir.exists())
		{
			File files[] = infosDir.listFiles();
			for (File tmpFile: files)
			{
				tmpFile.delete();
			}
			infosDir.delete();
		}
		infosDir = new File(path + "INFOs");
		infosDir.mkdirs();

		File downloadDir = new File(path +"Downloads/");
		if(downloadDir.exists())
		{
			File files[] = downloadDir.listFiles();
			for (File tmpFile: files)
			{
				tmpFile.delete();
			}
			downloadDir.delete();
		}
		downloadDir = new File(path + "Downloads");
		downloadDir.mkdirs();
		
		for(Object webUrlString : srcWebUrl.toArray())
			urlQueue.add(new WebUrl((String)webUrlString), 0);
		//urlQueue.add(new WebUrl("http://tieba.baidu.com/f?kw=radiohead"), 0);
	}

	/**
	 * 图形界面调用内核函数
	 */
	public static void test()
	{
		init();
		flag = true;
		for (int i = 0; i < Configuration.maxThread; i++)
		{
			threads[i] = new WebCrawler();
			threads[i].start();
		}
	}
	
	/**
	 * 线程执行内容
	 */
	public void run()
	{
		System.out.println("--|Thread " + id + "| starts crawling");

		// do jobs in this loop
		loop:while (flag)
		{
			// queue jobs
			// ensure enough urls in queue to be picked
			while (urlQueue.isEmpty())
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					System.out.println("--InterruptedException when thread sleeps: " + e.getMessage());
				}
			}

			// fetcher jobs
			WebUrl webUrl = urlQueue.getUrl();
			String content = fetcher.readHtmlFromUrl(webUrl);
			String charSet = webUrl.getCharSet();

			// crawler jobs
			if (content == "")
				continue;
			int htmlID = htmlDownloadedAdd();
			if (htmlID > Configuration.maxHtmlDownloaded)
				break loop;
			System.out.println("--|" + id + "|Crawling URL(" + htmlID + " downloaded): [" + charSet + "] " + webUrl.getUrlString());

			// parser jobs
			Parser.getUrlsFromHtml(content, webUrl);
			Parser.writeContentToFile(webUrl, content, charSet, htmlID);
			content = Parser.deHTMLedContent(content);
			Parser.getEmailsFromContent(content, webUrl);
			Parser.getPhonesFromContent(content, webUrl);
			Parser.writeInfoToFile(Configuration.savePath + "INFOs/[sumary]");
		}

		System.out.println("--|Thread " + id + "| stops crawling");
	}

	/**
	 * 增加已下载数
	 * @return 已下载数
	 */
	synchronized private int htmlDownloadedAdd()
	{
		return ++htmlDownloaded;
	}

	/**
	 * 构造函数
	 */
	public WebCrawler()
	{
		id = threadNum++;
	}
}
