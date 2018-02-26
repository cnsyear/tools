package com.zhao;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TreeSet<String> selectedSchemas = new TreeSet();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<JCheckBox> chks = new ArrayList();
	private Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
	private Map<String, String> map2 = new HashMap<String, String>();
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
	private JLabel jLabel10;// 文件路径
	private JLabel jLabel11;// 实体类包名
	private JTextField jText10;
	private JTextField jText11;
	private JLabel label_completed;
	private JLabel label_dbUrl;
	private JLabel label_total;
	private JPanel panel_schemas;
	private JProgressBar proccebar_execute;
	private JScrollPane scroll_schemas;
	//
	private String authorName = "jiabin Zhao";// 作者名字
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*

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
		setTitle("MySql Table To Java");

		this.btn_start.setText("开始生成");
		this.btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SelectSchemaFrame.this.btn_startActionPerformed(evt);
			}
		});
		this.jLabel1.setText("数据库：");
		this.jLabel10.setText("Java类存储路径：");
		this.jLabel11.setText("java类包名：");
		this.jText10.setText("D:\\entity\\");
		this.jText11.setText("cn.com.highset.bean");
		this.btn_cancle.setText("取消");
		this.btn_cancle.setToolTipText("");
		this.btn_cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SelectSchemaFrame.this.btn_cancleActionPerformed(evt);
			}
		});
		this.scroll_schemas.setBorder(BorderFactory
				.createTitledBorder("选择table"));

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

		this.jLabel5.setText("张表");

		this.jLabel6.setText("张表");

		this.jLabel7.setText("Made by: Jiabin Zhao");

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
			JOptionPane.showMessageDialog(this, "请选择需要生成实体的table！");
			return;
		}
		String str1 = this.jText10.getText();
		if (str1 == null || str1.trim().length() <= 0) {
			JOptionPane.showMessageDialog(this, "请输入Java类存储路径！");
			return;
		}
		String str2 = this.jText11.getText();
		if (str2 == null || str2.trim().length() <= 0) {
			JOptionPane.showMessageDialog(this, "请输入Java类包名！");
			return;
		}
		new Thread(new Runnable() {
			public void run() {
				SelectSchemaFrame.this.btn_start.setEnabled(false);
				SelectSchemaFrame.this.btn_cancle.setEnabled(false);
				for (JCheckBox chk : SelectSchemaFrame.this.chks) {
					chk.setEnabled(false);
				}
				int i = 0;
				for (String schema : SelectSchemaFrame.this.selectedSchemas) {
					start(schema);
					i++;
					SelectSchemaFrame.this.label_completed.setText(Converter
							.toBlank(Integer.valueOf(i)));
					SelectSchemaFrame.this.proccebar_execute.setValue(i * 100
							/ SelectSchemaFrame.this.total);
				}
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

	@SuppressWarnings("static-access")
	private void start(String tableName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.db.getConnection();

			String sql = "select * from " + tableName;
			stmt = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = stmt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);

				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")
						|| colTypes[i].equalsIgnoreCase("timestamp")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			try {
				String tName = new String(tableName);
				String[] str = tableName.split("_");
				String fileName = "";
				for (String s : str) {
					s = initcap(s);
					fileName += s;
				}
				String content = parse(colnames, colTypes, colSizes, fileName,
						tName);

				String path = this.jText10.getText();
				File dir = new File(path);
				if (!dir.exists())
					dir.mkdir();
				File directory = new File(path + "\\" + fileName + ".java");
				// System.out.println("绝对路径："+directory.getAbsolutePath());
				// System.out.println("相对路径："+directory.getCanonicalPath());
				// String path = this.getClass().getResource("").getPath();
				// System.out.println(path);
				// System.out.println("src/?/"+
				// path.substring(path.lastIndexOf("/com/",path.length())));
				// String outputPath = directory.getAbsolutePath() + "/src/"+
				// this.packageOutPath.replace(".", "/") + "/"+
				// initcap(tablename) + ".java";
				String outputPath = directory.getAbsolutePath();
				FileWriter fw = new FileWriter(outputPath);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(
					this,
					new StringBuilder().append("处理数据时出现异常！")
							.append(se.getMessage()).toString());
			log.error("数据库错误", se);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn);
			db.close(stmt);
			db.close(rs);
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
			String DBUrl = db.getURL();
			String DBname = DBUrl.substring(DBUrl.lastIndexOf("/") + 1);
			System.out.println("数据库:" + DBname);
			String sql = " select table_name,table_comment from information_schema.tables where table_schema=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, DBname.trim());
			rs = stmt.executeQuery();
			@SuppressWarnings("unused")
			int count = 0;
			while (rs.next()) {
				String schema = rs.getString(1);
				String comment = rs.getString(2);
				schemaList.add(schema);
				map2.put(schema, comment);
				count++;
				StringBuffer sql_column = new StringBuffer("SELECT ");
				sql_column.append(" 	COLUMN_NAME,");
				sql_column.append(" COLUMN_COMMENT  ");
				sql_column.append(" FROM ");
				sql_column.append("   information_schema. COLUMNS  ");
				sql_column.append("  WHERE ");
				sql_column.append(" 	table_schema = ? and table_name=? ");
				PreparedStatement stmt_column = conn
						.prepareStatement(sql_column.toString());
				stmt_column.setString(1, DBname.trim());
				stmt_column.setString(2, schema);
				ResultSet rs_column = stmt_column.executeQuery();
				Map<String, String> item = new HashMap<String, String>();
				while (rs_column.next()) {
					item.put(rs_column.getString(1), rs_column.getString(2));
				}
				map.put(schema, item);
			}

		} catch (SQLException se) {
			se.printStackTrace();
			JOptionPane.showMessageDialog(
					this,
					new StringBuilder().append("处理数据时出现异常！")
							.append(se.getMessage()).toString());
			log.error("数据库错误", se);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes,
			String fileName, String tableName) {

		StringBuffer sb = new StringBuffer();
		String packagePath = "cn";
		packagePath = this.jText11.getText();
		sb.append("package " + packagePath + ";\r\n");
		sb.append("\r\n");
		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("import javax.persistence.*;\r\n");

		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + tableName + " 实体类\r\n");
		sb.append(map2.get(tableName) + " \r\n");
		sb.append("    * " + new Date() + " " + this.authorName + "\r\n");
		sb.append("    */ \r\n\r\n\r\n");

		sb.append(" @Entity \r\n");
		sb.append(" @Table(name=\"" + tableName + "\") \r\n");
		// 实体部分
		sb.append("public class " + fileName + "{\r\n");
		processAllAttrs(sb, tableName);// 属性
		processAllMethod(sb);// get set方法
		processTostringMethod(fileName, sb);
		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 重写toString 方法
	 *
	 * <b>方法描述：</b><br/>
	 * 
	 * <b>创建人：</b>赵佳斌<br/>
	 * 
	 * <b>创建时间：</b>2017年5月3日 上午9:42:10<br/>
	 * 
	 * <b>备注：</b><br搜索/>
	 * 
	 * @version 1.0.0<br/>
	 */
	private void processTostringMethod(String fileName,
			StringBuffer sb) {
		sb.append("\t@Override");
		sb.append("\r\n");
		sb.append("\tpublic String toString(){ ");
		sb.append("\r\n");
		sb.append("\treturn \"");
		sb.append(fileName);
		sb.append("[");
		int len = colnames.length;
		for (int i = 0; i < len; i++) {
			String name = colnames[i];
			if (i == len - 1) {
				sb.append(name).append("=\"").append("+").append(name)
						.append("+");
			} else {
				sb.append(name).append("=\"").append("+").append(name)
						.append("+").append("\",");
			}

		}
		sb.append("\"]\";");
		sb.append("\r\n");
		sb.append("\t}\r\n");
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb, String tableName) {

		for (int i = 0; i < colnames.length; i++) {
			String name = colnames[i];
			if (name.equals("id")) {
				sb.append("   @Id  \r\n");
				sb.append("  @GeneratedValue(strategy = GenerationType.AUTO)   \r\n");
			}
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + name
					+ ";");
			sb.append("//");
			sb.append(map.get(tableName).get(name));
			sb.append("\r\n");
		}

	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append("\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}

	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {

		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}

		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Timestamp";
		} else if (sqlType.equalsIgnoreCase("double")) {
			return "Double";
		}

		return null;
	}
}