package jCrawler.queue;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import jCrawler.url.*;
import jCrawler.crawler.Configuration;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.net.*;

/**
 * 待爬取的URL队列
 */
public class UrlQueue
{
	private LinkedList<WebUrl> queue = new LinkedList<WebUrl>();

	private ConcurrentSkipListMap<String, Double> map = new ConcurrentSkipListMap<String, Double>();

	/**
	 * 初始化
	 */
	public void init()
	{
		map.clear();
	}
	
	/**
	 * 将URL加入队列中，或增加已有URL的排名分
	 * @param webUrl 待操作URL
	 * @param avrWeight 增加的排名分数（若是待加入的URL，该参数为0）
	 * @return 加入是否成功
	 */
	synchronized public boolean add(WebUrl webUrl, double avrWeight)
	{
		if (map.get(webUrl.getUrlString()) == null)
		{
			URL url;
			map.put(webUrl.getUrlString(), new Double(avrWeight));
			return queue.add(webUrl);
		}
		else
		{
			double curWeight = map.get(webUrl.getUrlString());
			map.put(webUrl.getUrlString(), new Double(curWeight + avrWeight));
			return true;
		}
	}

	/**
	 * 队列是否为空
	 * @return 队列是否为空
	 */
	synchronized public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	/**
	 * 获得队头
	 * @return 队头URL
	 */
	synchronized public WebUrl getUrl()
	{
		return queue.poll();
	}

	/**
	 * 获得URL数
	 * @return URL数量
	 */
	synchronized public int count()
	{
		return queue.size();
	}
	
	/**
	 * 生成已下载的URL键值对集合
	 * @return URL键值对集合
	 */
	public Set<Map.Entry<String, Double>> pageRank()
	{
		return map.entrySet();
	}

	/**
	 * 构造函数
	 */
	public UrlQueue()
	{}
}
