package cn.heicaijun.Laver.util;

import cn.heicaijun.Laver.Message.Message;

public class CheckConfigUtility {
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
}
