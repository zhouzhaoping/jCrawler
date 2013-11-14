package jCrawler.crawler;

import java.util.ArrayList;

/**
 * 保存程序变量、常量
 */
public class Configuration
{
	public final static String urlReg = "http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	
	public final static String charSetReg = "<meta[^>]*?charset=\"?(\\w+)\"?[\\W]*?>";
	public final static String hrefReg = "<a[^>]*href=\\s*\"(([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))\"[^>]*>(.*?)</a>";
    public final static String noteReg = "^//.*";
    public final static String newlineReg = "[\n\r\f\t]";
    public final static String headReg = "<head>.*?</head>";
    public final static String scriptReg = "<script.*?</script>";
    public final static String tagReg = "<[^>]*>";
    public final static String emailReg = "((\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+))";
    public final static String phoneReg = "(((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8})";
    
	public static int maxThread = 8;
	public static int maxHtmlDownloaded = 200;
	public static String savePath = "";
	
	/**
	 * 配置
	 * @param _maxThread 最大线程数
	 * @param _maxHtmlDownloaded 最大下载数
	 */
	public static void setConfiguration(int _maxThread, int _maxHtmlDownloaded)
	{
		maxThread = _maxThread;
		maxHtmlDownloaded = _maxHtmlDownloaded;
	}
	
	public final static double weight = 1.0;

	public static ArrayList<String> srcWebUrl = new ArrayList<String>();
	
	public enum ContentType
	{
		TYPE_URL, TYPE_EMAIL, TYPE_PHONE, TYPE_DOC, TYPE_PIC, TYPE_AUDIO, TYPE_TORRENT, TYPE_OTHER
	}
}
