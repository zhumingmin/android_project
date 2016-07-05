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

import com.avilyne.rest.model.BaoJianGuanLi;
import com.avilyne.service.BaoJianService;
import com.avilyne.service.HuJiService;

@Path("/baojianguanli")
public class BaoJianGuanLiResource {

	private final static String BaoJianXiangMu = "baojianxiangmu";
	private final static String BaoJianDiZhi = "baojiandizhi";
	private final static String LianXiFangShi = "lianxifangshi";
	private final static String YuYueShiJian = "yuyueshijian";

	private BaoJianGuanLi baojianguanli = new BaoJianGuanLi();
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

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	// Basic "is the service running" test
	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public BaoJianGuanLi postBaoJianGuanLi(
			MultivaluedMap<String, String> baojianguanliParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String baojianxiangmu = baojianguanliParams.getFirst(BaoJianXiangMu);

		String baojiandizhi = baojianguanliParams.getFirst(BaoJianDiZhi);
		String lianxifangshi = baojianguanliParams.getFirst(LianXiFangShi);
		String yuyueshijian = baojianguanliParams.getFirst(YuYueShiJian);

		System.out.println("Storing posted " + "{报建项目: " + baojianxiangmu + " "
				+ ";报建地址: " + baojiandizhi + "  " + ";联系方式: " + lianxifangshi
				+ ";预约时间: " + yuyueshijian + "}");

		baojianguanli.setBaoJianXiangMu(BaoJianXiangMu);
		baojianguanli.setBaoJianDiZhi(BaoJianDiZhi);
		baojianguanli.setLianXiFangShi(LianXiFangShi);
		baojianguanli.setYuYueShiJian(YuYueShiJian);

		System.out.println("hujiqianchu info: "
				+ baojianguanli.getBaoJianXiangMu() + " "
				+ baojianguanli.getBaoJianDiZhi() + " "
				+ baojianguanli.getLianXiFangShi() + " "
				+ baojianguanli.getYuYueShiJian() + " ");
		BaoJianService baojianservice = new BaoJianService();

		// 验证处理
		boolean baojian = baojianservice.hujiservice(baojianxiangmu,
				baojiandizhi, lianxifangshi, yuyueshijian, 0);// 空指针异常
		if (baojian) {
			System.out.print("报建信息上传成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("报建信息上传失败\n");
		}
		return baojianguanli;

	}
}
