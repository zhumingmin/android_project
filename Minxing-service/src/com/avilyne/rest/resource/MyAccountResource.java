package com.avilyne.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.MyAccount;
import com.avilyne.rest.model.YiBaoGuanLi;
import com.avilyne.service.MyAccountService;

@Path("/myaccount")
public class MyAccountResource {

	private final static String Account = "account";

	MyAccountService myaccountservice = new MyAccountService();
	static String account;
	private final MyAccount myaccount = new MyAccount();

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public MyAccount postMyAccount(
			MultivaluedMap<String, String> myaccountParams) {

		account = myaccountParams.getFirst(Account);

		System.out.println("Storing posted " + account);

		myaccount.setAccount(account);

		System.out.println("yibaoguanli info: " + myaccount.getAccount());

		return myaccount;

	}

	// 从服务器获得信息
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	// Use data from the client source to create a new Person object, returned
	// in JSON format.

	String name = myaccountservice.account1(account);
	String phonenumber = myaccountservice.account2(account);
	private MyAccount getmyaccount = new MyAccount(1, account, name,
			phonenumber);

	@GET
	@Path("cx")
	@Produces(MediaType.APPLICATION_JSON)
	public MyAccount getMyAccount() {
		System.out.println(myaccount.getAccount());
		return getmyaccount;
	}
}
