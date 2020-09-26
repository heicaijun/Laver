package cn.heicaijun.Laver.view;

import java.util.ArrayList;
import java.util.Scanner;

import cn.heicaijun.Laver.FileManeger;
import cn.heicaijun.Laver.bean.FileBean;
import cn.heicaijun.Laver.util.FileUtility;

public class RunInCmd {
	
	private static Scanner sc;
	
	public static void run(String[] args) {
		// TODO Auto-generated method stub
		int fileFormat,depth;
		String rootPath = null;
		FileManeger fileManeger = null;
		sc = new Scanner(System.in);
		System.out.print("请输入您要扫描的格式[0.all;1.xml;2.word;3.ppt;4.excel;5.pdf]:");
		do {
			fileFormat = sc.nextInt();
		} while (!FileManeger.checkFomat(fileFormat));
		//用于接收多余的回车
		sc.nextLine();
		System.out.print("请输入您要扫描的路径:");
		do {
			rootPath = sc.nextLine();
			String outputPath = rootPath;
			fileManeger = new FileManeger(rootPath, outputPath, fileFormat);
		}while (!fileManeger.checkPath());
		
		System.out.print("请输入您要扫描的深度[0.所有子目录;1.一级目录;2~n.2~n级目录]:");
		do {
			depth = sc.nextInt();
		} while (!FileManeger.checkDepth(depth));
		
		sc.close();
		//由于递归时depth为0会停止递归，所以用-2来区分。
		if (depth == 0) 
			depth = -2;	
		//由于输入的目录层级为主目录，理解上来说一级子目录应该为2，所以对depth+1
		ArrayList<FileBean> fileBeans = fileManeger.traversalFolder(fileManeger.getRootPath(), depth + 1, 0);
		FileUtility.writeFileBeansToCSV(fileBeans, fileManeger.getOutput());
	}
}
