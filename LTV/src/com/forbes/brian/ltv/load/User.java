package com.forbes.brian.ltv.load;

import java.util.*;

import com.forbes.brian.utils.misc.TimeUtils;

import java.sql.Date;

public class User {

	long id;
	double spent;
	int logins;
	int spendLogins; // logins where cash was spent
	
	Date lastUpdate; // recency

	
	// Map<Long,Double> appSpent;// cash spent on each app
	
	public User(long id) {
		this.id = id;
	//	appSpent = new HashMap<Long,Double>();
	}
	
	public void update(Record r){
		if(id != r.getUserId())
			return;
		spent += r.getSpent();
		++logins;
		lastUpdate = r.getDate();
		if(r.getSpent() > 0)
			++spendLogins;
		// Update app/spent vector
		/*
		Double spent = appSpent.get(r.getAppId());
		if(spent == null) spent = 0.0;
		appSpent.put(r.getAppId(), spent + r.getSpent());
		*/
	}

	public long getId() {
		return id;
	}

	public double getSpent() {
		return spent;
	}
	
	public int getLogins(){
		return logins;
	}
	
	public double getFrequency(Date start, Date end){
		double dayDiff = TimeUtils.dayDiff(start, end);
		return (double) logins / dayDiff;
	}
	
	public double getAvgSpent(){
		return (double) spent / (double) spendLogins;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}