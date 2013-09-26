package com.forbes.brian.ltv.examples;

import java.util.Comparator;


public class RecordComparator implements Comparator<Record> {
	@Override
	public int compare(Record lhs, Record rhs) {
		return lhs.getDate().compareTo(rhs.getDate());
	}
}