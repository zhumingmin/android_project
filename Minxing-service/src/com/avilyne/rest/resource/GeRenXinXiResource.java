package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.GeRenXinXi;

@Path("/gerenxinxi")
public class GeRenXinXiResource {

	private final static String Name = "name";
	private final static String Number = "number";
	private final static String Idnumber = "idnumber";

	private GeRenXinXi gerenxinxi = new GeRenXinXi(1, "zhangsan", "123456",
			"123456");

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// 从服务器获得信息
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	@GET
	@Path("xinxi")
	@Produces(MediaType.APPLICATION_JSON)
	public GeRenXinXi getGeRenXinXi() {

		System.out
				.println("Returning result: " + "{" + gerenxinxi.getName()
						+ ";" + gerenxinxi.getNumber()
						+  ";" + gerenxinxi.getIdNumber() + "}");

		return gerenxinxi;
	}

}
