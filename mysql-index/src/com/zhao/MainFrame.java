package com.zhao;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private JButton btn_confirm;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JPanel jPanel1;
	private JPasswordField txt_dbPwd;
	private JTextField txt_dbUrl;
	private JTextField txt_dbUser;

	public MainFrame() {
		initComponents();
		setLocationRelativeTo(null);
	}

	private void initComponents() {
		this.jPanel1 = new JPanel();
		this.jLabel1 = new JLabel();
		this.txt_dbUrl = new JTextField();
		this.jLabel2 = new JLabel();
		this.txt_dbUser = new JTextField();
		this.jLabel3 = new JLabel();
		this.txt_dbPwd = new JPasswordField();
		this.btn_confirm = new JButton();
		this.jLabel5 = new JLabel();
		this.jLabel4 = new JLabel();

		setDefaultCloseOperation(3);
		setTitle("MySql Create Index ");
		setLocationByPlatform(true);
		setResizable(false);

		this.jPanel1.setBorder(BorderFactory.createTitledBorder("连接配置"));
		this.jPanel1.setToolTipText("");

		this.jLabel1.setText("数据库：");

		this.txt_dbUrl.setText("localhost:3306/bizcom_oa");

		this.jLabel2.setText("用户名：");

		this.txt_dbUser.setText("root");
		this.txt_dbUser.setToolTipText("");

		this.jLabel3.setText("密　码：");
		this.txt_dbPwd.setText("root");
		this.btn_confirm.setText("确认");
		this.btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MainFrame.this.btn_confirmActionPerformed(evt);
			}
		});
		this.jLabel5.setFont(new Font("宋体", 0, 10));
		this.jLabel5.setText("（请确保用户名/密码可用!）");

		GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
		this.jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(10, 10, 10)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																this.btn_confirm,
																-1, -1, 32767)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				this.jLabel1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				this.txt_dbUrl))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				this.jLabel2)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				this.txt_dbUser))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				this.jLabel3)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel1Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								this.jLabel5,
																								-1,
																								272,
																								32767)
																						.addComponent(
																								this.txt_dbPwd))))
										.addContainerGap()));

		jPanel1Layout.linkSize(0, new Component[] { this.jLabel1, this.jLabel2,
				this.jLabel3 });

		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(16, 16, 16)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.jLabel1,
																-2, 25, -2)
														.addComponent(
																this.txt_dbUrl,
																-2, -1, -2))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED,
												29, 32767)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.txt_dbUser,
																-2, -1, -2)
														.addComponent(
																this.jLabel2,
																-2, 25, -2))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.jLabel5)
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																this.jLabel3,
																-2, 25, -2)
														.addComponent(
																this.txt_dbPwd,
																-2, -1, -2))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(this.btn_confirm, -2, 36,
												-2).addContainerGap()));

		jPanel1Layout.linkSize(1, new Component[] { this.jLabel1, this.jLabel2,
				this.jLabel3 });

		this.jLabel4.setText("Made by: Jiabin Zhao");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.LEADING)
										.addComponent(this.jPanel1, -1, -1,
												32767)
										.addGroup(
												GroupLayout.Alignment.TRAILING,
												layout.createSequentialGroup()
														.addGap(0, 0, 32767)
														.addComponent(
																this.jLabel4)))
						.addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(this.jPanel1, -2, -1, -2)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(this.jLabel4).addContainerGap(-1, 32767)));

		pack();
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void btn_confirmActionPerformed(ActionEvent evt) {
		this.btn_confirm.setEnabled(false);
		MySqlDB db = new MySqlDB();
		db.setNAME(this.txt_dbUser.getText());
		db.setPASS(this.txt_dbPwd.getText());
		db.setURL("jdbc:mysql://" + this.txt_dbUrl.getText());
		Connection conn = null;
		try {
			conn = db.getConnection();
			SelectSchemaFrame ssFrame = new SelectSchemaFrame(this, db,
					this.txt_dbUrl.getText());
			setVisible(false);
			ssFrame.setVisible(true);
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(this, "连接数据库错误！" + se.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close(conn);
		}
		this.btn_confirm.setEnabled(true);
	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels())
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		});

	}

}
