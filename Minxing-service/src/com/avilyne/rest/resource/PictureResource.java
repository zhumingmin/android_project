package com.avilyne.rest.resource;

import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import sun.misc.BASE64Decoder;

import com.avilyne.rest.model.Picture;
import com.sun.jersey.core.util.Base64;

@Path("/uploadpicture")
public class PictureResource {

	private final static String UploadPicture = "uploadpicture";
	private final static String PicPath = "picPath";

	private Picture picture = new Picture();
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

	/**
	 * 通过BASE64Decoder解码，并生成图片
	 * 
	 * @param imgStr
	 *            解码后的string
	 * @return
	 */
	public static boolean stringToImage(String imgStr, String imgFilePath) {
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null)
			return false;
		try {
			// Base64解码
			BASE64Decoder decoder = new BASE64Decoder();
			imgStr = imgStr.replace("data:image/jpeg;base64,", "");
			// byte[] b = Base64.decode(imgStr);
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Basic "is the service running" test
	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	// 上传信息到服务器
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Picture postUploadPicture(
			MultivaluedMap<String, String> uploadpictureParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		String uploadpicture = uploadpictureParams.getFirst(UploadPicture);
		String picPath = uploadpictureParams.getFirst(PicPath);
		String cunchudizhi = "F:\\Tomcat 7.0\\webapps\\RestWebService\\upload\\23.jpeg";

		byte[] c = Base64.decode(uploadpicture);
		System.out.println("Storing posted " + "编码后的照片参数:\n" + uploadpicture
				+ "\n照片地址：" + picPath + "\n是否转换成功："
				+ stringToImage(uploadpicture, cunchudizhi) + "\n解码后的照片参数" + c);

		picture.setUploadPicture(UploadPicture);
		picture.setpicPath(PicPath);
		System.out.println("hujiqianchu info: " + picture.getUploadPicture()
				+ picture.getpicPath());

		return picture;

	}

}
