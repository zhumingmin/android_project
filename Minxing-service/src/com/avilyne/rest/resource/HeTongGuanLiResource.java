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

import com.avilyne.rest.model.HeTongGuanLi;
import com.avilyne.service.HeTongService;
import com.avilyne.service.HuJiService;

@Path("/hetongguanli")
public class HeTongGuanLiResource {

	private final static String YiFang = "yifang";
	private final static String JiaFang = "jiafang";
	private final static String YueDingFeiYong = "yuedingfeiyong";
	private final static String QianDingNianXian = "qiandingnianxian";
	private final static String QiTaShuoMing = "qitashuoming";

	private HeTongGuanLi hetongguanli = new HeTongGuanLi();
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
	public HeTongGuanLi postHeTongGuanLi(
			MultivaluedMap<String, String> hetongguanliParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String yifang = hetongguanliParams.getFirst(YiFang);

		String jiafang = hetongguanliParams.getFirst(JiaFang);
		String yuedingfeiyong = hetongguanliParams.getFirst(YueDingFeiYong);
		String qiandingnianxian = hetongguanliParams.getFirst(QianDingNianXian);
		String qitashuoming = hetongguanliParams.getFirst(QiTaShuoMing);
		System.out.println("Storing posted " + "{甲方: " + jiafang + " "
				+ ";乙方: " + yifang + "  " + ";约定费用: " + yuedingfeiyong
				+ ";签订年限: " + qiandingnianxian + "  " + ";其他说明: "
				+ qitashuoming + "  " + "}");

		hetongguanli.setYiFang(YiFang);
		hetongguanli.setJiaFang(JiaFang);
		hetongguanli.setYueDingFeiYong(YueDingFeiYong);
		hetongguanli.setQianDingNianXian(QianDingNianXian);
		hetongguanli.setQiTaShuoMing(QiTaShuoMing);
		System.out.println("hujiqianchu info: " + hetongguanli.getJiaFang()
				+ " " + hetongguanli.getYiFang() + " "
				+ hetongguanli.getYueDingFeiYong() + " "
				+ hetongguanli.getQianDingNianXian() + " "
				+ hetongguanli.getQiTaShuoMing());
		HeTongService hetongservice = new HeTongService();

		// 验证处理
		boolean hetong = hetongservice.hetongservice(jiafang, yifang,
				yuedingfeiyong, qiandingnianxian, qitashuoming, 0);// 空指针异常
		if (hetong) {
			System.out.print("合同信息上传成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("合同信息上传成功！\n");
		}
		return hetongguanli;

	}
}
