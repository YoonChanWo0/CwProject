package com.cwproject.db;

public class Customer {
	private int customer_id;
	private String name; // 고객 이름
	private String snumber; // 고객주민번호
	private int phonenum; // 폰번호 
	private String gender; // 성별 
	
	public Customer(int customer_id, String name, String snumber, int phonenum, String gender) {
		this.customer_id = customer_id;
		this.name = name;
		this.snumber = snumber;
		this.phonenum = phonenum;
		this.gender = gender;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSnumber() {
		return snumber;
	}

	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}

	public int getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(int phonenum) {
		this.phonenum = phonenum;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
}
