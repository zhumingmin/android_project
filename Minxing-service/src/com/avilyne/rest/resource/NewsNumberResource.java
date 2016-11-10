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
import com.avilyne.rest.model.NewsNumber;

import com.avilyne.service.NewsService;

@Path("/newsnumber")
public class NewsNumberResource {
	private final static String Title = "title";
	private final static String Read = "read";
	private final static String Like = "like";
	private final static String Unlike = "unlike";
	NewsService newsservice = new NewsService();

	private NewsNumber news = new NewsNumber();
	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// �ӷ����������Ϣ

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public NewsNumber postNews(MultivaluedMap<String, String> newsParams) {
		String title = newsParams.getFirst(Title);
		String read = newsParams.getFirst(Read);

		String like = newsParams.getFirst(Like);
		String unlike = newsParams.getFirst(Unlike);
		news.setTitle(Read);
		news.setRead(Read);
		news.setLike(Like);
		news.setUnlike(Unlike);

		// ��֤����
		boolean newschange = newsservice.insertTuijian(read, like, unlike,
				title);// ��ָ���쳣
		if (newschange) {
			System.out.print("���ݸ��³ɹ���\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("���ݸ���ʧ�ܣ�\n");
		}
		return news;

	}

}
