package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.DengLu;
import com.avilyne.rest.model.Person;

@Path("/denglu")
public class DengLuService {

	private final static String ZhangHao = "zhangHao";
	private final static String MiMa = "miMa";
	private final static String Result = "resultGet";

	private DengLu denglu = new DengLu(1, "123456", "123456", "success");
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

	// Basic "is the service running" test
	// 从服务器获得信息
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	@GET
	@Path("123456")
	@Produces(MediaType.APPLICATION_JSON)
	public DengLu getSampleDengLu() {

		System.out.println("Returning: " + denglu.getZhangHao() + " "
				+ denglu.getMiMa() + " " + denglu.getResult());

		return denglu;
	}

}
