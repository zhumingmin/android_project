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

import com.avilyne.rest.model.FanKui;

import com.avilyne.service.FanKuiService;

@Path("/fankui")
public class FanKuiResource {

	private final static String ShouJiXiTong = "shoujixitong";
	private final static String DangQianShiJian = "dangqianshijian";
	private final static String FanKuiNeiRong = "fankuineirong";

	private FanKui fankui = new FanKui();

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public FanKui postFanKui(MultivaluedMap<String, String> fankuiParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String shoujixitong = fankuiParams.getFirst(ShouJiXiTong);

		String dangqianshijian = fankuiParams.getFirst(DangQianShiJian);
		String fankuineirong = fankuiParams.getFirst(FanKuiNeiRong);

		fankui.setShouJiXiTong(ShouJiXiTong);
		fankui.setDangQianShiJian(DangQianShiJian);
		fankui.setFanKuiNeiRong(FanKuiNeiRong);

		FanKuiService fankuiservice = new FanKuiService();

		// 验证处理
		boolean fankuixinxi = fankuiservice.fankuiservice(shoujixitong,
				dangqianshijian, fankuineirong, 0);// 空指针异常
		if (fankuixinxi) {
			System.out.print("上传成功！\n");
		} else {
			System.out.print("上传失败\n");
		}
		return fankui;

	}
}
