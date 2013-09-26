package com.forbes.brian.ltv.examples;

import java.sql.Date;


public class Record {
	final int id;
	final Date date;
	int number;
	double amount;
	
	public Record(int id, Date date, int number, double value) {
		super();
		this.id = id;
		this.date = date;
		this.number = number;
		this.amount = value;
	}

	public void setNumber(int number) {
		this.number = number;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getNumber() {
		return number;
	}

	public double getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", date=" + date + ", number=" + number
				+ ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + number;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (number != other.number)
			return false;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		return true;
	}
	
	
	
}
