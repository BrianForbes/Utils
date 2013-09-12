package com.forbes.brian.utils.db;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

import com.forbes.brian.utils.io.IOUtils;



public class SqlReader {

	private static final String cComment = "//";

	public static String readSql(String fileName) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = IOUtils.getReader(fileName);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith(cComment))
					continue;
				result.append(line + " ");
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result.toString();
	}

}
