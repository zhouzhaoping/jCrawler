package jCrawler.parser;

import jCrawler.url.*;
import jCrawler.crawler.*;
import jCrawler.crawler.Configuration.ContentType;
import jCrawler.fetcher.*;
import jCrawler.queue.*;

import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.*;

import javax.swing.text.AbstractDocument.Content;

import org.omg.CORBA.StringSeqHelper;

/**
 * ��HTML���ݽ��з���
 */
public class Parser 
{
	private static UrlQueue urlQueue;
	private static ConcurrentSkipListMap<String, String> emailMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> phoneMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> audioMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> docMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> picMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> torrentMap = new ConcurrentSkipListMap<String, String>();
	private static ConcurrentSkipListMap<String, String> otherMap = new ConcurrentSkipListMap<String, String>();

	static final String[] contentType = {"_email", "_phone", "_doc", "_pic", "_audio", "_torrent", "_other"};
	static ArrayList<String>[] contentArray;

	/**
	 * ��ʼ��
	 */
	static public void init()
	{
		Parser.emailMap.clear();
		Parser.phoneMap.clear();
		Parser.audioMap.clear();
		Parser.docMap.clear();
		Parser.picMap.clear();
		Parser.torrentMap.clear();
		Parser.otherMap.clear();
	}
	
	/**
	 * ��HTML��������ƥ�������URL�����������
	 * @param htmlContent HTML����
	 * @param webUrl HTML��Ӧ��URL��Ϣ
	 */
    static public void getUrlsFromHtml(String htmlContent, WebUrl webUrl)
    {
        Pattern p = Pattern.compile(Configuration.hrefReg, Pattern.DOTALL);
        Pattern pp = Pattern.compile(Configuration.urlReg, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlContent);
        Matcher pm;
        ArrayList<String> newUrlStrings = new ArrayList<String>();
        while (m.find())
        {
            String newUrlString = m.group(1);
            pm = pp.matcher(newUrlString);
            if (pm.find())
            {
                newUrlString = pm.group(0);
                newUrlStrings.add(newUrlString);
            }
        }
        double avrWeight = Configuration.weight / (double)newUrlStrings.size();
        for (String newUrlString: newUrlStrings)
        {
        	// deal with normal link
            WebUrl newWebUrl = new WebUrl(newUrlString);
            urlQueue.add(newWebUrl, avrWeight);
            
            // deal with source link
            if (newUrlString.endsWith(".doc") || newUrlString.endsWith(".docx") || newUrlString.endsWith(".txt") || newUrlString.endsWith(".pdf"))
            {
            	add(newUrlString, ContentType.TYPE_DOC, webUrl.getUrlString());
            }
            else if (newUrlString.endsWith(".mp3") || newUrlString.endsWith(".wma") || newUrlString.endsWith(".wav") || newUrlString.endsWith(".acc"))
            {
            	add(newUrlString, ContentType.TYPE_AUDIO, webUrl.getUrlString());
            }
            else if (newUrlString.endsWith(".jpeg") || newUrlString.endsWith(".gif") || newUrlString.endsWith(".bmp") || newUrlString.endsWith(".png") || newUrlString.endsWith(".jpg"))
            {
            	add(newUrlString, ContentType.TYPE_PIC, webUrl.getUrlString());
            }
            else if (newUrlString.endsWith(".torrent"))
            {
            	add(newUrlString, ContentType.TYPE_TORRENT, webUrl.getUrlString());
            }
            else if (newUrlString.endsWith(".exe") || newUrlString.endsWith(".zip") || newUrlString.endsWith(".rar"))
            {
            	add(newUrlString, ContentType.TYPE_OTHER, webUrl.getUrlString());
            }
        }
    }

    /**
     * ��HTML���ݽ�������ȥ��ǩ
     * @param content HTML����
     * @return ȥ��ǩ�����ҳ����
     */
    static public String deHTMLedContent(String content)
    {
        content = content.replaceAll(Configuration.noteReg,"");
        content = content.replaceAll(Configuration.newlineReg,"");
        content = content.replaceAll("( )+", " ");
        String noHeadContent = content.replaceAll(Configuration.headReg,"");
        String noScriptContent = noHeadContent.replaceAll(Configuration.scriptReg,"");
        String noHtmlContent = noScriptContent.replaceAll(Configuration.tagReg,"");
        return noHtmlContent;
    }

    /**
     * ����ҳ�����л��email
     * @param content ��ҳ����
     * @param webUrl ��ҳ��Ӧ��URL
     */
    static public void getEmailsFromContent(String content, WebUrl webUrl)
    {
        Pattern p = Pattern.compile(Configuration.emailReg, Pattern.DOTALL);
        Matcher m = p.matcher(content);
        while (m.find())
        {
        	String emailString = m.group(1);
        	// do things
            add(emailString, ContentType.TYPE_EMAIL, webUrl.getUrlString());
        }
    }
    
    /**
     * ����ҳ�����л���ֻ�����
     * @param content ��ҳ����
     * @param webUrl ��ҳ��Ӧ��URL
     */
    static public void getPhonesFromContent(String content, WebUrl webUrl)
    {
    	Pattern p = Pattern.compile(Configuration.phoneReg, Pattern.DOTALL);
    	Matcher m = p.matcher(content);
        while (m.find())
        {
        	String phoneString = m.group(1);
        	// do things
            add(phoneString, ContentType.TYPE_PHONE, webUrl.getUrlString());
        }
    }
    
