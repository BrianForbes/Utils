package com.forbes.brian.ltv.load;

import java.sql.Date;
import java.sql.ResultSet;

public class Record {
	
	final Date date;
	final long appId,userId;
	final int spent, spentPaid;
	
	public Record(ResultSet rs) throws Exception {
		date = rs.getDate("dt");
		appId = rs.getLong("app_id");
		userId = rs.getLong("user_id");
		spent = rs.getInt("spent");
		spentPaid = rs.getInt("spent_paid");
	}

	@Override
	public String toString() {
		return "Record [date=" + date + ", appId=" + appId + ", userId="
				+ userId + ", spent=" + spent + ", spentPaid=" + spentPaid
				+ "]";
	}
	
	

	public Date getDate() {
		return date;
	}

	public long getAppId() {
		return appId;
	}

	public long getUserId() {
		return userId;
	}

	public int getSpent() {
		return spent;
	}

	public int getSpentPaid() {
		return spentPaid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (appId ^ (appId >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		Record other = (Record) obj;
		if (appId != other.appId)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	
	

}
