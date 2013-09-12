package com.forbes.brian.utils.io;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class IOUtils {

	public static final String ENCODE = "UTF-8";
	
	public static Properties getProperties(String file) throws IOException{
		InputStream is = getInputStream(file);
		Properties prop = new Properties();
		prop.load(is);
		return prop;
	}
	
	public static InputStream getInputStream(String fileName) {
		//System.out.println("Opening file : "+fileName);
		InputStream is = IOUtils.class.getClassLoader().getResourceAsStream(fileName);
		return is;
	}
	
	public static BufferedReader getReader(String fileName, String encoding) throws UnsupportedEncodingException{
		InputStream is =  getInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
		return br;
	}
	
	public static BufferedReader getReader(String fileName) throws UnsupportedEncodingException{
		InputStream is = getInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, ENCODE));
		return br;
	}
	
	
	public static BufferedWriter getWriter(String fileName) throws IOException{
		FileWriter writer = new FileWriter( fileName ); 
		BufferedWriter out = new BufferedWriter( writer );
		return out;
	}
	
	public static void main(String[] args) throws Exception {
		
		
		BufferedReader br = IOUtils.getReader("Author.csv");
		br.close();
		
		


	}
	
	
}
