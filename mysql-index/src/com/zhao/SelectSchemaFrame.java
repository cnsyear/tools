package com.zhao;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
	private JLabel label_completed;
	private JLabel label_dbUrl;
	private JLabel label_total;
	private JPanel panel_schemas;
	private JProgressBar proccebar_execute;
	private JScrollPane scroll_schemas;

	public SelectSchemaFrame(MainFrame mainFrame, MySqlDB db, String dbUrl) {
		this.mainFrame = mainFrame;
		this.db = db;
		initComponents();
		setLocationRelativeTo(null);
		initSchemas();
		this.label_dbUrl.setText(dbUrl);

	}

	private void initComponents() {
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

		this.btn_start.setText("开始创建");
		this.btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				SelectSchemaFrame.this.btn_startActionPerformed(evt);
			}
		});
		this.jLabel1.setText("数据库：");
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
			JOptionPane.showMessageDialog(this, "请选择table！");
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
			String sql  = "show index from " +tableName;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);  
			while(rs.next()){   
				try {
					String Key_name = rs.getString("Key_name");
			         if(!"PRIMARY".equals(Key_name)){
			        	 sql = "ALTER TABLE "+tableName+" DROP INDEX "+Key_name; 
			        	 stmt = conn.prepareStatement(sql);
			        	 stmt.execute();
			         }
				} catch (Exception e) {
					// TODO: handle exception
				}
		         
		     }   
			sql = "select * from " + tableName;
			stmt = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = stmt.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			for (int i = 0; i < size; i++) {
				try {
					String colName = rsmd.getColumnName(i + 1);
					if(!"id".equals(colName)){
						System.out.println(colName);
						sql  ="CREATE INDEX "+colName+" ON "+tableName+" ("+colName+")";
						stmt = conn.prepareStatement(sql);
						stmt.execute();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
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

	
}