    /**
     * ��URL����ҳ����д���ļ���
     * @param webUrl URL��Ϣ
     * @param content URL��Ӧ����ҳ����
     * @param charSet ��ҳ��charset
     * @param htmlDownloaded ��ǰ������
     */
    static public void writeContentToFile(WebUrl webUrl, String content, String charSet, int htmlDownloaded)
    {
        if (content != "")
        {
            try
            {
                // write page content
                String fileName = Configuration.savePath + "HTMLs/" + htmlDownloaded + "_" + webUrl.getUrlString().replaceAll("http://", "").replaceAll("/", "_") + ".content";
                if (fileName.length() >= 105)
                    fileName = fileName.substring(0, 99) + ".content";
                File file = new File(fileName);
                file.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), charSet);
                out.write(content);
                out.close();
            }
            catch (IOException e)
            {
                System.out.println("--IOException in output new file: " + e.getMessage());
            }
        }
    }

    /**
     * ������������Ϣ����URL��Ӧ����Ϣ����
     * @param str ����������Ϣ
     * @param type ��Ϣ������
     * @param formUrl ��ӦURL
     */
    static public void add(String str, ContentType type, String formUrl)
	{
		switch (type)
		{
			case TYPE_EMAIL:
				if (emailMap.get(str) == null)
				{
					emailMap.put(str, formUrl);
					contentArray[0].add(str);
					contentArray[0].add(formUrl);
				}
				break;
			case TYPE_PHONE:
				if (phoneMap.get(str) == null)
				{
					phoneMap.put(str, formUrl);
					contentArray[1].add(str);
					contentArray[1].add(formUrl);
				}
				break;
			case TYPE_DOC:
				if (docMap.get(str) == null)
				{
					docMap.put(str, formUrl);
					contentArray[2].add(str);
					contentArray[2].add(formUrl);
				}
				break;
			case TYPE_PIC:
				if (picMap.get(str) == null)
				{
					picMap.put(str, formUrl);
					contentArray[3].add(str);
					contentArray[3].add(formUrl);
				}
				break;
			case TYPE_AUDIO:
				if (audioMap.get(str) == null)
				{
					audioMap.put(str, formUrl);
					contentArray[4].add(str);
					contentArray[4].add(formUrl);
				}
				break;
			case TYPE_TORRENT:
				if (torrentMap.get(str) == null)
				{
					torrentMap.put(str, formUrl);
					contentArray[5].add(str);
					contentArray[5].add(formUrl);
				}
				break;
			case TYPE_OTHER:
				if (otherMap.get(str) == null)
				{
					otherMap.put(str, formUrl);
					contentArray[6].add(str);
					contentArray[6].add(formUrl);
				}
				break;
		}
	}
    
    /**
     * �������������ݻ��ܣ�д��ָ���ļ���
     * @param fileName ָ���ļ�
     */
	static public void writeInfoToFile(String fileName)
	{
		File file;
		PrintWriter pw = null;
		
		// write source info
		for (int i = 0; i < contentArray.length; ++i)
		{
			try
			{
				file = new File(fileName + contentType[i]);
				file.createNewFile();
				pw = new PrintWriter(new FileOutputStream(file));

				pw.println(contentArray[i].size());
				for (String j : contentArray[i])
					pw.println(j);
			}
			catch (IOException e)
			{
				System.out.println("--IOException in output new file: " + e.getMessage());
			}
			finally{pw.close();}
		}
		
		// sort pagerank
		Set<Entry<String, Double>> set = urlQueue.pageRank();
		Object arr[] = set.toArray();
		ArrayList<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>();
		for (Object obj : arr)
		{
			list.add((Map.Entry<String, Double>)obj);
		}
		setComparator comp = new setComparator();
		Collections.sort(list, comp);
	
		// write pagerank info
		file = new File(fileName + "_pageRank");
		int no = 1;
		try
		{
			file.createNewFile();
			pw = new PrintWriter(new FileOutputStream(file));
			pw.println(set.size());
			for (Entry<String, Double> entry : list)
				pw.println((no++) + " " + entry.getValue() + " <-> " + entry.getKey());
		} 
		catch (IOException e)
		{
			System.out.println("--IOException in output new file: " + e.getMessage());
		}
		finally{pw.close();}
		
	}
	
	/**
	 * ���캯��
	 * @param urlQueue
	 */
    public Parser(UrlQueue urlQueue)
    {
        Parser.urlQueue = urlQueue;
		contentArray = new ArrayList[contentType.length];
		for (int i = 0; i < contentType.length; ++i)
		{
			contentArray[i] = new ArrayList<String>();
		}
    }
}

/**
 * ��ҳ���������ñȽ���
 */
class setComparator implements Comparator<Map.Entry<String, Double>>
{
	public int compare(Map.Entry<String, Double> one, Map.Entry<String, Double> two)
	{
		double value1 = one.getValue().doubleValue();
		double value2 = two.getValue().doubleValue();
		if (value1 < value2)
			return 1;
		else if (value1 == value2)
			return 0;
		else 
			return -1;
	}
}
