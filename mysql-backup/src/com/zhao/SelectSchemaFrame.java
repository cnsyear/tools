package com.zhao;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class SelectSchemaFrame extends JFrame {
	private static final Logger log = LogManager
			.getLogger(SelectSchemaFrame.class);
	private MainFrame mainFrame;
	private MySqlDB db;
	private TreeSet<String> selectedSchemas = new TreeSet<String>();
	private List<JCheckBox> chks = new ArrayList<JCheckBox>();
	private int total;
	private JButton btn_cancle;
	private JButton btn_start;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel10;// 脚本、备份存储路径
	private JLabel jLabel11;// mysqldump路径
	private JTextField jText10;
	private JTextField jText11;
	private JLabel label_completed;
	private JLabel label_dbUrl;
	private JLabel label_total;
	private JPanel panel_schemas;
	private JProgressBar proccebar_execute;
	private JScrollPane scroll_schemas;
	
	public static String mysqldump;//mysqldump动态获取地址
	public static String userName;//数据库连接用户名
	public static String password;//数据库连接密码
	private StringBuffer content;//最终生成的文本

	public SelectSchemaFrame(MainFrame mainFrame, MySqlDB db, String dbUrl) {
		this.mainFrame = mainFrame;
		this.db = db;
		initComponents();
		setLocationRelativeTo(null);
		initSchemas();
		this.label_dbUrl.setText(dbUrl);

	}

	private void initComponents() {
		this.jLabel10 = new JLabel();
		this.jLabel11 = new JLabel();
		this.jText10 = new JTextField();
		this.jText11 = new JTextField();
		this.btn_start = new JButton();
		this.jLabel1 = new JLabel();
		this.label_dbUrl = new JLabel();
		this.btn_cancle = new JButton();
		this.scroll_schemas = new JScrollPane();
		this.panel_schemas = new JPanel();
		this.proccebar_execute = new JProgressBar();
		this.jLabel2 = new JLabel();
		this.jLabel3 = new JLabel();
		this.label_completed = new JLabel();
		this.jLabel4 = new JLabel();
		this.label_total = new JLabel();
		this.jLabel5 = new JLabel();
		this.jLabel6 = new JLabel();
		this.jLabel7 = new JLabel();

		setDefaultCloseOperation(3);
		setTitle("MySql备份脚本生成工具");

		this.btn_start.setText("开始生成");
		this.btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SelectSchemaFrame.this.btn_startActionPerformed(evt);
			}
		});
		this.jLabel1.setText("数据库：");
		this.jLabel10.setText("脚本、备份存储路径路径：");
		this.jText10.setText("D:\\mysql_backup");

		this.jLabel11.setText("mysqldump路径：");
		this.jText11.setText("C:\\Program Files\\MySQL\\MySQL Server 5.6\\bin\\mysqldump");
		
	
		this.btn_cancle.setText("取消");
		this.btn_cancle.setToolTipText("");
		this.btn_cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SelectSchemaFrame.this.btn_cancleActionPerformed(evt);
			}
		});
		this.scroll_schemas.setBorder(BorderFactory
				.createTitledBorder("选择数据库"));

		this.panel_schemas.setBackground(new Color(255, 255, 255));

		GroupLayout panel_schemasLayout = new GroupLayout(this.panel_schemas);
		this.panel_schemas.setLayout(panel_schemasLayout);
		panel_schemasLayout.setHorizontalGroup(panel_schemasLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						210, 32767));

		panel_schemasLayout.setVerticalGroup(panel_schemasLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0,
						310, 32767));

		this.scroll_schemas.setViewportView(this.panel_schemas);

		this.proccebar_execute.setStringPainted(true);

		this.jLabel2.setText("进　度：");

		this.jLabel3.setText("已处理");

		this.label_completed.setText("0");
		this.label_completed.setToolTipText("");

		this.jLabel4.setText("选　择");

		this.label_total.setText("0");
		this.label_total.setToolTipText("");

		this.jLabel5.setText("个数据库");

		this.jLabel6.setText("个数据库");

		this.jLabel7.setText("Made by: Jie.Zhao");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(this.scroll_schemas, -2, 222, -2)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										12, 32767)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)

												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createParallelGroup(
																GroupLayout.Alignment.LEADING)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						this.jLabel10)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.UNRELATED)
																				.addComponent(
																						this.jText10,
																						-2,
																						263,
																						-2)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createParallelGroup(
																GroupLayout.Alignment.LEADING)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						this.jLabel11)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.UNRELATED)
																				.addComponent(
																						this.jText11,
																						-2,
																						263,
																						-2)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createParallelGroup(
																GroupLayout.Alignment.LEADING)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						this.jLabel1)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.UNRELATED)
																				.addComponent(
																						this.label_dbUrl,
																						-2,
																						263,
																						-2))
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						this.jLabel2)
																				.addPreferredGap(
																						LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(
																						this.proccebar_execute,
																						-2,
																						247,
																						-2)))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.btn_cancle,
																						-2,
																						174,
																						-2)
																				.addComponent(
																						this.btn_start,
																						-2,
																						174,
																						-2)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										this.jLabel3)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										this.label_completed,
																										-2,
																										33,
																										-2)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										this.jLabel6))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										this.jLabel4)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										this.label_total,
																										-2,
																										33,
																										-2)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										this.jLabel5)))
																.addGap(75, 75,
																		75))
												.addGroup(
														GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		this.jLabel7)
																.addContainerGap()))));

		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(45, 45,
																		45)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING)
																				.addComponent(
																						this.label_dbUrl,
																						-2,
																						18,
																						-2)
																				.addComponent(
																						this.jLabel1))
																.addGap(18, 18,
																		18)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.jLabel10,
																						-2,
																						-1,
																						-2)
																				.addComponent(
																						this.jText10))
																.addGap(11, 11,
																		11)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.jLabel11,
																						-2,
																						-1,
																						-2)
																				.addComponent(
																						this.jText11))
																.addGap(11, 11,
																		11)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						this.proccebar_execute,
																						-2,
																						-1,
																						-2)
																				.addComponent(
																						this.jLabel2))
																.addGap(11, 11,
																		11)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						this.jLabel3)
																				.addComponent(
																						this.label_completed)
																				.addComponent(
																						this.jLabel6))
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						this.jLabel4)
																				.addComponent(
																						this.label_total,
																						-2,
																						15,
																						-2)
																				.addComponent(
																						this.jLabel5))
																.addGap(15, 15,
																		15)
																.addComponent(
																		this.btn_start,
																		-2, 42,
																		-2)
																.addGap(29, 29,
																		29)
																.addComponent(
																		this.btn_cancle,
																		-2, 41,
																		-2)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED,
																		-1,
																		32767)
																.addComponent(
																		this.jLabel7))
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		this.scroll_schemas)))
								.addContainerGap()));

		pack();
	}

	private void btn_cancleActionPerformed(ActionEvent evt) {
		setVisible(false);
		this.mainFrame.setVisible(true);
		dispose();
	}

	private void btn_startActionPerformed(ActionEvent evt) {
		if (this.selectedSchemas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "请选择需要备份的数据库！");
			return;
		}
		String str1 = this.jText10.getText();
		if (str1 == null || str1.trim().length() <= 0) {
			JOptionPane.showMessageDialog(this, "请输入脚本、备份存储路径！");
			return;
		}
		else {
			File file = new File(str1);
			if(!file.exists()) {
				file.mkdirs();
			}
			if(!file.isDirectory()) {
				JOptionPane.showMessageDialog(this, "脚本、备份存储路径只能是目录！");
				return;	
			}
			
		}
		String str2 = this.jText11.getText();
		if (str2 == null || str2.trim().length() <= 0) {
			JOptionPane.showMessageDialog(this, "请输入mysqldump路径！");
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				SelectSchemaFrame.this.btn_start.setEnabled(false);
				SelectSchemaFrame.this.btn_cancle.setEnabled(false);
				for (JCheckBox chk : SelectSchemaFrame.this.chks) {
					chk.setEnabled(false);
				}
				String mysqldump = jText11.getText();//mysqldump路径
				String path = jText10.getText();//脚本、备份存储路径
				content = new StringBuffer();//初始化文本
				content.append("@echo off");
				content.append("\r\n");
				content.append("set \"Ymd=%date:~,4%%date:~5,2%%date:~8,2%\"");
				content.append("\r\n");
				int i = 0;
				for (String schema : SelectSchemaFrame.this.selectedSchemas) {
					File file = new File(path+"\\"+schema);
					if(!file.exists()) {
						file.mkdirs();
					}
					content.append("\""+mysqldump+"\" --no-defaults -u "+userName+" --password="+password+" "+schema+"> "+path+"\\"+schema+"\\"+schema+"-%Ymd%.sql");
					content.append("\r\n");
					i++;
					SelectSchemaFrame.this.label_completed.setText(Converter.toBlank(Integer.valueOf(i)));
					SelectSchemaFrame.this.proccebar_execute.setValue(i * 100 / SelectSchemaFrame.this.total);
				}
				content.append("@echo on");
				createFile(content.toString());
				JOptionPane.showMessageDialog(SelectSchemaFrame.this, "已完成！");
				for (JCheckBox chk : SelectSchemaFrame.this.chks) {
					chk.setEnabled(true);
				}
				SelectSchemaFrame.this.label_completed.setText("0");
				SelectSchemaFrame.this.proccebar_execute.setValue(0);
				SelectSchemaFrame.this.btn_start.setEnabled(true);
				SelectSchemaFrame.this.btn_cancle.setEnabled(true);
			}
		}).start();
	}
	
	/**
	 * 创建bat文件
	 */
	private void createFile(String text) {
		String path = jText10.getText();//脚本、备份存储路径
		FileOutputStream o=null;  
		try {  
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
			File batFile = new File(path+"\\mysql_backup.bat");
			if(!batFile.exists()) {
				batFile.createNewFile();
			}else {
				batFile.delete();
				batFile.createNewFile();
			}
		  o = new FileOutputStream(batFile);  
	      o.write(text.getBytes("GBK"));  
	    }catch (Exception e) {  
		   e.printStackTrace();  
	    }finally{  
		    if(o!=null) {
		    	try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
		    }
	    }  
	}
	
	@SuppressWarnings("static-access")
	private void initSchemas() {
		List<String> schemaList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.db.getConnection();
			String sql = "show databases";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			@SuppressWarnings("unused")
			int count = 0;
			while (rs.next()) {
				String schema = rs.getString(1);
				schemaList.add(schema);
				count++;
			}
			
			
			//获取mysql的本机地址
			String sql2 = "select @@basedir as basePath from dual;";
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
			while (rs.next()) {
				//String a = rs.getString(1);
				//System.out.println(a);
				mysqldump =  rs.getString(1); 
				System.out.println(mysqldump);	
				// G:/mysql/
				String pf = mysqldump.substring(0, 2);
				System.out.println(pf);
				
				String lj = mysqldump.substring(3, mysqldump.length() -1);
				
				lj = lj.replaceAll("/", "\\\\");
				System.out.println(lj);
				
				mysqldump = pf+"\\"+lj+"\\bin\\mysqldump";
				System.out.println(mysqldump);	
				//改为动态获取 
				this.jText11.setText(mysqldump);
			
			}
			
			
		} catch (SQLException se) {
			se.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					new StringBuilder().append("处理数据时出现异常！")
							.append(se.getMessage()).toString());
			log.error("数据库错误", se);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			db.close(conn);
			db.close(stmt);
			db.close(rs);
		}
		GridLayout layout = new GridLayout(schemaList.size(), 1);
		this.panel_schemas.setLayout(layout);
		for (String schema : schemaList) {
			final JCheckBox chkbox = new JCheckBox(schema);
			chkbox.setSelected(true);//默认全部选中
			SelectSchemaFrame.this.selectedSchemas.add(chkbox.getText());
			SelectSchemaFrame.this.changeTotal();
			
			chkbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chkbox.isSelected())
						SelectSchemaFrame.this.selectedSchemas.add(chkbox
								.getText());
					else {
						SelectSchemaFrame.this.selectedSchemas.remove(chkbox
								.getText());
					}
					new Thread(new Runnable() {
						public void run() {
							SelectSchemaFrame.this.changeTotal();
						}
					}).start();
				}
			});
			this.chks.add(chkbox);
			this.panel_schemas.add(chkbox);
		}
	}

	private void changeTotal() {
		if (this.selectedSchemas.isEmpty()) {
			this.label_total.setText("0");
			return;
		}
		this.total = SelectSchemaFrame.this.selectedSchemas.size();
		this.label_total
				.setText(Converter.toBlank(Integer.valueOf(this.total)));

	}
}