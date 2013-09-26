package com.forbes.brian.ltv.examples;



import java.sql.Date;
import java.util.*;

public class User {

	final int id;
	List<Record> trans;
	private boolean sorted;
	int totalTrans;
	
	public User(int id) {
		super();
		this.id = id;
		trans = new ArrayList<Record>();
		sorted = true;
	}
	
	
	/*
	public double getTx(){
		return toDouble( getLastDate() );
	}
	*/
	
	@Override
	public String toString() {
		return "User [id=" + id + ", trans=" + trans + "]";
	}

	public Date getLastDate(){
		sort();
		return trans.get(trans.size() -1).getDate();
	}

	public void add(Record r){
		if(id != r.getId())
			return;
		trans.add(r);
		totalTrans += 1;//r.getNumber();
		sorted = false;
	}
	
	private void sort(){
		if(sorted)
			return;
		Collections.sort(trans, new RecordComparator());
		sorted = true;
	}
	
	public int getTotalTrans(){
		return totalTrans;
	}
	
	public List<Record> getTrans(){
		sort();
		return trans;
	}
	
	public Date firstDate(){
		sort();
		return trans.get(0).getDate();
	}
	
	public Date lastDate(){
		sort();
		return trans.get(trans.size()-1).getDate();
	}
	
	public double firstAmount(){
		sort();
		return trans.get(0).getAmount();
	}
	
	public double lastAmount(){
		sort();
		return trans.get(trans.size()-1).getAmount();
	}
	
	
	
}
