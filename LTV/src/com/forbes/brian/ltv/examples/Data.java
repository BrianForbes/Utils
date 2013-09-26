package com.forbes.brian.ltv.examples;

import static com.forbes.brian.ltv.functions.Functions.OPT_PARAMS;
import static com.forbes.brian.ltv.functions.Functions.likelihood;

import java.sql.Date;
import java.util.*;

import com.forbes.brian.ltv.functions.Params;
import com.forbes.brian.utils.misc.TimeUtils;

/** Data split */
public class Data {
	
	List<Record> data;
	Date splitDate;
	List<Record> train, test;
	
	List<Date> trainDates;
	
	Map<Integer,User> trainUsers, testUsers;
	
	public Data(List<Record> data,Date date) throws Exception {
		super();
		splitDate = date;
		this.data = data;
		splitData();
		initUsers();
		initDates();
	}
	
	
	
	
	public double logLik(double [] params){
		double lik = 0;
		for(User u : trainUsers()){
			lik += Math.log( likelihood(params,getX(u),getTx(u),getT()) );
		}
		return lik;
	}

	/** Number of repeat purchases. */
	public double getX(User u){
		return u.getTrans().size();
	}
	
	/** last observed transaction date */
	public double getTx(User u){
		return toDouble( u.getLastDate() );
	}
	
	public double getT(){
		return toDouble( getLastDate() );
	}
	
	// Units are number of weeks
	public double toDouble(Date date){
		double diff = TimeUtils.dayDiff(getFirstDate(), date) ;
		//double val = diff /7.0 - 6.85;
		// System.out.println(diff);
		double val = diff /7.0;
		return val;
	}
	
	public Date getFirstDate(){
		return trainDates.get(0);
	}
	
	public Date getLastDate(){
		return trainDates.get(trainDates.size() -1);
	}
	
	public Collection<User> trainUsers(){
		return trainUsers.values();
	}
	
	public User trainUser(int id){
		return trainUsers.get(id);
	}
	
	private void initDates(){
		Set<Date> dates = new LinkedHashSet<Date>();
		for(User u : trainUsers.values())
			for(Record r : u.getTrans())
				dates.add(r.getDate());
		trainDates = new ArrayList<Date>(dates);
		Collections.sort(trainDates);
	}
	
	private void initUsers() throws Exception {
		trainUsers = new HashMap<Integer,User>();
		testUsers = new HashMap<Integer,User>();
		for(Record r : train){
			int id = r.getId();
			User u = trainUsers.get(id);
			if(u == null) u = new User(id);
			u.add(r);
			trainUsers.put(id, u);
		}
		for(Record r : test){
			int id = r.getId();
			User u = testUsers.get(id);
			if(u == null) u = new User(id);
			u.add(r);
			testUsers.put(id, u);
		}
		// Remove first transactions
		Set<Integer> ids = new HashSet<Integer>(trainUsers.keySet());
		for(Integer id : ids){
			if(trainUsers.get(id).getTrans().size() < 2)
				trainUsers.remove(id);
			else
				trainUsers.get(id).getTrans().remove(0);
		}
	}
	
	
	private void splitData() throws Exception{
		Collections.sort(data,new RecordComparator());
		train = new ArrayList<Record>();
		test =  new ArrayList<Record>();
		for(Record r : data){
			if(r.getDate().after(splitDate))
				test.add(r);
			else
				train.add(r);
		}
		Collections.sort(train,new RecordComparator());
		Collections.sort(test,new RecordComparator());
		System.out.println("Train/test split = " + train.size()/ (double) test.size());
	}
	
	
	
}
