package cn.heicaijun.Laver.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.heicaijun.Laver.bean.ConfigBean;
import cn.heicaijun.Laver.bean.FileBean;
import cn.heicaijun.Laver.util.CongfigSerializeUtility;
import cn.heicaijun.Laver.util.FileUtility;
import cn.heicaijun.Laver.util.ImageUtility;
import cn.heicaijun.Laver.util.StringUtility;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.InputEvent;
import java.awt.Font;

import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class  MainFrm{

	private JDesktopPane desktopPane = null;
	private JFrame frmLaver;
	private JTextField rootPathTextField;
	private JTextField otherFormatTextField;
	private JTextField depthTextField;
	private JTextField userDefineTextField;
	
	private JCheckBox chckbxAll;
	private JCheckBox chckbxXml;
	private JCheckBox chckbxWord;
	private JCheckBox chckbxPpt;
	private JCheckBox chckbxExcel;
	private JCheckBox chckbxPdf;
	private JCheckBox chckbxOther;
	
	private JRadioButton checkAllRadioBtn;
	private JRadioButton onlyLastestRadioBtn;
	private JRadioButton userDefineRadioBtn;
	
	private JSlider depthSlider;
	
	private JTable searchResultTable;
	
	private JButton exportToExcelBtn;
	
	private JToggleButton hiddenYNToggleBtn;
	
	private final ButtonGroup modeBtnGroup = new ButtonGroup();
	
	JPanel advancedPanel;
	
	// 下述2个常量为所有图标的大小
	private final static int ICONS_WIDTH_DEFAULT = 18;
	private final static int ICONS_HEIGHT_DEFAULT = 18;
	// 下述2个常量为其他自定义规则中提示的内容
	private final static String OTHER_FORMAT_HINT = "可输入用逗号隔开的多个文件后缀,范例：【png,jepg,gif】";
	private final static String OTHER_FORMAT_HINT_ALL_SELECTED = "包含其他所有类型文件";
	// 下述2个常量为用户自定义格式框中提示的内容
	private final static String OTHER_MODE_HINT = "可输入用逗号隔开的多个版本标识符号,范例：【ver,v】";
	// 下述2个常量为自定义的字体
	private final static Font DEFAULT_BODY_FONT = new Font("微软雅黑", Font.PLAIN, 14);
	private final static Font DEFAULT_TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 16);
	// 下述常量为默认配置存储位置
	private final static File DEFALT_CONFIG_FILE = new File("." + File.separator + 
															"config" + File.separator + 
															"DefaultConfig.conf");
	private ConfigBean configBean;
	private ArrayList<FileBean> resultFileBeans;
	private JPanel panel;
	
	/**
	 * Launch the application.
	 */
	public static void run(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrm window = new MainFrm();
					window.frmLaver.setVisible(true);
				} catch (Exception e) {
					DEFALT_CONFIG_FILE.delete();
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLaver = new JFrame();
		frmLaver.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrm.class.getResource("/images/icons/laver.png")));
		frmLaver.setTitle("Laver文件版本遍历器");
		frmLaver.setBounds(100, 100, 748, 603);
		frmLaver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu settingMenu = new JMenu("操作");
		settingMenu.setFont(DEFAULT_TITLE_FONT);
		settingMenu.setIcon(ImageUtility.getImageIcon("/images/btns/setting.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		menuBar.add(settingMenu);
		
		JMenuItem importConfItem = new JMenuItem("导入配置");
		importConfItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importConfig(e);
			}
		});
		importConfItem.setFont(DEFAULT_BODY_FONT);
		importConfItem.setIcon(ImageUtility.getImageIcon("/images/btns/import.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		settingMenu.add(importConfItem);
		
		JMenuItem exportConfItem = new JMenuItem("导出配置");
		exportConfItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportConfig(e);
			}
		});
		exportConfItem.setFont(DEFAULT_BODY_FONT);
		exportConfItem.setIcon(ImageUtility.getImageIcon("/images/btns/export.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		settingMenu.add(exportConfItem);
		
		JMenu moreMenu = new JMenu("帮助");
		moreMenu.setIcon(ImageUtility.getImageIcon("/images/btns/about.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		moreMenu.setFont(DEFAULT_TITLE_FONT);
		moreMenu.setHorizontalAlignment(SwingConstants.TRAILING);
		menuBar.add(moreMenu);
		
		JMenuItem helpItem = new JMenuItem("帮助");
		helpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alertHelpWeb(e);
			}
		});
		helpItem.setFont(DEFAULT_BODY_FONT);
		helpItem.setIcon(ImageUtility.getImageIcon("/images/btns/help.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.ALT_MASK));
		moreMenu.add(helpItem);
		
		JMenuItem aboutUSItem = new JMenuItem("关于我们");
		aboutUSItem.setIcon(ImageUtility.getImageIcon("/images/btns/aboutUS.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		aboutUSItem.setFont(DEFAULT_BODY_FONT);
		aboutUSItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aboutUSAction(arg0);
			}
		});
		moreMenu.add(aboutUSItem);
		
		JLabel label = new JLabel("需要扫描的目录：");
		label.setFont(DEFAULT_BODY_FONT);
		
		rootPathTextField = new JTextField();
		rootPathTextField.setColumns(10);
		
		JButton fileBrowserBtn = new JButton("浏览");
		fileBrowserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dirBrowserActionPerformed(e);
			}
		});
		
		JLabel lblNewLabel = new JLabel("需要检索的格式：");
		lblNewLabel.setFont(DEFAULT_BODY_FONT);
		
		chckbxAll = new JCheckBox("ALL");
