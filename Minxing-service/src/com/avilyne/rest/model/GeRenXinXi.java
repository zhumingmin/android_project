package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GeRenXinXi {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getIdNumber() {
		return idnumber;
	}

	public void setIdNumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GeRenXinXi() {

		id = -1;
		name = "";
		number = "";
		idnumber = "";

	}

	public GeRenXinXi(long id, String name, String number, String idnumber) {

		this.id = id;
		this.name = name;
		this.number = number;
		this.idnumber = idnumber;
	}

	private long id;
	private String name;
	private String number;
	private String idnumber;

}