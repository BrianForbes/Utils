package com.forbes.brian.ltv.load;

import java.util.*;

public class Users {

	Map<Long,User> users;
	
	public Users() {
		users = new HashMap<Long,User>();
	}
	
	public Map<Long,User> getUsers(){
		return users;
	}
	
	public void update(Record r){
		long id = r.getUserId();
		User u = users.get(id);
		if(u == null)
			u = new User(id);
		u.update(r);
		users.put(id, u);
	}
	
}
