package org.techhub.Repository;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBConfig {
	private static DBConfig db;
	private static Connection conn;
	private static PreparedStatement stmt;
	private static ResultSet rs;
	private static CallableStatement cstmt;

	private DBConfig() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties props = new Properties();
			FileInputStream inputStream = new FileInputStream("src/main/resources/dbconfig.properties");
			props.load(inputStream);
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");

			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	public static synchronized DBConfig getInstance() {
		if (db == null) {
			db = new DBConfig();
		}
		return db;
	}

	public static Connection getConn() {
		return conn;
	}

	public static PreparedStatement getStatement() {
		return stmt;
	}

	public static ResultSet getResult() {
		return rs;
	}

	public static CallableStatement getCallStatement() {
		return cstmt;
	}
}
//    public static void main(String[] args) {
//        DBConfig d = DBConfig.getInstance();
//        if (conn != null) {
//            System.out.println("Database connected at restau");
//        } else {
//            System.out.println("Database not connected");
//        }
//    }
//}
