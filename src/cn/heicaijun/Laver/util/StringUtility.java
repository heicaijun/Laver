package cn.heicaijun.Laver.util;

public class StringUtility {
	// 所有分割字符的正则表达式[ ] ( ) \ / | . _ - （ ）以及空格" "
	public final static String DEPART_CHAR_REGIX = "[\\|/\\[_\\-\\(\\)\\. （）\\]\\\\]";
	
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}else {
			return false;
		}
	}
	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}else {
			return true;
		}
	}
	/**
	 * 用于将字符串用[',' '，' ';' '、' '\' '/']等字符分开成字符串数组，主要用于用户自行输入的一些文件格式后缀
	 * @param str 输入的字符串".png,.svg,.jepg"等
	 * @return String[]返回分离的字符数组{".png", ".svg", ".jepg"}
	 */
	public static String[] splitStringToStrings(String str) {
		String[] result = null;
		if (StringUtility.isNotEmpty(str)) {
			if (str.contains(",")) {
				result = str.split(",");
			}else if (str.contains("，")) {
				result = str.split("，");
			}else if (str.contains(";")) {
				result = str.split(";");
			}else if (str.contains("、")) {
				result = str.split("、");
			}else {
				result = new String[1];
				result[0] = str;
			}
		}
		return result;
	}
	/**
	 * 将字符串转换为数字的int数组
	 * @param str
	 * @return
	 */
	public static int[] stringToIntArray(String str) { 
		// 如果字符串为空,或者不为数字与字符的结合返回null
		if (isEmpty(str) || !isNumberOrSymbol(str)) {
			return null;
		}
		if (isEmpty(str.replaceAll("[0-9]", ""))) {
			// 如果字符串只有数字，返回长度为1的int数组
			int[] result = new int[1];
			result[0] = Integer.parseInt(str);
			return result;
		}else if (isNumberOrSymbol(str)) {
			// 如果含分割符，则将之拆分成int数组
			String[] strArray = str.split(DEPART_CHAR_REGIX);
			// 剔除数组中的空串""。
			strArray = removeNullStrInArray(strArray);
			int[] result = new int[strArray.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = Integer.parseInt(strArray[i]);
			}
			return result;
		}
		return null;
	}
	/**
	  * 字符串小写并除去空白字符：该方法可用于处理字符串，删除空格、制表符、换页符等空白字符，然后将之转为小写
	 * @param str	输入的字符串
	 * @return	处理后的字符串
	 */
	public static String strLowercaseWithoutBlank(String str) {
		// 去除所有的空白字符
		String result = str.replaceAll("\\s*", "");
		// 将string转为小写
		result = result.toLowerCase();
		return result;
	}
	/**
	 * 判断一个字符串是否为数字与连接字符的[ ] ( ) \ / | . _ - （ ）组合
	 * @param str
	 * @return
	 */
	public static boolean isNumberOrSymbol(String str) {
		// 去除所有的[ ] ( ) \ / | . _ - （ ）连接符，
		String bufferStr = str.replaceAll(DEPART_CHAR_REGIX, "");
		// 去除所有数字，如果长度为0则返回true
		bufferStr = bufferStr.replaceAll("[0-9]", "");
		if (bufferStr.length() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 去除String数组中所有空字符串
	 * @param str
	 * @return
	 */
	public static String[] removeNullStrInArray(String[] str) {
		int realLength = 0;
		for (String string : str) {
			if (isNotEmpty(string)) {
				realLength++;
			}
		}
		String[] result = new String[realLength];
		int i = 0;
		for (String string : str) {
			if (isNotEmpty(string)) {
				result[i] = string;
				i++;
			}
		}
		return result;
	}
}
