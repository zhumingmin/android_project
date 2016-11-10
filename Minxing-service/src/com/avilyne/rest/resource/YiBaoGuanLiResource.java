package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.YiBaoGuanLi;
import com.avilyne.service.YiBaoService;

@Path("/yibaoguanli")
public class YiBaoGuanLiResource {

	private final static String ShenFenZhengHaoMa = "shenfenzhenghaoma";

	YiBaoService yibaoservice = new YiBaoService();
	static String shenfenzhenghaoma;
	private final YiBaoGuanLi yibaoguanli = new YiBaoGuanLi();

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

	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public YiBaoGuanLi postYiBaoGuanLi(
			MultivaluedMap<String, String> yibaoguanliParams) {

		 shenfenzhenghaoma = yibaoguanliParams
				.getFirst(ShenFenZhengHaoMa);

		System.out.println("Storing posted " + shenfenzhenghaoma);

		yibaoguanli.setShenFenZhengHaoMa(shenfenzhenghaoma);

		System.out.println("yibaoguanli info: "
				+ yibaoguanli.getShenFenZhengHaoMa());

		return yibaoguanli;

	}

	String result = yibaoservice.yibao(shenfenzhenghaoma);
	private YiBaoGuanLi getyibaoguanli = new YiBaoGuanLi(1,
			shenfenzhenghaoma, result);

	@GET
	@Path("cx")
	@Produces(MediaType.APPLICATION_JSON)
	public YiBaoGuanLi getYiBaoGuanLi() {
		System.out.println(yibaoguanli.getShenFenZhengHaoMa());
		return getyibaoguanli;
	}
}
