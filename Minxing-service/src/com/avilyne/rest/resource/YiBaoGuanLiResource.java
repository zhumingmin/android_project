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

@Path("/yibaoguanli")
public class YiBaoGuanLiResource {

	private final static String ShenFenZhengHaoMa = "shenFenZhengHaoma";
	private final static String JiaoFeiQingKuan = "jiaoFeiQingKuan";

	private YiBaoGuanLi yibaoguanli = new YiBaoGuanLi(1, "123456",
			"您需要缴费！");

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
	@Path("cx")
	@Produces(MediaType.APPLICATION_JSON)
	public YiBaoGuanLi getYiBaoGuanLi() {

		System.out.println("Returning result: " + "{"
				+ yibaoguanli.getShenFenZhengHaoMa() + ";"
				+ yibaoguanli.getJiaoFeiQingKuan() + "}");
		System.out.println(yibaoguanli);

		return yibaoguanli;
	}

	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public YiBaoGuanLi postYiBaoGuanLi(
			MultivaluedMap<String, String> yibaoguanliParams) {

		String shenfenzhenghaoma = yibaoguanliParams
				.getFirst(ShenFenZhengHaoMa);
		String jiaofeiqingkuan = yibaoguanliParams.getFirst(JiaoFeiQingKuan);

		System.out.println("Storing posted " + shenfenzhenghaoma + " "
				+ jiaofeiqingkuan);

		yibaoguanli.setShenFenZhengHaoMa(shenfenzhenghaoma);
		yibaoguanli.setJiaoFeiQingKuan(jiaofeiqingkuan);

		System.out.println("yibaoguanli info: "
				+ yibaoguanli.getShenFenZhengHaoMa() + " "
				+ yibaoguanli.getJiaoFeiQingKuan());

		return yibaoguanli;

	}
}
