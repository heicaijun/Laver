package cn.heicaijun.Laver.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;

import cn.heicaijun.Laver.bean.ConfigBean;
import cn.heicaijun.Laver.bean.FileBean;

public class FileUtility {
	
	public final static int IS_SAME_FILE = 1;
	public final static int CANT_COMPARE = 0;
	public final static int NOT_SAME_FILE = -1;
	
	
	
	public static void writeFileBeansToCSV(ArrayList<FileBean> fileBeans, File output) {
		writeByBufferedWriter(fileBeans, output);
	}
	
	private static void writeByBufferedWriter(ArrayList<FileBean> fileBeans, File output) {
		/**
		 * 在JDK7中，可以将流对象的定义放在try()中，
		 * 这个流对象作用域就在try中有效
		 * try中的代码执行完后，会自动将流对象释放，无需写finally
		 * 格式:
		 * 		try(定义流对象;定义流对象……) {
		 * 			可能产生异常的代码
		 * 		}catch(异常类 变量名){
		 * 			异常的处理逻辑
		 * 		}
		 */
		//由于Excel打开时默认使用当前系统的字符集打开，所以需要先获取当前系统的字符集
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(output),
				System.getProperty("sun.jnu.encoding"));				
				BufferedWriter bufferedWriter = new BufferedWriter(osw);){
			bufferedWriter.write("父目录,文件名,完整路径");
			bufferedWriter.newLine();
			for (FileBean fileBean : fileBeans) {
				System.out.println(fileBean.toString());
				bufferedWriter.write(fileBean.toString());
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 检查路径是否正确
	public static boolean checkPath(String path) {
		if (StringUtility.isNotEmpty(path)) {
			if (new File(path).isDirectory()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 对ConfigBean配置文件中的目录进行扫描。
	 * @param configBean	配置档，包含扫描的目录等信息
	 * @return	FileBean	扫描结果，包含文件名，路径等信息
	 */
	public static ArrayList<FileBean> traversalFolder(ConfigBean configBean) {
		// 获取文件过滤器，滤掉不需要扫描的文件类型
		FilenameFilter filenameFilter = FileFormatUtility.getFilenameFilter(configBean.getSearchFormat(),
							StringUtility.splitStringToStrings(configBean.getOtherFormatStr().toLowerCase()));
		
		// 递归调用traversal函数,对目录进行扫描
		return traversalFolder(configBean.getRootPath(),
				configBean.getDepth(),
				configBean.getMode(), 
				configBean.getHiddenYN(),
				StringUtility.splitStringToStrings(configBean.getUserDefineMode()),
				filenameFilter);
	}
	/**
	 * 对文件进行扫描的关键函数
	 * @param florderFile : 需要扫描的目录
	 * @param depth : 需要扫描的深度
	 * @param mode  : 扫描的模式，0代表全部列出，1表示只列出最新版的
	 * @param hiddenYN	：是否扫描隐藏文件
	 * @return ArrayList<FileBean> : 是由文件信息组成的数组集合。
	 */
	private static ArrayList<FileBean> traversalFolder(File florderFile, 
			int depth,
			int mode, 
			Boolean hiddenYN, 
			String[] userDefineMode,
			FilenameFilter filenameFilter) {
		ArrayList<FileBean> resultArrayList = new ArrayList<FileBean>();
		ArrayList<FileBean> fileBufferArrayList = new ArrayList<FileBean>();
		
		if (depth == -1) {
			return resultArrayList;
		}else {
			File[] files = florderFile.listFiles(filenameFilter);
			if (files == null) {
				return resultArrayList;
			}
			for (File file : files) {
				if (file.isFile()) {
					FileBean fileBean = null;
					if (hiddenYN) {
						fileBean = new FileBean(file.getName(),
								file.getParentFile().getName(), 
								file.getAbsolutePath());
					}else if (!file.isHidden()) {
						fileBean = new FileBean(file.getName(),
												file.getParentFile().getName(), 
												file.getAbsolutePath());
					}
					if (fileBean != null)
						fileBufferArrayList.add(fileBean);
				}else if (file.isDirectory()) {
					resultArrayList.addAll(traversalFolder(file, 
							depth - 1,
							mode, 
							hiddenYN,
							userDefineMode, 
							filenameFilter));
				}	
			}
			
			if (mode == ConfigBean.CHECK_ALL_MODE) {
				// 如果扫描模式为全扫描
				resultArrayList.addAll(fileBufferArrayList);	
				return resultArrayList;
			}else if (mode == ConfigBean.ONLY_LASTEST_MODE) {
				// 如果扫描模式为最新版本
				fileBufferArrayList = getLastedFileList(fileBufferArrayList,
						SearchModeUtility.DEFAULT_VERSION_TAG);
				resultArrayList.addAll(fileBufferArrayList);	
				return resultArrayList;
			}else {
				// 如果扫描模式为用户自定义
				if (userDefineMode == null) {
					resultArrayList.addAll(fileBufferArrayList);
				}else {
					fileBufferArrayList = getLastedFileList(fileBufferArrayList,
							userDefineMode);
					resultArrayList.addAll(fileBufferArrayList);
				}
				return resultArrayList;
			}
			
		}
	}
	/**
	 * 将文件的列表中的旧版本剔除，只留下最新版
	 * @param fileBufferArrayList	:	版本的
	 * @param versionTag
	 * @return
	 */
	private static ArrayList<FileBean> getLastedFileList(ArrayList<FileBean> fileBufferArrayList,
			String[] versionTag) {
		// 如果文件夹下无文件,或者只有一个文件，则不进行比对
		if (fileBufferArrayList.isEmpty() || fileBufferArrayList.size() == 1) {
			return fileBufferArrayList;
		}
		ArrayList<FileBean> resultArrayList = new ArrayList<FileBean>();
		// 先对文件进行排序,字典顺序大的放后面
		fileBufferArrayList.sort(new Comparator<FileBean>() {

			@Override
			public int compare(FileBean fileBean1, FileBean fileBean2) {
				return fileBean1.getFileName().compareToIgnoreCase(
													fileBean2.getFileName());
			}
			
		});
		for (int i = 1; i < fileBufferArrayList.size(); i++) {
			int compareFlag = NOT_SAME_FILE;
			// 获取相邻两个file的名字
			String fileNameA = fileBufferArrayList.get(i - 1).getFileName().toLowerCase();
			String fileNameB = fileBufferArrayList.get(i).getFileName().toLowerCase();
			for (String versionStr : versionTag) {
				versionStr = versionStr.toLowerCase();
				// 判断是否为相同文件
				if (isSameFile(fileNameA, fileNameB, versionStr) == IS_SAME_FILE) {
					String compareResult = compareFileVersion(fileNameA, fileNameB, versionStr);
					if (compareResult == null) {
						// 比较出错时继续下一轮循环
						continue;
					}else if (compareResult == fileNameA) {
						// 如果是A的版本号较高则将A与B的位置调换，使最新版的始终在后面
						FileBean bufferFileBean = fileBufferArrayList.get(i - 1);
						fileBufferArrayList.set(i - 1, fileBufferArrayList.get(i));
						fileBufferArrayList.set(i, bufferFileBean);
					}
					compareFlag = IS_SAME_FILE;
					break;
				}
			}
			if (compareFlag != IS_SAME_FILE) {
				if (i == fileBufferArrayList.size() -1) {
					// 如果已经检索到最末尾了，则将两个文件都添加到结果数组中
					resultArrayList.add(fileBufferArrayList.get(i-1));
					resultArrayList.add(fileBufferArrayList.get(i));
				}else {
					// 如果不是相同文件，则将索引小的添加到结果数组中
					resultArrayList.add(fileBufferArrayList.get(i-1));
				}
			}else {
				if (i == fileBufferArrayList.size() -1) {
					// 如果已经检索到最末尾了，则将索引值大(也即版本号大的)的添加到结果数组中
					resultArrayList.add(fileBufferArrayList.get(i));
				}
			}
		}
		return resultArrayList;
	}
	/**
	 * 判断2个文件名是否为同一类文件的2个不同版本。比如，产品说明书ver_1.2.3.pdf 与  产品说明书ver_1.3.4.pdf 即为同一文件的不同版本
	 * @param fileA	:文件A的文件名
	 * @param fileB	:文件B的文件名
	 * @param splitRule	：比对用的规则数组(为版本标识符的集合，如["ver", "v"])
	 * @return	FileUtility.CANT_COMPARE: 代表无法比较
	 * @return	FileUtility.NOT_SAME_FILE: 代表不是同一个文件
	 * @return	FileUtility.IS_SAME_FILE: 代表是同一文件
	 */
	public static int isSameFile(String fileA, String fileB, String splitRule) {
		// 如果两者任意一个不含拆分规则，则返回CANT_COMPARE
		if (!fileA.toLowerCase().contains(splitRule.toLowerCase()) || 
				!fileB.toLowerCase().contains(splitRule.toLowerCase())) {
			return CANT_COMPARE;
		}
		int endOfFileIndexA = fileA.lastIndexOf(".");
		int endOfFileIndexB = fileB.lastIndexOf(".");
		if (!fileA.substring(endOfFileIndexA)
				.equalsIgnoreCase(fileB.substring(endOfFileIndexB))) {
			return NOT_SAME_FILE;
		}
		int startOfVersionIndexA = fileA.lastIndexOf(splitRule) + splitRule.length();
		int startOfVersionIndexB = fileB.lastIndexOf(splitRule) + splitRule.length();
		// 获取文件剔除版本号后的文件名
		String fileRealNameA = StringUtility.strLowercaseWithoutBlank(
				fileA.substring(0, startOfVersionIndexA));
		String fileRealNameB = StringUtility.strLowercaseWithoutBlank(
				fileB.substring(0, startOfVersionIndexB));
		// 获取文件的版本号
		String fileVersionNameA = fileA.substring(startOfVersionIndexA, endOfFileIndexA);
		String fileVersionNameB = fileA.substring(startOfVersionIndexB, endOfFileIndexB);
		// 对版本号进行判断，是否为数字与连接符的组合
		if (fileRealNameA.equalsIgnoreCase(fileRealNameB)) {
			if (StringUtility.isNumberOrSymbol(fileVersionNameA) && 
					StringUtility.isNumberOrSymbol(fileVersionNameB)) {
				return IS_SAME_FILE;
			}else {
				return NOT_SAME_FILE;
			}
		}
		return CANT_COMPARE;
	}
	/**
	 * 比较文件的版本号
	 * @param fileA	:需要比较的文件A的文件名
	 * @param fileB	:需要比较的文件B的文件名
	 * @param splitRule	:拆分的规则
	 * @return	fileA,fileB中文件版本号更高的那个，或者如果无法比较则返回null
	 */
	public static String compareFileVersion(String fileA, String fileB, String splitRule) {
		int endOfFileIndexA = fileA.lastIndexOf(".");
		int endOfFileIndexB = fileB.lastIndexOf(".");
		int startOfVersionIndexA = fileA.lastIndexOf(splitRule) + splitRule.length();
		int startOfVersionIndexB = fileB.lastIndexOf(splitRule) + splitRule.length();
		// 获取文件的版本号
		String fileVersionNameA = fileA.substring(startOfVersionIndexA, endOfFileIndexA);
		String fileVersionNameB = fileB.substring(startOfVersionIndexB, endOfFileIndexB);
		int[] versionNoA = StringUtility.stringToIntArray(fileVersionNameA);
		int[] versionNoB = StringUtility.stringToIntArray(fileVersionNameB);
		if (versionNoA == null || versionNoB == null) {
			return null;
		}else {
			for (int i = 0; i < Math.min(versionNoA.length, versionNoB.length); i++) {
				if (versionNoA[i] < versionNoB[i]) {
					return fileB;
				}else if (versionNoA[i] > versionNoB[i]) {
					return fileA;
				}
			}
			if (versionNoA.length > versionNoB.length) {
				return fileA;
			}else if (versionNoA.length < versionNoB.length) {
				return fileB;
			}else {
				return null;
			}
		}
	}
	/**
	 * 将字符串删除空白字符,并小写化
	 * @param file	需要查询的文件
	 * @return	转换后的String
	 */
	public static String getFileNameFomated(String fileName) {
		// 获取文件名且将之删除空白字符串并小写化
		return StringUtility.strLowercaseWithoutBlank( fileName.substring(0, fileName.lastIndexOf(".")) );
	}
}
