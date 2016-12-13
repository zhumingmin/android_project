package com.avilyne.rest.resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.GET;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.ZiXun;
import com.avilyne.rest.model.ZiXunYueDu;

import com.avilyne.service.LeiBieService;

import com.avilyne.service.ZiXunService;

@Path("/zixun")
public class ZiXunResource {
	private final static String YueDu = "yuedu";
	private final static String BiaoTi = "biaoti";
	ZiXunService zxservice = new ZiXunService();
	ZiXunYueDu zxyd = new ZiXunYueDu();
	LeiBieService lbs = new LeiBieService();
	String leibie = lbs.newleibie();
	ArrayList<String> biaotilist = zxservice.biaotiservice(leibie);
	ArrayList<String> laiyuanlist = zxservice.laiyuanservice(leibie);
	ArrayList<String> yuedulist = zxservice.yueduservice(leibie);
	ArrayList<String> shijianlist = zxservice.shijianservice(leibie);
	ArrayList<String> tupianlist = zxservice.tupianservice(leibie);
	ArrayList<String> neitonglist = zxservice.neirongservice(leibie);
	ArrayList<String> lianjielist = zxservice.lianjieservice(leibie);

	private ZiXun zx = new ZiXun(1, biaotilist, laiyuanlist, yuedulist,
			shijianlist, tupianlist, neitonglist, lianjielist);
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
	@Path("1")
	@Produces(MediaType.APPLICATION_JSON)
	public ZiXun getZiXun() {

		// System.out.println("Returning result: " + "{" + news.getTitle() + ";"
		// + news.getAuthor() + ";" + news.getPubDate() + ";"
		// + news.getCommentCount() + ";" + news.getBody() + ";"
		// + news.getLink() + ";" + news.getPicturepath() + "}");
		// System.out.println(news);

		return zx;
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ZiXunYueDu postZiXunYueDu(
			MultivaluedMap<String, String> zixunyueduParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String biaoti = zixunyueduParams.getFirst(YueDu);
		String yuedu = zixunyueduParams.getFirst(BiaoTi);

		zxyd.setYueDu(YueDu);
		zxyd.setBiaoTi(BiaoTi);

		// 验证处理
		boolean gengxinyuedu = zxservice.gengxinyuedu(biaoti, yuedu);// 空指针异常
		if (gengxinyuedu) {
			System.out.print("上传成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("上传失败\n");
		}
		return zxyd;

	}
}
