package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyAccount {

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MyAccount() {

		id = -1;
		account = "";
		name = "";
		phonenumber = "";

	}

	public MyAccount(long id, String account, String name, String phonenumber) {

		this.id = id;
		this.account = account;
		this.name = name;
		this.phonenumber = phonenumber;
	}

	private long id;
	private String account;
	private String name;
	private String phonenumber;

}