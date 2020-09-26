package cn.heicaijun.Laver.view;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cn.heicaijun.Laver.util.ImageUtility;

import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class AboutUSDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;

	/**
	 * Launch the application.
	 */
	public static void alertDialog(JFrame owner, Component parentComponent) {
		try {
			AboutUSDialog dialog = new AboutUSDialog(owner, parentComponent);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AboutUSDialog(JFrame owner, Component parentComponent) {
		super(owner, "关于我们", true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutUSDialog.class.getResource("/images/icons/laver.png")));
		setBounds(100, 100, 450, 443);
		setLocationRelativeTo(parentComponent);
		setResizable(false);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("确定");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 444, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon(AboutUSDialog.class.getResource("/images/icons/LaverTitle.png")));
		JLabel lblLaver = new JLabel("Laver 1.0");
		lblLaver.setFont(new Font("微软雅黑", Font.BOLD, 14));
		
		JLabel lblCopyrightc = new JLabel("Copyright (c) 2020 月湖发电站 (MoonLakeps),Inc.All rights reserved");
		lblCopyrightc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel label = new JLabel("支持与疑难解答");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblPoweredByHeicaijun = new JLabel("Powered By Heicaijun");
		lblPoweredByHeicaijun.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 12));
		lblPoweredByHeicaijun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Desktop desktop = Desktop.getDesktop();  
				try {
					desktop.browse(new URI("https://github.com/heicaijun"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
		lblPoweredByHeicaijun.setIcon(ImageUtility.getImageIcon("/images/icons/heicaijun.png", 30, 30));
		
		JButton btnNewButton = new JButton("https://github.com/heicaijun/Laver");
		btnNewButton.setForeground(SystemColor.textHighlight);
		// 首先设置不绘制按钮边框
		btnNewButton.setBorderPainted(false);
		//不绘制默认按钮背景
		btnNewButton.setContentAreaFilled(false);
		//不绘制图片或文字周围的焦点虚框
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.getDesktop();  
				try {
					desktop.browse(new URI("https://github.com/heicaijun/Laver"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlShadow);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLaver)
					.addContainerGap(366, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCopyrightc)
					.addContainerGap(42, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addContainerGap(346, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
							.addComponent(lblPoweredByHeicaijun)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(scrollPane_1, Alignment.LEADING)
						.addComponent(scrollPane, Alignment.LEADING))
					.addGap(20))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLaver)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCopyrightc)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPoweredByHeicaijun)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 3, Short.MAX_VALUE))
		);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.menu);
		textArea.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setEnabled(false);
		textArea.setText("该系统属月湖发电站团队产品，用户享有使用权，月湖发电站团队保留产品著作权。");
		scrollPane.setViewportView(textArea);
		
		JTextArea txtrgithubissue = new JTextArea();
		txtrgithubissue.setBackground(SystemColor.menu);
		txtrgithubissue.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		txtrgithubissue.setForeground(SystemColor.menu);
		txtrgithubissue.setEditable(false);
		txtrgithubissue.setEnabled(false);
		txtrgithubissue.setLineWrap(true);
		txtrgithubissue.setText("本产品系开源项目，如需提供支持可通过github提出Issue，我们将为您解答");
		scrollPane_1.setViewportView(txtrgithubissue);
		contentPanel.setLayout(gl_contentPanel);
		getContentPane().setLayout(groupLayout);
	}
}
