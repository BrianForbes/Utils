package com.forbes.brian.utils.misc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.forbes.brian.utils.io.IOUtils;

/**
 * 
 * @author brian.forbes
 *
 * Assigns indices to any objects
 */
public class Indexer<T> {

	Map<T,Integer> indices;
	Map<Integer,T> inverse;
	
	public Indexer() {
		indices = new HashMap<T,Integer>();
		inverse = new HashMap<Integer,T>();
	}
	
	public int size(){
		return indices.size();
	}
	
	public Map<Integer,T> getInverse(){
		return inverse;
	}
	
	public int getId(T t){
		Integer id = indices.get(t);
		if( id == null ){
			id = indices.size();
			indices.put(t, id);
			inverse.put(id,t);
		}
		return id;
	}
	
	
	public T getInverse(int id){
		return inverse.get(id);
	}
	
	public void write(String file) throws IOException{
		BufferedWriter bw = IOUtils.getWriter(file);
		for(Map.Entry<Integer, T> e : inverse.entrySet()){
			bw.write( e.getKey() +","+ e.getValue() + "\n");
		}
		bw.close();
	}

	
	
}
