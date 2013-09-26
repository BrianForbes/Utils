package com.forbes.brian.ltv.load;
import java.sql.*;
import java.util.*;

import com.forbes.brian.utils.db.Mysql;


public class Loader {

	Users u;
	
	public Loader(){
		u = new Users();
	}

	public static void main(String[] args) throws Exception {
		Loader loader = new Loader();
		loader.load(2013 , 6);
	}


	public void load(int year, int month)throws Exception {
		String table = getTableName( year , month);
		Connection conn = getConnection();
		Statement st = conn.createStatement();
		st.setFetchSize(Integer.MIN_VALUE);
		ResultSet rs = st.executeQuery("select * from " + table + " where spent > 0");
		int m = 0;
		while(rs.next()){
			Record r = new Record(rs);
			u.update(r);
			if(++m % 1000000 == 0)
				//break;
				System.out.println(m);
		}
		st.close();
		rs.close();
		conn.close();
		
		for(Map.Entry<Long, User> e : u.getUsers().entrySet()){
			User u = e.getValue();
			if(u.getSpent() >0)
				System.out.println(e.getKey() + " | " + u.getSpent());
		}
	}
	
	public static Connection getConnection() throws Exception{
		return Mysql.getBiz01Connection("dmd");
	}
	
	private static String getTableName(int year, int month){
		String prefix = "dmd.d_ltvapp_";
		String date = null;
		if(month < 10)
			date = Integer.toString(year) + "0" + Integer.toString(month);
		else
			date = Integer.toString(year) + Integer.toString(month);
		return prefix + date;
	}
	
}
