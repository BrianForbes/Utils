package com.forbes.brian.ltv.examples;

import java.io.BufferedReader;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forbes.brian.utils.io.IOUtils;
import com.forbes.brian.utils.misc.Pair;
import com.forbes.brian.utils.misc.TimeUtils;

public class Loader {

	private List<Record> rawData;
	private List<Record> mergedData;
	
	public List<Record> getRecords()  throws Exception {
		readData();
		mergeData();
		return mergedData;
	}
	
	/** Combine records on same day */
	private void mergeData() throws Exception {
		Map<Pair<Integer,Date>, Record> merged = 
				new HashMap<Pair<Integer,Date>, Record>();
		for(Record r : rawData){
			Pair<Integer,Date> p = new Pair<Integer,Date>(r.getId(),r.getDate());
			Record s = merged.get(p);
			if(s == null){
				s = new Record(r.getId(),r.getDate(),r.getNumber(),r.getAmount());
			}
			else{
				s.setNumber(r.getNumber() + s.getNumber());
				s.setAmount(r.getAmount() + s.getAmount());
			}
			merged.put(p, s);
		}
		mergedData = new ArrayList<Record>(merged.values());
		Collections.sort(mergedData,new RecordComparator());
	}

	private void readData() throws Exception {
		rawData = new ArrayList<Record>();
		BufferedReader br = IOUtils.getReader("CDNOW_sample.txt");
		String line = null;
		int i = 0;
		while((line = br.readLine()) != null){
			if(++i == 1) continue; //skip first line (header)
			Record r = toRecord(line);
			rawData.add(r);
		}
		br.close();
	}

	private static Record toRecord(String line) throws ParseException{
		String [] s = line.split("\\s+");
		int id = Integer.parseInt(s[2]);
		Date date = new java.sql.Date( TimeUtils.fromYYYYmmDD(s[3]).getTime() );
		int count = Integer.parseInt(s[4]);
		double amount = Double.parseDouble(s[5]);
		Record r = new Record(id,date,count,amount);
		return r;
	}
	
}
