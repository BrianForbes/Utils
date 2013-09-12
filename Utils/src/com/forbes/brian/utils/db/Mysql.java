package com.forbes.brian.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import com.forbes.brian.utils.io.PropertyReader;

public class Mysql {

	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		//Connection conn = Mysql.getBiz01MommaConnection();
		//conn.close();
	}

	private static Connection getConnection(String configFile) throws Exception{
		Map<String,String> props = PropertyReader.read(configFile);
		String url = String.format(
				"jdbc:mysql://%s:%s/%s?zeroDateTimeBehavior=convertToNull",
				props.get("host"),
				props.get("port"),
				props.get("dbname"));
		Connection conn = DriverManager.getConnection(url,
				props.get("user"),props.get("password"));
		return conn;
	}
	
	public static Connection getBiz01Connection(String dbName) throws Exception{
		Map<String,String> props = PropertyReader.read("config.db.mysql.biz01");
		String url = String.format(
				"jdbc:mysql://%s:%s/%s?zeroDateTimeBehavior=convertToNull",
				props.get("host"),
				props.get("port"),
				dbName);
		Connection conn = DriverManager.getConnection(url,
				props.get("user"),props.get("password"));
		return conn;
	}
	
	public static Connection getBiz01BrianConnection() throws Exception{
		return getBiz01Connection("brian");
	}
	
	public static Connection getAmazonLocal() throws Exception{
		return getConnection("config.db.mysql.local.channel_data");
	}
	
	public static Connection getAmazonConnection() throws Exception{
		return getConnection("config.db.mysql.amazon");
	}

	public static Connection getLocalConnection() throws Exception{
		return getConnection("config.db.mysql.local");
	}
	
	
}
