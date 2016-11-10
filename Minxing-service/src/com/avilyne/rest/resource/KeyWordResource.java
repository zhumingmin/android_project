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

import com.avilyne.rest.model.KeyWord;

import com.avilyne.service.KeyWordService;

@Path("/keyword")
public class KeyWordResource {
	String guanjianci = null;
	private final static String KEYWORD = "keyword";
	KeyWordService keywordservice = new KeyWordService();
	private KeyWord keyword = new KeyWord();

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	// �ϴ���Ϣ��������
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public KeyWord postKeyword(MultivaluedMap<String, String> keywordParams) {

		guanjianci = keywordParams.getFirst(KEYWORD);

		System.out.println("�û��������Ĺؼ���: " + guanjianci);

		keyword.setKeyWord(KEYWORD);

		boolean keywordpost = keywordservice.keywordservice(guanjianci, 0);
		if (keywordpost) {
			System.out.print("������д��ɹ���\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("������д��ʧ�ܣ�\n");
		}

		return keyword;

	}

	public String getKeyword() {
		return guanjianci;
	}
}
