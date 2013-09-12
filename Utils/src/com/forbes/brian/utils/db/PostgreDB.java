package com.forbes.brian.utils.db;

import java.sql.*;
import java.util.*;

import com.forbes.brian.utils.io.PropertyReader;

public class PostgreDB {

	
	static{
		try{
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}

	
	private static Connection getConnection(String configFile) throws Exception{
		Map<String,String> props = PropertyReader.read(configFile);
		String url = String.format(
				"jdbc:postgresql://%s:%s/%s",
				props.get("host"),
				props.get("port"),
				props.get("dbname"));
		Connection conn = DriverManager.getConnection(url,
				props.get("user"),props.get("password"));
		return conn;
	}
	
	public static Connection getRemoteConnection() throws Exception{
		return getConnection("config.db.postgre.remote");
	}
	

	
	public static Connection getLocalConnection() throws Exception{
		return getConnection("config.db.postgre.local");
	}
	
}
