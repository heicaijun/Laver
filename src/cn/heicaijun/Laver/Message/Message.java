package cn.heicaijun.Laver.Message;

import java.awt.Component;
import java.awt.Frame;

import cn.heicaijun.Laver.Laver;
import cn.heicaijun.Laver.view.CommonDialog;

public class Message {
	public static void showMsg(String message) {
		if (Laver.workMode == Laver.RUN_IN_CMD) {
			System.out.println(message);
		}else if (Laver.workMode == Laver.RUN_IN_UI) {
			CommonDialog.showMsg(message);
		}
	}
	public static void showMsg(String msg, String msgType, Frame owner, Component parentComponent) {
		CommonDialog.showMsg(msg, msgType, owner, parentComponent);
	}
}
