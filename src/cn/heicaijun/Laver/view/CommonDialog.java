package cn.heicaijun.Laver.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.heicaijun.Laver.util.ImageUtility;

import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;

public class CommonDialog {
	private final static int IMAGE_WIDTH = 40;
	private final static int IMAGE_HEIGHT = 40;
	
	public final static String PROMPT = "PROMPT-提示";
	public final static String WARNING = "WARNING-警告";
	public final static String ERROR = "ERROR-错误";
	/**
	 * 通用对话框，用于显示简单提示
	 * @param msg
	 */
	public static void showMsg(String msg) {
		final JDialog dialog = new JDialog();
		dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(CommonDialog.class.getResource("/images/icons/laver.png")));
		// 设置对话框的宽高
		dialog.setSize(250, 150);

        // 设置对话框大小不可改变
		dialog.setResizable(true);
        // 创建一个标签显示消息内容
		JLabel messageLabel = new JLabel(msg);

        // 创建一个按钮用于关闭对话框
		JButton okBtn = new JButton("确认");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // 关闭对话框
				dialog.dispose();
			}
		});

        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
		JPanel panel = new JPanel();

        // 添加组件到面板
		panel.add(messageLabel);
		panel.add(okBtn);

        // 设置对话框的内容面板
		dialog.setContentPane(panel);

        // 显示对话框
		dialog.setVisible(true);
	}
	/**
	 * 显示一个自定义的对话框
	 * @param msg 需要显示的信息
	 * @param msgType 为消息类型，可用CommonDialog.PROMPT,CommonDialog.WARNING,CommonDialog.ERROR来表示提示框的严重等级
	 * @param owner 对话框的拥有者
	 * @param parentComponent 对话框的父级组件
	 * @wbp.parser.entryPoint
	 */
	public static void showMsg(String msg, String msgType, Frame owner, Component parentComponent) {
		final JDialog dialog = new JDialog(owner, msgType, true);
		dialog.setIconImage(Toolkit.getDefaultToolkit().getImage(CommonDialog.class.getResource("/images/icons/laver.png")));
		// 设置对话框的宽高
		dialog.setSize(343, 289);

        // 设置对话框大小不可改变
		dialog.setResizable(false);

        // 设置对话框相对显示的位置
		dialog.setLocationRelativeTo(parentComponent);
		// 设置父窗体不允许聚焦
		dialog.setModal(true);

        // 创建一个按钮用于关闭对话框
		JButton okBtn = new JButton("确认");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // 关闭对话框
				dialog.dispose();
			}
		});

        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
		JPanel panel = new JPanel();
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(ImageUtility.getImageIcon("/images/dialog/prompt.png", IMAGE_WIDTH, IMAGE_HEIGHT));
		switch (msgType) {
		case PROMPT:
			imageLabel.setIcon(ImageUtility.getImageIcon("/images/dialog/prompt.png", IMAGE_WIDTH, IMAGE_HEIGHT));
			break;
		case WARNING:
			imageLabel.setIcon(ImageUtility.getImageIcon("/images/dialog/warning.png", IMAGE_WIDTH, IMAGE_HEIGHT));
			break;
		case ERROR:
			imageLabel.setIcon(ImageUtility.getImageIcon("/images/dialog/error.png", IMAGE_WIDTH, IMAGE_HEIGHT));
			break;
		default:
			imageLabel.setIcon(ImageUtility.getImageIcon("/images/dialog/prompt.png", IMAGE_WIDTH, IMAGE_HEIGHT));
			break;
		}
        // 设置对话框的内容面板
		dialog.setContentPane(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(43, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(270, Short.MAX_VALUE)
					.addComponent(okBtn)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(imageLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(okBtn)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JTextArea msgTextArea = new JTextArea();
		scrollPane.setViewportView(msgTextArea);
		msgTextArea.setLineWrap(true);
		msgTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		msgTextArea.setBackground(Color.WHITE);
		msgTextArea.setEnabled(false);
		msgTextArea.setEditable(false);
		msgTextArea.setText(msg);
		panel.setLayout(gl_panel);

        // 显示对话框
		dialog.setVisible(true);
	}
}
