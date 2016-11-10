package com.avilyne.rest.resource;

import java.util.ArrayList;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.GET;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import com.avilyne.rest.model.HotWords;

import com.avilyne.service.KeyWordService;

@Path("/hotwords")
public class HotWordsResource {

	KeyWordService kws = new KeyWordService();
	ArrayList<String> kwr = kws.hotwords();

	private HotWords hw = new HotWords(kwr);

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
	public HotWords getHotWords() {

		// System.out.println("Returning result: " + "{" + news.getTitle() + ";"
		// + news.getAuthor() + ";" + news.getPubDate() + ";"
		// + news.getCommentCount() + ";" + news.getBody() + ";"
		// + news.getLink() + ";" + news.getPicturepath() + "}");
		// System.out.println(news);

		return hw;
	}

}
