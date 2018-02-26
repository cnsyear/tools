package com.zhao;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private JTextField txt_dbPwd;
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
		this.txt_dbPwd = new JTextField();
		this.btn_confirm = new JButton();
		this.jLabel5 = new JLabel();
		this.jLabel4 = new JLabel();

		setDefaultCloseOperation(3);
		setTitle("Blog文章生成工具");
		setLocationByPlatform(true);
		setResizable(false);

		this.jPanel1.setBorder(BorderFactory.createTitledBorder("地址配置"));
		this.jPanel1.setToolTipText("");

		this.jLabel1.setText("根路径：");

		//this.txt_dbUrl.setText("D:\\Workspace_github\\KongGaoDeNiao.github.io");
		this.txt_dbUrl.setText(System.getProperty("user.dir"));

		this.jLabel2.setText("源路径：");

		this.txt_dbUser.setText("_inputs");
		this.txt_dbUser.setToolTipText("");

		this.jLabel3.setText("输出路径：");
		this.txt_dbPwd.setText("_posts");
		this.btn_confirm.setText("确认");
		this.btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String _inputs = txt_dbUrl.getText()+"\\"+txt_dbUser.getText();
				String _posts = txt_dbUrl.getText()+"\\"+txt_dbPwd.getText();
				String _backup = txt_dbUrl.getText()+"\\_backup\\_posts";
				//处理文章
				//1.读取源路径的所有文件
				File file = new File(_inputs);
				File[] array = file.listFiles();
				if(array.length == 0) {
					JOptionPane.showMessageDialog(MainFrame.this, "源路径没有发现文件！");
					return;
				}
				for (int i = 0; i < array.length; i++) {
					if(array[i].isFile()) {
						long nowLong = System.currentTimeMillis();
						File from = array[i];
						File backup = new File(_backup+"\\"+nowLong+"——"+from.getName());
						String fileNew = from.getName();
						fileNew = fileNew.substring(0, 11)+nowLong+".md";
						File posts = new File(_posts+"\\"+fileNew);
						try {
							this.copyFile(from,backup);
							this.copyFile(from,posts);
							from.delete();
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				JOptionPane.showMessageDialog(MainFrame.this, "已完成！");
				
			}
			
			/*
			 * 文件复制
			 */
			private void copyFile(File fromFile, File toFile) throws Exception {
				FileInputStream fis = new FileInputStream(fromFile);
				FileOutputStream fos = new FileOutputStream(toFile);
				byte[] b =new byte[1024];
				int n = 0;
				while((n=fis.read(b))!=-1) {
					fos.write(b, 0, n);
				}
				fos.close();
				fis.close();
			}
			
		});
		this.jLabel5.setFont(new Font("宋体", 0, 10));
		this.jLabel5.setText("（请确保源路径目录不为空）");

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

		this.jLabel4.setText("Made by: Jie.Zhao");

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

	 
	public static void main(String[] args) {
		
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
