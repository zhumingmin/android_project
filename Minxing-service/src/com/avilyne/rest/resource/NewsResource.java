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

import com.avilyne.rest.model.News;
import com.avilyne.rest.model.ZiDingYi;

import com.avilyne.service.FuzzyQueryService;

import com.avilyne.service.KeyWordService;
import com.avilyne.service.NewsService;

@Path("/news")
public class NewsResource {
	private final static String Title = "title";
	private final static String Category = "category";
	private final static String Pubdate = "pubdate";
	private final static String Readnumber = "readnumber";
	private final static String Likenumber = "likenumber";
	private final static String Unlikenumber = "unlikenumber";
	private final static String Body = "body";
	private ZiDingYi zidingyi = new ZiDingYi();
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

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public ZiDingYi postZiDingYi(MultivaluedMap<String, String> zidingyiParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String title = zidingyiParams.getFirst(Title);

		String category = zidingyiParams.getFirst(Category);
		String pubdate = zidingyiParams.getFirst(Pubdate);
		String readnumber = zidingyiParams.getFirst(Readnumber);
		String likenumber = zidingyiParams.getFirst(Likenumber);
		String unlikenumber = zidingyiParams.getFirst(Unlikenumber);
		String body = zidingyiParams.getFirst(Body);

		zidingyi.setTitle(Title);
		zidingyi.setCategory(Category);
		zidingyi.setPubDate(Pubdate);
		zidingyi.setRead(Readnumber);
		zidingyi.setLike(Likenumber);
		zidingyi.setUnlike(Unlikenumber);
		zidingyi.setBody(Body);

		// 验证处理
		boolean zidingyifuwu = newsservice.zidingyiservice(title, category,
				pubdate, readnumber, likenumber, unlikenumber, body, null,
				null, 0);// 空指针异常
		if (zidingyifuwu) {
			System.out.print("上传成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("上传失败\n");
		}
		return zidingyi;

	}
}
