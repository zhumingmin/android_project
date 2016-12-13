package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.LeiBie;
import com.avilyne.service.LeiBieService;

@Path("/leibie")
public class LeiBieResource {
	String guanjianci = null;
	private final static String LEIBIE = "leibie";
	LeiBieService leibieservice = new LeiBieService();
	private LeiBie leibie = new LeiBie();

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public LeiBie postLeiBie(MultivaluedMap<String, String> leibieParams) {

		guanjianci = leibieParams.getFirst(LEIBIE);

		System.out.println("用户的搜索的关键词: " + guanjianci);

		leibie.setLeiBie(LEIBIE);

		boolean keywordpost = leibieservice.leibieservice(guanjianci, 0);
		if (keywordpost) {
			System.out.print("搜索词写入成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("搜索词写入失败！\n");
		}

		return leibie;

	}

	public String getKeyword() {
		return guanjianci;
	}
}
