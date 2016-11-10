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

import com.avilyne.rest.model.HeTongGuanLi;
import com.avilyne.rest.model.News;

import com.avilyne.service.FuzzyQueryService;
import com.avilyne.service.HeTongService;
import com.avilyne.service.KeyWordService;
import com.avilyne.service.NewsService;

@Path("/news")
public class NewsResource {

	NewsService newsservice = new NewsService();
	FuzzyQueryService fqs = new FuzzyQueryService();
	KeyWordService kws = new KeyWordService();
	String kwr = kws.newkeyword();
	ArrayList<String> bodylist = fqs.fqs(kwr);
	ArrayList<String> titlelist = fqs.fqtitle(kwr);

	ArrayList<String> picturepath = newsservice.newspicture(kwr);
	ArrayList<String> pubdate = newsservice.getPubdate(kwr);
	ArrayList<String> category = newsservice.getCategory(kwr);
	ArrayList<String> read = newsservice.getRead(kwr);
	ArrayList<String> like = newsservice.getLike(kwr);
	ArrayList<String> unlike = newsservice.getUnlike(kwr);
	private News news = new News(1, titlelist, category, pubdate, read, like,
			unlike, bodylist,
			"http://news.163.com/16/0705/18/BR7U2NM300014PRF.html", picturepath);
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

		// System.out.println("Returning result: " + "{" + news.getTitle() + ";"
		// + news.getAuthor() + ";" + news.getPubDate() + ";"
		// + news.getCommentCount() + ";" + news.getBody() + ";"
		// + news.getLink() + ";" + news.getPicturepath() + "}");
		// System.out.println(news);

		return news;
	}

}
