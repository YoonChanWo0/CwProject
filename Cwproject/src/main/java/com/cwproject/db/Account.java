package com.cwproject.db;

import javax.naming.InsufficientResourcesException;

public class Account {
	private String anumber; // 계좌번호
	private int password; // 비밀번호
	private int deposit; // 입금
	private int withdraw; // 출금
	private int account_id; // 계좌 아이디
	private int customer_id; // 고객 아이디
	private int balance; // 잔액
	private int makeacc; // 계좌등록
	
	
	public Account(String anumber,int password){
		this.anumber=anumber;
		this.password=password;
		this.balance=0;
		this.deposit = 0;
		this.withdraw = 0;
		this.account_id = account_id;
		this.customer_id = customer_id;
		this.makeacc = 0;
	}


	public String getAnumber() {
		return anumber;
	}

	public void setAnumber(String anumber) {
		this.anumber = anumber;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public int getMakeacc() {
		return makeacc;
	}

	public void setMakeacc(int makeacc) {
		this.makeacc = makeacc;
	}

//	@Override
//    public String toString() {
//        return "Account{name='" + name + "', snumber=" + snumber + ", anumber=" + anumber + ", balance=" + balance + "}";
//    }
}

