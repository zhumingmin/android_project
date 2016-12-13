package com.avilyne.rest.resource;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import sun.misc.BASE64Decoder;

import com.avilyne.rest.model.NfcTag;
import com.avilyne.service.HuJiService;
import com.avilyne.service.NfcTagService;
import com.sun.jersey.core.util.Base64;

@Path("/nfctag")
public class NfcTagResource {

	private final static String NfcTag = "nfctag";

	private NfcTag nfctag = new NfcTag();
	// private Person person = new Person();

	// The @Context annotation allows us to have certain contextual objects
	// injected into this class.
	// UriInfo object allows us to get URI information (no kidding).
	@Context
	UriInfo uriInfo;

	// Another "injected" object. This allows us to use the information that's
	// part of any incoming request.
	// We could, for example, get header information, or the requestor's
	// address.
	@Context
	Request request;

	// Basic "is the service running" test
	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public NfcTag postNfcTag(MultivaluedMap<String, String> nfctagParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");

		String nfc = nfctagParams.getFirst(NfcTag);

		nfctag.setNfcTag(NfcTag);

		NfcTagService nfctagservice = new NfcTagService();

		// 验证处理
		boolean tag = nfctagservice.nfcservice(nfc, 0);// 空指针异常
		if (tag) {
			System.out.print("NFC标签信息上传成功！\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("NFC标签信息上传失败！\n");
		}

		return nfctag;

	}
}
