package com.avilyne.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NfcTag {

	public String getNfcTag() {
		return nfctag;
	}

	public void setNfcTag(String nfctag) {
		this.nfctag = nfctag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NfcTag() {

		id = -1;
		nfctag = "";

	}

	public NfcTag(long id, String nfctag) {

		this.id = id;
		this.nfctag = nfctag;

	}

	private long id;
	private String nfctag;

}