//		chckbxAll.setIcon(ImageUtility.getImageIcon("/images/btns/selected_false.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
//		chckbxAll.setSelectedIcon(ImageUtility.getImageIcon("/images/btns/selected_true.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		chckbxAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox checkBox = (JCheckBox) e.getSource();
				chckbxXml.setSelected(checkBox.isSelected());
				chckbxWord.setSelected(checkBox.isSelected());
				chckbxPpt.setSelected(checkBox.isSelected());
				chckbxExcel.setSelected(checkBox.isSelected());
				chckbxPdf.setSelected(checkBox.isSelected());
				chckbxOther.setSelected(checkBox.isSelected());
				//当全选框被选中时，不希望用户能改动OtherFormat文本框中的内容
				otherFormatTextField.setEnabled(!checkBox.isSelected());
				// 当全部框变动时，将other中提示变动为合适的提示语句
				changeOtherFormatTextField();
			}
		});
		chckbxAll.setToolTipText("将会检索文件夹内所有文件，包括但不限于后续列出的所有类型");
		chckbxAll.setBackground(Color.WHITE);
		chckbxAll.setSelected(true);
		chckbxAll.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxXml = new JCheckBox("xml");
		chckbxXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxXml.setBackground(Color.WHITE);
		chckbxXml.setSelected(true);
		chckbxXml.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxWord = new JCheckBox("word");
		chckbxWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxWord.setBackground(Color.WHITE);
		chckbxWord.setSelected(true);
		chckbxWord.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxPpt = new JCheckBox("PPT");
		chckbxPpt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxPpt.setBackground(Color.WHITE);
		chckbxPpt.setSelected(true);
		chckbxPpt.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxExcel = new JCheckBox("excel");
		chckbxExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxExcel.setBackground(Color.WHITE);
		chckbxExcel.setSelected(true);
		chckbxExcel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxPdf = new JCheckBox("pdf");
		chckbxPdf.setToolTipText("所有以pdf结尾的文件将被检出");
		chckbxPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxPdf.setBackground(Color.WHITE);
		chckbxPdf.setSelected(true);
		chckbxPdf.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		chckbxOther = new JCheckBox("other:");
		chckbxOther.setSelected(true);
		chckbxOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otherFormatTextField.setEnabled(chckbxOther.isSelected());
				checkAllChckbxActionPerformed(e);
			}
		});
		chckbxOther.setBackground(Color.WHITE);
		chckbxOther.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
				
		otherFormatTextField = new JTextField();
		otherFormatTextField.setEnabled(false);
		otherFormatTextField.setText(OTHER_FORMAT_HINT);
		otherFormatTextField.setForeground(Color.GRAY);
		otherFormatTextField.setToolTipText(OTHER_FORMAT_HINT);
		otherFormatTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (OTHER_FORMAT_HINT.equals(otherFormatTextField.getText())) {
					otherFormatTextField.setText("");
					otherFormatTextField.setForeground(Color.BLACK);
				}
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (StringUtility.isEmpty(otherFormatTextField.getText())) {
					otherFormatTextField.setText(OTHER_FORMAT_HINT);
					otherFormatTextField.setForeground(Color.GRAY);
				}
			}
		});
		otherFormatTextField.setColumns(10);
		
		JLabel label_1 = new JLabel("需要扫描的深度：");
		label_1.setFont(DEFAULT_BODY_FONT);
		
		depthTextField = new JTextField();
		depthTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char ch= arg0.getKeyChar();
	            if(ch<'0'||ch>'9')
	            	arg0.consume();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				depthTextFieldCommit(e);
			}
		});
		depthTextField.setText("0");
		depthTextField.setColumns(10);
		
		depthSlider = new JSlider();
		depthSlider.setBackground(Color.WHITE);
		depthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				depthSliderChanged(arg0);
			}
		});
		depthSlider.setValue(0);
		depthSlider.setMaximum(20);
		
		JButton startSearchBtn = new JButton("开始扫描");
		startSearchBtn.setFont(DEFAULT_BODY_FONT);
		startSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSearchBtnActPerformed(e);
			}
		});
		startSearchBtn.setIcon(ImageUtility.getImageIcon("/images/btns/search.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		
		JScrollPane searchResultScrollPane = new JScrollPane();
		searchResultScrollPane.setEnabled(false);
		
		exportToExcelBtn = new JButton("导出结果至Excel");
		exportToExcelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveToCSV(e);
			}
		});
		exportToExcelBtn.setEnabled(false);
		exportToExcelBtn.setFont(DEFAULT_BODY_FONT);
		exportToExcelBtn.setIcon(ImageUtility.getImageIcon("/images/btns/exportToExcel.png", ICONS_WIDTH_DEFAULT, ICONS_HEIGHT_DEFAULT));
		
		advancedPanel = new JPanel();
		advancedPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		advancedPanel.setBackground(Color.WHITE);
		advancedPanel.setForeground(Color.WHITE);
		advancedPanel.setVisible(false);
		
		JToggleButton toggleButton = new JToggleButton("高级选项");
		// 首先设置不绘制按钮边框
		toggleButton.setBorderPainted(false);
		toggleButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		toggleButton.setSelectedIcon(ImageUtility.getImageIcon("/images/btns/optionOn.png", 16, 16));
		toggleButton.setIcon(ImageUtility.getImageIcon("/images/btns/optionOff.png", 16, 16));
		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				advancedPanel.setVisible(toggleButton.isSelected());
			}
		});
		
		panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(SystemColor.controlHighlight);
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addGap(10)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rootPathTextField, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileBrowserBtn, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxAll)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxXml)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxWord)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxPpt)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxExcel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxPdf)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxOther)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(otherFormatTextField, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(depthTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(depthSlider, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)))
					.addContainerGap())
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchResultScrollPane, GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(startSearchBtn, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 238, Short.MAX_VALUE)
					.addComponent(exportToExcelBtn, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(toggleButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(advancedPanel, GroupLayout.PREFERRED_SIZE, 637, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_desktopPane.setVerticalGroup(
			gl_desktopPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_desktopPane.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(rootPathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(fileBrowserBtn))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(chckbxAll)
						.addComponent(chckbxXml)
						.addComponent(chckbxWord)
						.addComponent(chckbxPpt)
						.addComponent(chckbxExcel)
						.addComponent(chckbxPdf)
						.addComponent(chckbxOther)
						.addComponent(otherFormatTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_desktopPane.createParallelGroup(Alignment.TRAILING)
							.addComponent(depthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(depthSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_1))
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(toggleButton))
						.addGroup(gl_desktopPane.createSequentialGroup()
							.addGap(15)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(advancedPanel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_desktopPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(startSearchBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(exportToExcelBtn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchResultScrollPane, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel label_2 = new JLabel("扫  描  模  式：");
		label_2.setFont(DEFAULT_BODY_FONT);
		
		checkAllRadioBtn = new JRadioButton("全检出");
		checkAllRadioBtn.setToolTipText("无论版本号是多少，全部检出");
		checkAllRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onlyLastestRadioBtn.isSelected()) {
					userDefineTextField.setEnabled(userDefineRadioBtn.isSelected());
					if ("".contentEquals(userDefineTextField.getText()) ) {
						userDefineTextField.setText(OTHER_MODE_HINT);
						userDefineTextField.setForeground(Color.GRAY);
					}
				}
			}
		});
		modeBtnGroup.add(checkAllRadioBtn);
		checkAllRadioBtn.setBackground(Color.WHITE);
		checkAllRadioBtn.setFont(DEFAULT_BODY_FONT);
		checkAllRadioBtn.setSelected(true);
		
		onlyLastestRadioBtn = new JRadioButton("只显示最新版");
		onlyLastestRadioBtn.setToolTipText("使用系统默认的标识符[version,ver,v]来拆分版本号并对比");
		onlyLastestRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onlyLastestRadioBtn.isSelected()) {
					userDefineTextField.setEnabled(userDefineRadioBtn.isSelected());
					if ("".contentEquals(userDefineTextField.getText()) ) {
						userDefineTextField.setText(OTHER_MODE_HINT);
						userDefineTextField.setForeground(Color.GRAY);
					}
				}
			}
		});
		onlyLastestRadioBtn.setBackground(Color.WHITE);
		onlyLastestRadioBtn.setFont(DEFAULT_BODY_FONT);
		modeBtnGroup.add(onlyLastestRadioBtn);
		
		userDefineRadioBtn = new JRadioButton("自定义规则：");
		userDefineRadioBtn.setToolTipText(OTHER_MODE_HINT);
		userDefineRadioBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userDefineTextField.setEnabled(userDefineRadioBtn.isSelected());
			}
		});
		userDefineRadioBtn.setBackground(Color.WHITE);
		userDefineRadioBtn.setFont(DEFAULT_BODY_FONT);
		modeBtnGroup.add(userDefineRadioBtn);
		
		userDefineTextField = new JTextField();
		userDefineTextField.setEnabled(false);
		userDefineTextField.setEnabled(false);
		userDefineTextField.setText(OTHER_MODE_HINT);
		userDefineTextField.setForeground(Color.GRAY);
		userDefineTextField.setToolTipText(OTHER_MODE_HINT);
		userDefineTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (OTHER_MODE_HINT.equals(userDefineTextField.getText())) {
					userDefineTextField.setText("");
					userDefineTextField.setForeground(Color.BLACK);
				}
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (StringUtility.isEmpty(userDefineTextField.getText())) {
					userDefineTextField.setText(OTHER_MODE_HINT);
					userDefineTextField.setForeground(Color.GRAY);
				}
			}
		});
		userDefineTextField.setColumns(10);
		
		JLabel label_3 = new JLabel("是否扫描隐藏文件：");
		label_3.setFont(DEFAULT_BODY_FONT);
		
		hiddenYNToggleBtn = new JToggleButton();
		// 首先设置不绘制按钮边框
		hiddenYNToggleBtn.setBorderPainted(false);
		//不绘制默认按钮背景
		hiddenYNToggleBtn.setContentAreaFilled(false);
		//不绘制图片或文字周围的焦点虚框
		hiddenYNToggleBtn.setFocusPainted(false); 
		hiddenYNToggleBtn.setIcon(ImageUtility.getImageIcon("/images/btns/toggleOff.png", 26, 16));
		hiddenYNToggleBtn.setSelectedIcon(ImageUtility.getImageIcon("/images/btns/toggleOn.png", 26, 16));
		GroupLayout gl_advancedPanel = new GroupLayout(advancedPanel);
		gl_advancedPanel.setHorizontalGroup(
			gl_advancedPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_advancedPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_advancedPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_advancedPanel.createSequentialGroup()
							.addComponent(label_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(hiddenYNToggleBtn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_advancedPanel.createSequentialGroup()
							.addComponent(label_2)
							.addGap(6)
							.addComponent(checkAllRadioBtn)))
					.addGap(2)
					.addComponent(onlyLastestRadioBtn)
					.addComponent(userDefineRadioBtn)
					.addGap(6)
					.addComponent(userDefineTextField, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_advancedPanel.setVerticalGroup(
			gl_advancedPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_advancedPanel.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_advancedPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_advancedPanel.createSequentialGroup()
							.addGap(4)
							.addComponent(label_2))
						.addComponent(checkAllRadioBtn)
						.addComponent(onlyLastestRadioBtn)
						.addComponent(userDefineRadioBtn)
						.addGroup(gl_advancedPanel.createSequentialGroup()
							.addGap(4)
							.addComponent(userDefineTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(5)
					.addGroup(gl_advancedPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(hiddenYNToggleBtn, 0, 0, Short.MAX_VALUE)
						.addComponent(label_3))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		advancedPanel.setLayout(gl_advancedPanel);
		
		searchResultTable = new JTable();
		searchResultTable.setFont(DEFAULT_BODY_FONT);
		DefaultTableModel tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u7236\u76EE\u5F55", "\u6587\u4EF6\u540D", "\u5B8C\u6574\u8DEF\u5F84"
				}
			);
		searchResultTable.setModel(tableModel);
		searchResultTable.getColumnModel().getColumn(0).setPreferredWidth(90);
		searchResultTable.getColumnModel().getColumn(1).setPreferredWidth(249);
		searchResultTable.getColumnModel().getColumn(2).setPreferredWidth(283);
		
        // 使用 表格模型 创建 行排序器（TableRowSorter 实现了 RowSorter）
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(tableModel);
        // 给 表格 设置 行排序器
		searchResultTable.setRowSorter(rowSorter);
		
//        // 创建单元格渲染器
//		GrayTableCellRenderer grayTableCellRenderer = new GrayTableCellRenderer();
//		// 遍历表格的每一列，分别给每一列设置单元格渲染器
//        for (int i = 0; i < searchResultTable.getColumnModel().getColumnCount(); i++) {
//            // 根据 列名 获取 表格列
//            TableColumn tableColumn = searchResultTable.getColumn(i);
//            // 设置 表格列 的 单元格渲染器
//            tableColumn.setCellRenderer(grayTableCellRenderer);
//        }
//		
		searchResultScrollPane.setViewportView(searchResultTable);
		desktopPane.setLayout(gl_desktopPane);
		GroupLayout groupLayout = new GroupLayout(frmLaver.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(desktopPane)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(desktopPane, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
		);
		frmLaver.getContentPane().setLayout(groupLayout);
		
		if (DEFALT_CONFIG_FILE.exists()) {
			Object object = CongfigSerializeUtility.loadObject(DEFALT_CONFIG_FILE);
			if (object instanceof ConfigBean) {
				configBean = (ConfigBean) object;
			}
			if (configBean != null) {
				refreshWindow(configBean);
			}
		}
	}
	protected void importConfig(ActionEvent e) {
		// 创建一个默认的文件选取器
		JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
		fileChooser.setSelectedFile(DEFALT_CONFIG_FILE.getAbsoluteFile());

        // 设置默认使用的文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("Laver Config文件(*.conf)", "conf"));
		//不允许所有类型的文件过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
		int result = fileChooser.showOpenDialog(desktopPane);

		if(result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的路径
			File confFile = fileChooser.getSelectedFile();
			if (confFile.exists()) {
				Object object = CongfigSerializeUtility.loadObject(confFile);
				if (object != null && object instanceof ConfigBean) {
					configBean = (ConfigBean) object;
					refreshWindow(configBean);
				}
			}
		}
	}

	/**
	 * 点击导出配置按钮后，弹出默认的保存配置信息
	 * @param e
	 */
	protected void exportConfig(ActionEvent e) {
		if (configBean == null) {
			CommonDialog.showMsg("当前配置不可用。请先进行简单配置，并扫描后进行导出操作",
					CommonDialog.WARNING, frmLaver, frmLaver);
			return;
		}
		// 创建一个默认的文件选取器
		JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
		fileChooser.setSelectedFile(DEFALT_CONFIG_FILE.getAbsoluteFile());

        // 设置默认使用的文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("Laver Config文件(*.conf)", "conf"));
		//不允许所有类型的文件过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
		int result = fileChooser.showSaveDialog(desktopPane);

		if(result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
			File confFile = fileChooser.getSelectedFile();
			// 将此次扫描的配置档保存起来，以便用户下次开启软件时可以重新导入最近一次正确配置
			if (confFile.getParentFile().isDirectory()) {
				// 检查用户输入的格式是否符合要求
				if (confFile.getName().endsWith(".conf")) {
					if (!CongfigSerializeUtility.store(configBean, confFile)) {
						// 说明存储不成功，需要删掉错误的File
						if (confFile.exists()) {
							confFile.delete();
						}
					}
				}else {
					// 如果不符合就在文件结尾加上格式
					confFile = new File(confFile.getParentFile(), confFile.getName() + ".conf");
					if (!CongfigSerializeUtility.store(configBean, confFile)) {
						// 说明存储不成功，需要删掉错误的File
						if (confFile.exists()) {
							confFile.delete();
						}
					}
				}
			}else {
				confFile.getParentFile().mkdirs();
				CongfigSerializeUtility.store(configBean, confFile);
			}
		}
		
	}

	private void refreshWindow(ConfigBean configBean) {
		// 设置扫描目录文本框
		rootPathTextField.setText(configBean.getRootPath().getPath());
		// 设置扫描格式
		Set<Integer> searchFomats = configBean.getSearchFormat();
		if (searchFomats.contains(ConfigBean.ALL_SELECTOR)) {
			chckbxAll.setSelected(true);
		}else {
			chckbxAll.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.XML_SELECTOR)) {
			chckbxXml.setSelected(true);
		}else {
			chckbxXml.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.WORD_SELECTOR)) {
			chckbxWord.setSelected(true);
		}else {
			chckbxWord.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.PPT_SELECTOR)) {
			chckbxPpt.setSelected(true);
		}else {
			chckbxPpt.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.EXCEL_SELECTOR)) {
			chckbxExcel.setSelected(true);
		}else {
			chckbxExcel.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.PDF_SELECTOR)) {
			chckbxPdf.setSelected(true);
		}else {
			chckbxPdf.setSelected(false);
		}
		if (searchFomats.contains(ConfigBean.OTHER_SELECTOR)) {
			if (StringUtility.isNotEmpty(configBean.getOtherFormatStr())) {
				chckbxOther.setSelected(true);
				otherFormatTextField.setEnabled(true);
				otherFormatTextField.setText(configBean.getOtherFormatStr());
				otherFormatTextField.setForeground(Color.BLACK);
			}
		}else {
			chckbxOther.setSelected(false);
		}
		// 设置扫描深度
		int depth = configBean.getDepth();
		if (depth > 0 && depth <= 20) {
			depthTextField.setText(depth + "");
			depthSlider.setValue(depth);
		}
		// 设置扫描模式
		switch (configBean.getMode()) {
		case ConfigBean.CHECK_ALL_MODE:
			checkAllRadioBtn.setSelected(true);
			break;
		case ConfigBean.ONLY_LASTEST_MODE:
			onlyLastestRadioBtn.setSelected(true);
			break;
		case ConfigBean.USER_DEFINE_MODE:
			if (StringUtility.isNotEmpty(configBean.getUserDefineMode())) {
				userDefineRadioBtn.setSelected(true);
				userDefineTextField.setEnabled(true);
				userDefineTextField.setText(configBean.getUserDefineMode());
				userDefineTextField.setForeground(Color.BLACK);;
			}
			break;
		default:
			break;
		}
		// 设置是否扫描隐藏文件
		hiddenYNToggleBtn.setSelected(configBean.getHiddenYN());
	}

	protected void alertHelpWeb(ActionEvent e) {
		Desktop desktop = Desktop.getDesktop();  
		try {
			desktop.browse(new URI("https://www.cnblogs.com/heicaijun/p/13723837.html"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}  
	}

	protected void changeOtherFormatTextField() {
		if (chckbxAll.isSelected()) {
			otherFormatTextField.setToolTipText(OTHER_FORMAT_HINT_ALL_SELECTED);
			if ("".contentEquals(otherFormatTextField.getText()) ||
					OTHER_FORMAT_HINT.equals(otherFormatTextField.getText()) ||
					OTHER_FORMAT_HINT_ALL_SELECTED.equals(otherFormatTextField.getText())) {
				otherFormatTextField.setText(OTHER_FORMAT_HINT_ALL_SELECTED);
				otherFormatTextField.setForeground(Color.GRAY);
			}
		}else {
			otherFormatTextField.setToolTipText(OTHER_FORMAT_HINT);
			if ("".contentEquals(otherFormatTextField.getText()) ||
					OTHER_FORMAT_HINT.equals(otherFormatTextField.getText()) ||
					OTHER_FORMAT_HINT_ALL_SELECTED.equals(otherFormatTextField.getText())) {
				otherFormatTextField.setText(OTHER_FORMAT_HINT);
				otherFormatTextField.setForeground(Color.GRAY);
			}
			otherFormatTextField.setEnabled(chckbxOther.isSelected());
		}
	}

	// 当检索格式中的选项框取消且All选项框是选中状态时取消All选项框的选中状态
	protected void checkAllChckbxActionPerformed(ActionEvent e) {
		JCheckBox checkbox = (JCheckBox)e.getSource();
		if (chckbxAll.isSelected() && !checkbox.isSelected()) {
			chckbxAll.setSelected(false);
		}
		changeOtherFormatTextField();
	}
	/**
	 * 点击开始扫描后触发，将对输入的内容进行检查，如无问题则将扫描结果写入到下方的表格中
	 * @param e
	 */
	protected void startSearchBtnActPerformed(ActionEvent e) {
		//先检查所有输入格式是否正确
		if (!FileUtility.checkPath(rootPathTextField.getText())) {
			CommonDialog.showMsg("填写路径不正确，请检查", CommonDialog.ERROR, frmLaver, desktopPane);
		}else if (!chckbxAll.isSelected() &&
				!chckbxXml.isSelected() &&
				!chckbxWord.isSelected() &&
				!chckbxPpt.isSelected() &&
				!chckbxExcel.isSelected() &&
				!chckbxPdf.isSelected() &&
				!chckbxOther.isSelected()) {
			CommonDialog.showMsg("请选择至少一项检索格式", CommonDialog.ERROR, frmLaver, desktopPane);
		}else {
			// 在格式正确的前提下，创建一个ConfigBean用于存储所有的配置信息
			// 扫描的根目录
			String rootPath = rootPathTextField.getText();
			// 获取扫描的格式
			Set<Integer> searchResultTable = new HashSet<Integer>();
			if (chckbxAll.isSelected()) {
				searchResultTable.add(ConfigBean.ALL_SELECTOR);
			}
			if (chckbxXml.isSelected()) {
				searchResultTable.add(ConfigBean.XML_SELECTOR);
			}
			if (chckbxWord.isSelected()) {
				searchResultTable.add(ConfigBean.WORD_SELECTOR);
			}
			if (chckbxPpt.isSelected()) {
				searchResultTable.add(ConfigBean.PPT_SELECTOR);
			}
			if (chckbxExcel.isSelected()) {
				searchResultTable.add(ConfigBean.EXCEL_SELECTOR);
			}
			if (chckbxPdf.isSelected()) {
				searchResultTable.add(ConfigBean.PDF_SELECTOR);
			}
			// 获取扫描深度
			int depth = depthSlider.getValue();
			// 获取扫描模式，默认全扫描
			int mode = ConfigBean.CHECK_ALL_MODE;
			if (checkAllRadioBtn.isSelected()) {
				mode = ConfigBean.CHECK_ALL_MODE;
			}
			if (onlyLastestRadioBtn.isSelected()) {
				mode = ConfigBean.ONLY_LASTEST_MODE;
			}
			if (userDefineRadioBtn.isSelected()) {
				mode = ConfigBean.USER_DEFINE_MODE;
			}
			// 是否扫描隐藏文件
			boolean hiddenYN = hiddenYNToggleBtn.isSelected();
			configBean = new ConfigBean(rootPath, searchResultTable, depth, mode, hiddenYN);
			if (chckbxOther.isSelected()) {
				searchResultTable.add(ConfigBean.OTHER_SELECTOR);
				// 其他格式框内有内容的前提下，给配置其他格式赋值
				if (StringUtility.isNotEmpty(otherFormatTextField.getText()) &&
					!OTHER_FORMAT_HINT.equals(otherFormatTextField.getText()) &&
					!OTHER_FORMAT_HINT_ALL_SELECTED.equals(otherFormatTextField.getText()) ) {
					configBean.setOtherFormatStr(otherFormatTextField.getText());
				}
			}
			// 如果用户自定义规则被勾选的话
			if (userDefineRadioBtn.isSelected()) {
				if (StringUtility.isNotEmpty(userDefineTextField.getText()) &&
						!OTHER_FORMAT_HINT.equals(userDefineTextField.getText()) ) {
						configBean.setUserDefineMode(userDefineTextField.getText());
				}
			}
			resultFileBeans = FileUtility.traversalFolder(configBean);
			if(resultFileBeans != null && !resultFileBeans.isEmpty()) {
				// 说明扫描成功了，且值不为空，此时将导出按钮置为可点击
				exportToExcelBtn.setEnabled(true);
				// 将此次扫描的配置档保存起来，以便用户下次开启软件时可以重新读取最近一次正确配置
				if (DEFALT_CONFIG_FILE.getParentFile().isDirectory()) {
					if (!CongfigSerializeUtility.store(configBean, DEFALT_CONFIG_FILE)) {
						// 说明存储不成功，需要删掉错误的File
						if (DEFALT_CONFIG_FILE.exists()) {
							DEFALT_CONFIG_FILE.delete();
						}
					}
				}else {
					DEFALT_CONFIG_FILE.getParentFile().mkdirs();
					CongfigSerializeUtility.store(configBean, DEFALT_CONFIG_FILE);
				}
			}else {
				//说明扫描结果为空，此时将导出按钮置为不可点击
				exportToExcelBtn.setEnabled(false);
			}
			
			showFileBeansInTable(resultFileBeans);
		}
	}
	private void showFileBeansInTable(ArrayList<FileBean> fileBeans) {
		DefaultTableModel dtm = (DefaultTableModel) searchResultTable.getModel();
		dtm.setRowCount(0);
		if(!fileBeans.isEmpty()) {
			String[] bufferStr = new String[3];
			for (FileBean fileBean : fileBeans) {
				bufferStr[0] = fileBean.getFatherPath();
				bufferStr[1] = fileBean.getFileName();
				bufferStr[2] = fileBean.getAbsolutePath();
				dtm.addRow(bufferStr);
			}
		}
	}

	/**
	 * 用于导出为Excel文件使用
	 * @param e
	 */
	protected void saveToCSV(ActionEvent e) {
		// 创建一个默认的文件选取器
		JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
		fileChooser.setSelectedFile(configBean.getOutputPath());

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
//		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Excel(*.csv)", "csv"));
        // 设置默认使用的文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel(*.csv)", "csv"));
		//不允许所有类型的文件过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
		int result = fileChooser.showSaveDialog(desktopPane);

		if(result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
			File chooseFile = fileChooser.getSelectedFile();
			if (chooseFile.getName().toLowerCase().endsWith(".csv")) {
				configBean.setOutputPath(chooseFile);
			}else {
				configBean.setOutputPath(new File(chooseFile.getParentFile(), chooseFile.getName() + ".csv"));
			}
			FileUtility.writeFileBeansToCSV(resultFileBeans, configBean.getOutputPath());
		}
	}
	/**
	 * 用于在选择需要扫描的目录时可以浏览系统文件夹
	 * @param e
	 */
	protected void dirBrowserActionPerformed(ActionEvent e) {
		// 创建一个默认的文件选取器
		JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为文本框中的文件夹
		if (rootPathTextField.getText() != null && !"".equals(rootPathTextField.getText()) )
			fileChooser.setCurrentDirectory(new File(rootPathTextField.getText()));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
		int result = fileChooser.showOpenDialog(desktopPane);

		if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
			File file = fileChooser.getSelectedFile();
			rootPathTextField.setText(file.getPath());
		}

	}
	/**
	 * 用于在深度输入框键入时，检测是否为数字，且小于20，且滑块同步变化
	 * @param e
	 */
	protected void depthTextFieldCommit(KeyEvent e) {
		if (StringUtility.isEmpty(depthTextField.getText())) {
			depthTextField.setText("0");
		}
		if (Integer.parseInt( depthTextField.getText() ) > 20) {
			depthTextField.setText("20");
		}
		depthSlider.setValue(Integer.parseInt( depthTextField.getText() ));
		
	}
	/**
	 * 用于在滑块发生改变时将深度的输入框同步变化
	 * @param event
	 */
	
	private void depthSliderChanged(ChangeEvent event) {
		JSlider slider = (JSlider) event.getSource();
		depthTextField.setText(slider.getValue() + "");	
	}
	/**
	 * 用于弹出关于我们的介绍
	 * @param evt
	 */
	private void aboutUSAction(ActionEvent evt) {
		AboutUSDialog.alertDialog(frmLaver, frmLaver);
	}
}
