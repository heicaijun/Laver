package cn.heicaijun.Laver.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;
import cn.heicaijun.Laver.bean.ConfigBean;

public class FileFormatUtility {
	
	private final static String[] XML = {".xml"};
	private final static String[]  PDF = {".pdf"};
	private final static String[]  WORD = {
			".doc",
			".docx",
			".docm",
			".dot",
			".dotx",
			".dotm"
	};
	private final static String[]  PPT = {
			".ppt",
			".pptx",
			".pptm",
			".pot",
			".potx",
			".potm"
	};
	private final static String[]  EXCEL = {
			".xlsx",
			".xls",
			".xlsm",
			".xlsb",
			".xltx",
			".xlt",
			".xltm",
			".csv"
	};
	
	// 默认全扫描
	private static FilenameFilter filenameFilter = new allFilterImpl();
	/**
	 * 用于获取FilenameFilter文件过滤器的方法。
	 * @param selections	配置档的选项。ConfigBean.ALL_SELECTOR，ConfigBean.XML_SELECTOR等，对应了不同的选择器
	 * @param otherFormatStrings	其他的一些用户自定义的格式
	 * @return
	 */
	public static FilenameFilter getFilenameFilter(Set<Integer> selections, String[] otherFormatStrings) {
		// 缓存文件格式的Set
		Set<String> fileFormatSet = new HashSet<>();
		for (int select : selections) {
			switch (select) {
				case ConfigBean.ALL_SELECTOR:
					filenameFilter = new allFilterImpl();
					return filenameFilter;
				case ConfigBean.XML_SELECTOR:
					for (String format : XML) {
						fileFormatSet.add(format);
					}
					break;
				case ConfigBean.WORD_SELECTOR:
					for (String format : WORD) {
						fileFormatSet.add(format);
					}
					break;
				case ConfigBean.PPT_SELECTOR:
					for (String format : PPT) {
						fileFormatSet.add(format);
					}
					break;
				case ConfigBean.EXCEL_SELECTOR:
					for (String format : EXCEL) {
						fileFormatSet.add(format);
					}
					break;
				case ConfigBean.PDF_SELECTOR:
					for (String format : PDF) {
						fileFormatSet.add(format);
					}
					break;
				case ConfigBean.OTHER_SELECTOR:
					if(otherFormatStrings != null) {
						for (String format : otherFormatStrings) {
							fileFormatSet.add(format);
						}
						
					}
					break;
				default:
					break;
			}
		}
		filenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (new File(dir, name).isDirectory())
					return true;
				for (String str : fileFormatSet) {
					if (name.toLowerCase().endsWith(str)) {
						return true;
					}
				}
				return false;
			}
		};
		return filenameFilter;
	}

}

class allFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return true;
	}
}