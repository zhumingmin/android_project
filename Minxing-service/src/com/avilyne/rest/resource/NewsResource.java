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

import com.avilyne.rest.model.News;
import com.avilyne.rest.model.YiBaoGuanLi;
import com.avilyne.service.NewsService;

@Path("/news")
public class NewsResource {

	private final static String ShenFenZhengHaoMa = "shenFenZhengHaoma";
	private final static String JiaoFeiQingKuan = "jiaoFeiQingKuan";

	private News news = new News(1, "二十国集团民间社会会议开幕 习近平致贺信", "为民服务的村委",
			"2016-07-06", 0,
			"2016年二十国集团民间社会（C20）会议5日在山东省青岛市开幕，中共中央总书记、国家主席习近平向会议发来贺信。",
			"http://news.163.com/16/0705/18/BR7U2NM300014PRF.html");

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
	public News getNews() {
		NewsService ns = new NewsService();
		System.out.println("Returning result: " + "{" + news.getTitle() + ";"
				+ news.getAuthor() + ";" + news.getPubDate() + ";"
				+ news.getCommentCount() + ";" + news.getBody() + ";"
				+ news.getLink() + "}");
		System.out.println(news);

		return news;
	}

}
