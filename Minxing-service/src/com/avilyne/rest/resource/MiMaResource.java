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

import com.avilyne.rest.model.MiMa;

import com.avilyne.service.MiMaService;

@Path("/mima")
public class MiMaResource {

	private final static String ZhangHao = "zhanghao";

	private final static String ShouJiHao = "shoujihaoma";

	private final MiMa mima = new MiMa();
	static String zhanghao;
	static String shoujihaoma;
	MiMaService mimaservice = new MiMaService();

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
	public MiMa postMiMa(MultivaluedMap<String, String> mimaParams) {

		zhanghao = mimaParams.getFirst(ZhangHao);

		shoujihaoma = mimaParams.getFirst(ShouJiHao);

		mima.setZhangHao(zhanghao);

		mima.setShouJiHao(shoujihaoma);
		System.out.println("密码 info: " + ","+mima.getZhangHao()
				+ mima.getShouJiHao());
		return mima;
	}

	String result = mimaservice.mima(zhanghao, shoujihaoma);
	
	private MiMa getmima = new MiMa(1, zhanghao, shoujihaoma, result);

	@GET
	@Path("zh")
	@Produces(MediaType.APPLICATION_JSON)
	public MiMa getMiMa() {
		System.out.println(result);
		return getmima;
	}
}
