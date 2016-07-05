package com.avilyne.rest.resource;

import java.io.PrintWriter;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.Login;
import com.avilyne.rest.model.Person;
import com.avilyne.service.Service;

@Path("/login")
public class LoginResource {

	private final static String ZhangHao = "loginzhanghao";
	private final static String MiMa1 = "loginmima";

	private Login login = new Login();
	// private Person person = new Person();

	// The @Context annotation allows us to have certain contextual objects
	// injected into this class.
	// UriInfo object allows us to get URI information (no kidding).
	@Context
	UriInfo uriInfo;

	// Another "injected" object. This allows us to use the information that's
	// part of any incoming request.
	// We could, for example, get header information, or the requestor's
	// address.
	@Context
	Request request;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Login postLogin(MultivaluedMap<String, String> loginParams) {

		String zhanghao = loginParams.getFirst(ZhangHao);

		String mima = loginParams.getFirst(MiMa1);

		login.setZhangHao(zhanghao);

		login.setMiMa1(mima);
		Service service = new Service();
		boolean loginresult = service.login(zhanghao, mima);
		if (loginresult) {
			System.out.print("µÇÂ½³É¹¦£¡\n");
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("µÇÂ½Ê§°Ü£¡\n");

		}
		return login;

	}

}
