package cn.heicaijun.Laver;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.heicaijun.Laver.Message.Message;
import cn.heicaijun.Laver.bean.FileBean;

public class FileManeger {
	static String outputFileNameString = "VersionList";
	private File rootPath = null;
	private File outputFile = null;
	private FilenameFilter filenameFilter = null;
	
	public FileManeger(String inputPath, String outputPath, int fileFormat){
		this.setRootPath(new File(inputPath));
		this.setOutput(new File(outputPath,outputFileNameString + 
				new SimpleDateFormat("_yyMMdd_HHmmss").format(new Date()) + 
				".csv"));
		this.filenameFilter = filenameFilterSelector(fileFormat);
	}
	/**
	 * 检查输入的路径是否合法
	 * checkPath
	 * @throws PathIllegle 
	 */
	public boolean checkPath() {
		if(!getRootPath().isDirectory()) {
			Message.showMsg("您输入的路径不正确，请重新输入:" + getRootPath());
			return false;
		}else if (!getRootPath().isAbsolute()) {
			Message.showMsg("您输入的路径不是绝对路径，请重新输入:" + getRootPath());
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 检查输入的格式是否在允许的格式范围
	 * @param format :扫描的文件
	 * @return :true表示格式正确，false是错误
	 */
	public static boolean checkFomat(int format) {
		if (format < 0 || format > 5) {
			Message.showMsg("您输入的扫描格式不在允许范围(0~4)：" + format + "，请重新输入：");
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 检查输入的depth格式是否在允许的格式范围0~n
	 * @param depth :扫描深度
	 * @return :true表示格式正确，false是错误
	 */
	public static boolean checkDepth(int depth) {
		if (depth < 0 ) {
			Message.showMsg("您输入的深度不在允许范围(0~n)：" + depth + "，请重新输入：");
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 对文件进行扫描的关键函数
	 * @param FlorderFile : 需要扫描的目录
	 * @param depth : 需要扫描的深度
	 * @param mode  : 扫描的模式，0代表全部列出，1表示只列出最新版的
	 * @return ArrayList<FileBean> : 是由文件信息组成的数组集合。
	 */
	public ArrayList<FileBean> traversalFolder(File FlorderFile, int depth, int mode) {
		ArrayList<FileBean> resultArrayList = new ArrayList<FileBean>();
		
		if (depth == 0) {
			return resultArrayList;
		}else {
			File[] files = FlorderFile.listFiles(filenameFilter);
			for (File file : files) {
				if (file.isFile()) {
					FileBean fileBean = new FileBean(file.getName(),file.getParentFile().getName(), file.getAbsolutePath()) ;
					resultArrayList.add(fileBean);
				}else if (file.isDirectory()) {
					resultArrayList.addAll(traversalFolder(file, depth - 1, mode));
				}
			}
			return resultArrayList;
		}
	}
	/**
	 * filemameFilter选择器，用于将输入映射成一个Fileter[0.all,1.xml,2.word,3.excel,4.ppt,5.pdf]
	 * @param fileFormat : 格式输入0-5
	 * @return	FilenameFilter : 一个FilenameFilter
	 */
	private FilenameFilter filenameFilterSelector(int fileFormat) {

		FilenameFilter filenameFilter = null;
		switch (fileFormat) {
		case 0:
			filenameFilter = new allFilterImpl();	
			break;
		case 1:
			filenameFilter = new xmlFilterImpl();
			break;
		case 2:
			filenameFilter = new wordFilterImpl();
			break;
		case 3:
			filenameFilter = new pptFilterImpl();
			break;
		case 4:
			filenameFilter = new excelFilterImpl();
			break;
		case 5:
			filenameFilter = new pdfFilterImpl();
			break;
		default:
			filenameFilter = new allFilterImpl();
			break;
		}
		return filenameFilter;
		
	}
	public File getRootPath() {
		return rootPath;
	}
	public void setRootPath(File input) {
		this.rootPath = input;
	}
	public File getOutput() {
		return outputFile;
	}
	public void setOutput(File output) {
		this.outputFile = output;
	}
}



class allFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return true;
	}
}

class pdfFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return new File(dir, name).isDirectory() 
				|| name.toLowerCase().endsWith(".pdf");
	}
}

class xmlFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return new File(dir, name).isDirectory() 
				|| name.toLowerCase().endsWith(".xml");
	}
}
class wordFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return new File(dir, name).isDirectory() 
				|| name.toLowerCase().endsWith(".doc")
				|| name.toLowerCase().endsWith(".docx")
				|| name.toLowerCase().endsWith(".docm")
				|| name.toLowerCase().endsWith(".dot")
				|| name.toLowerCase().endsWith(".dotx")
				|| name.toLowerCase().endsWith(".dotm");
	}
}

class pptFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return new File(dir, name).isDirectory() 
				|| name.toLowerCase().endsWith(".ppt")
				|| name.toLowerCase().endsWith(".pptx")
				|| name.toLowerCase().endsWith(".pptm")
				|| name.toLowerCase().endsWith(".pot")
				|| name.toLowerCase().endsWith(".potx")
				|| name.toLowerCase().endsWith(".potm");
	}
}

class excelFilterImpl implements FilenameFilter {
	public boolean accept(File dir,String name) {
		return new File(dir, name).isDirectory() 
				|| name.toLowerCase().endsWith(".xlsx")
				|| name.toLowerCase().endsWith(".xls")
				|| name.toLowerCase().endsWith(".xlsm")
				|| name.toLowerCase().endsWith(".xlsb")
				|| name.toLowerCase().endsWith(".xltx")
				|| name.toLowerCase().endsWith(".xlt")
				|| name.toLowerCase().endsWith(".xltm")
				|| name.toLowerCase().endsWith(".csv");
	}
}