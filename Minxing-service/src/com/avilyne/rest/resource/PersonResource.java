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

import com.avilyne.rest.model.Person;
import com.avilyne.service.Service;

@Path("/account")
public class PersonResource {

	private final static String ZhangHao = "zhanghao";
	private final static String XingMing = "xingming";
	private final static String ShouJiHao = "shoujihaoma";
	private final static String MiMa1 = "mima1";
	private final static String MiMa2 = "mima2";
	Service service = new Service();
	private Person register = new Person();
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

	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Person postRegister(MultivaluedMap<String, String> registerParams) {

		String zhanghao = registerParams.getFirst(ZhangHao);
		String xingming = registerParams.getFirst(XingMing);
		String shoujihaoma = registerParams.getFirst(ShouJiHao);
		String mima1 = registerParams.getFirst(MiMa1);
		String mima2 = registerParams.getFirst(MiMa2);

		System.out.println("Storing posted " + "{账号: " + zhanghao + " "
				+ "姓名: " + xingming + "  " + "手机号码: " + shoujihaoma + "  "
				+ "密码1: " + mima1 + "  " + "密码2: " + mima2 + "}");

		register.setZhangHao(zhanghao);
		register.setXingMing(xingming);
		register.setShouJiHao(shoujihaoma);
		register.setMiMa1(mima1);
		register.setMiMa2(mima2);

		System.out.println("person info: " + register.getZhangHao() + " "
				+ register.getXingMing() + " " + register.getShouJiHao() + " "
				+ register.getMiMa1() + " " + register.getMiMa2());

		boolean registerpost = service.register(zhanghao, xingming,
				shoujihaoma, mima1, mima2, 0);
		if (registerpost) {
			System.out.print("注册成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("注册失败！\n");
		}

		return register;

	}
}
