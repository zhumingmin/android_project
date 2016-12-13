package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.GongGao;

import com.avilyne.service.GongGaoService;

@Path("/toupiaogonggao")
public class TouPiaoGongGaoResource {

	GongGaoService gonggaoservice = new GongGaoService();

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

	String gonggao = gonggaoservice.toupiaogonggao();
	String time = gonggaoservice.toupiaotime();

	private GongGao getgonggao = new GongGao(1, gonggao, time);

	@GET
	@Path("toupiao")
	@Produces(MediaType.APPLICATION_JSON)
	public GongGao getGongGao() {

		return getgonggao;
	}

}
