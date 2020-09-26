package cn.heicaijun.Laver;

import cn.heicaijun.Laver.view.MainFrm;
import cn.heicaijun.Laver.view.RunInCmd;

public class Laver {
	public static final String VERSION = "v0.1";
	public static final int RUN_IN_CMD = 0;
	public static final int RUN_IN_UI = 1;
	//设置工作模式为运行UI
	public static int workMode = RUN_IN_UI;
	
	public static void main(String[] args){
		if (workMode == RUN_IN_CMD) {
			RunInCmd.run(args);
		}else if (workMode == RUN_IN_UI) {
			MainFrm.run(args);
		}
	}
}