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
 * ����ȡ��URL����
 */
public class UrlQueue
{
	private LinkedList<WebUrl> queue = new LinkedList<WebUrl>();

	private ConcurrentSkipListMap<String, Double> map = new ConcurrentSkipListMap<String, Double>();

	/**
	 * ��ʼ��
	 */
	public void init()
	{
		map.clear();
	}
	
	/**
	 * ��URL��������У�����������URL��������
	 * @param webUrl ������URL
	 * @param avrWeight ���ӵ��������������Ǵ������URL���ò���Ϊ0��
	 * @return �����Ƿ�ɹ�
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
	 * �����Ƿ�Ϊ��
	 * @return �����Ƿ�Ϊ��
	 */
	synchronized public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	/**
	 * ��ö�ͷ
	 * @return ��ͷURL
	 */
	synchronized public WebUrl getUrl()
	{
		return queue.poll();
	}

	/**
	 * ���URL��
	 * @return URL����
	 */
	synchronized public int count()
	{
		return queue.size();
	}
	
	/**
	 * ���������ص�URL��ֵ�Լ���
	 * @return URL��ֵ�Լ���
	 */
	public Set<Map.Entry<String, Double>> pageRank()
	{
		return map.entrySet();
	}

	/**
	 * ���캯��
	 */
	public UrlQueue()
	{}
}
