package com.zhao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class MySqlDB {
	private static final Logger log = Logger.getLogger(MySqlDB.class);
	// ��ݿ�����
	private String URL;
	private String NAME;
	private String PASS;
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getPASS() {
		return PASS;
	}

	public void setPASS(String pASS) {
		PASS = pASS;
	}

	public MySqlDB(String uRL, String nAME, String pASS) {
		super();
		URL = uRL;
		NAME = nAME;
		PASS = pASS;
	}

	public MySqlDB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName(DRIVER);
		return DriverManager.getConnection(this.URL, this.NAME, this.PASS);
	}

	  public static void close(Connection conn) {
		    if (conn != null)
		      try {
		        conn.close();
		      } catch (SQLException e) {
		        log.error("关闭数据库连接异常", e);
		      }
		  }

		  public static void close(PreparedStatement pstmt)
		  {
		    if (pstmt != null)
		      try {
		        pstmt.close();
		      } catch (SQLException e) {
		        log.error("关闭预处理异常", e);
		      }
		  }

		  public static void close(Statement stmt)
		  {
		    if (stmt != null)
		      try {
		        stmt.close();
		      } catch (SQLException e) {
		        log.error("关闭声明异常", e);
		      }
		  }

		  public static void close(ResultSet rs)
		  {
		    if (rs != null)
		      try {
		        rs.close();
		      } catch (SQLException e) {
		        log.error("关闭结果集异常", e);
		      }
		  }
}
