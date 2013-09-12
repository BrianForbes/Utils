package com.forbes.brian.utils.misc;

import java.util.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

// For sorting vectors
public class EntryComparator<T> implements Comparator<Map.Entry<T, Double>> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String,Double> m = new HashMap<String,Double>();
		
		m.put("a", 3.0); m.put("b", 5.0); m.put("c", 1.0);m.put("d", 15.0);
		
	
		List<Map.Entry<String, Double>> l = new ArrayList<Map.Entry<String, Double>>(m.entrySet());
		// SORT RECS from highest to lowest rating
		Collections.sort(l,new EntryComparator<String>());
		
		
		for(Map.Entry<String, Double> e: l)
			System.out.println(e.getKey() + " | "+e.getValue());

	}

	@Override
	public int compare(Map.Entry<T, Double> lhs, Map.Entry<T, Double> rhs) {
		double lValue = lhs.getValue();
		double rValue = rhs.getValue();
		if( lValue < rValue ) return 1;
		else if( lValue > rValue )return -1;
		else return 0;
	}

}
