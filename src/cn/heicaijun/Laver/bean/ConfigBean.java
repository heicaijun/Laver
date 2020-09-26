package cn.heicaijun.Laver.bean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import cn.heicaijun.Laver.util.StringUtility;

public class ConfigBean implements Serializable{

	/**
	 * 可以序列化，以便存储配置文件，用户下次打开软件的时候可以重新加载配置，而不用自行配置。
	 */
	private static final long serialVersionUID = 1L;
	
	public final static int ALL_SELECTOR = 0;
	public final static int XML_SELECTOR = 1;
	public final static int WORD_SELECTOR = 2;
	public final static int PPT_SELECTOR = 3;
	public final static int EXCEL_SELECTOR = 4;
	public final static int PDF_SELECTOR = 5;
	public final static int OTHER_SELECTOR = 6;
	
	public final static int CHECK_ALL_MODE = 7;
	public final static int ONLY_LASTEST_MODE = 8;
	public final static int USER_DEFINE_MODE = 9;
	
	
	final static String DEFAULT_OUTPUT_FILENAME = "VersionList";
	private File rootPath = null;
	private File outputPath = null;
	private Set<Integer> searchFormat = null;
	private int depth = -5;
	private int mode = 0;
	private String otherFormatStr = "";
	private String userDefineMode = "";
	private boolean hiddenYN = false;

	public String getOtherFormatStr() {
		return otherFormatStr;
	}
	public void setOtherFormatStr(String otherFormatStr) {
		this.otherFormatStr = otherFormatStr;
	}
	public ConfigBean() {
		// 序列化需要无参构造方法
		super();
	}
	public ConfigBean(String rootPathString,
			Set<Integer> searchFormat,
			int depth,
			int mode,
			boolean hiddenYN) {
		this.rootPath = new File(rootPathString);
		this.setSearchFormat(searchFormat);
		if (depth == 0) {
			//由于递归时depth为-1会停止递归，所以用-5来区分。
			this.depth = -5;
		}else {
			this.depth = depth;
		}
		this.mode = mode;
		this.hiddenYN = hiddenYN;
		if (StringUtility.isNotEmpty(rootPathString)) {
			this.outputPath = new File(rootPathString +
					File.separator +
					DEFAULT_OUTPUT_FILENAME +
					new SimpleDateFormat("_yyMMdd_HHmmss").format(new Date()) +
					".csv");
		}
	}
	public File getRootPath() {
		return rootPath;
	}
	public void setRootPath(File rootPath) {
		this.rootPath = rootPath;
	}
	public File getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(File outputPath) {
		this.outputPath = outputPath;
	}
	public int getDepth() {
		return depth ;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public Set<Integer> getSearchFormat() {
		return searchFormat;
	}
	public void setSearchFormat(Set<Integer> searchFormat) {
		this.searchFormat = searchFormat;
	}
	public String getUserDefineMode() {
		return userDefineMode;
	}
	public void setUserDefineMode(String userDefineMode) {
		this.userDefineMode = userDefineMode;
	}
	public boolean getHiddenYN() {
		return hiddenYN;
	}
	public void setHiddenYN(boolean hiddenYN) {
		this.hiddenYN = hiddenYN;
	}

}
