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

	private News news = new News(1, "��ʮ��������������鿪Ļ ϰ��ƽ�º���", "Ϊ�����Ĵ�ί",
			"2016-07-06", 0,
			"2016���ʮ�����������ᣨC20������5����ɽ��ʡ�ൺ�п�Ļ���й���������ǡ�������ϯϰ��ƽ����鷢�����š�",
			"http://news.163.com/16/0705/18/BR7U2NM300014PRF.html");

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// �ӷ����������Ϣ
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
