package com.avilyne.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.PiaoShuNumber;

import com.avilyne.service.MinYiZhengJiService;

@Path("/piaoshu")
public class PiaoShuResource {
	private final static String PiaoShu = "piaoshu";
	private final static String CanXuanRen = "canxuanren";
	MinYiZhengJiService minyizhengjiservice = new MinYiZhengJiService();

	private PiaoShuNumber piaoshunumber = new PiaoShuNumber();
	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// 从服务器获得信息

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public PiaoShuNumber postPiaoShuNumber(
			MultivaluedMap<String, String> piaoshunumberParams) {
		String piaoshu = piaoshunumberParams.getFirst(PiaoShu);
		String canxuanren = piaoshunumberParams.getFirst(CanXuanRen);
		piaoshunumber.setPiaoShu(piaoshu);
		piaoshunumber.setCanXuanRen(canxuanren);
		// 验证处理
		boolean piaoshuchange = minyizhengjiservice.setpiaoshu(piaoshu,
				canxuanren);// 空指针异常
		if (piaoshuchange) {
			System.out.print("数据更新成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("数据更新失败！\n");
		}
		return piaoshunumber;

	}

}
