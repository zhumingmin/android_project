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

import com.avilyne.rest.model.HuJiQianChu;
import com.avilyne.service.HuJiService;
import com.sun.jersey.core.util.Base64;

@Path("/hujiqianchu")
public class HuJiQianChuResource {

	private final static String QianChuRenXingMing = "qianchurenxingming";
	private final static String ShenFenZhengHaoMa = "shenfenzhenghaoma";
	private final static String QianChuLiYou = "qianchuliyou";
	private final static String UploadPicture = "uploadpicture";
	private final static String PicPath = "picPath";

	private HuJiQianChu hujiqianchu = new HuJiQianChu();
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
	 * ͨ��BASE64Decoder���룬������ͼƬ
	 * 
	 * @param imgStr
	 *            ������string
	 * @return
	 */
	public static boolean stringToImage(String imgStr, String imgFilePath) {
		// ���ֽ������ַ�������Base64���벢����ͼƬ
		if (imgStr == null)
			return false;
		try {
			// Base64����
			BASE64Decoder decoder = new BASE64Decoder();
			imgStr = imgStr.replace("data:image/jpeg;base64,", "");
			// byte[] b = Base64.decode(imgStr);
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					// �����쳣����
					b[i] += 256;
				}
			}
			// ����jpegͼƬ
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
	// �ϴ���Ϣ��������
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public HuJiQianChu postHuJiQianChu(
			MultivaluedMap<String, String> hujiqianchuParams) {
		// String name=new
		// String(request.getParameter("name").getBytes("ISO8859-1"),"utf-8");
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy��MM��dd��HHʱmm��ss��");// ���Է�����޸����ڸ�ʽ
		String time = dateFormat.format(now);
		String qianchurenxingming = hujiqianchuParams
				.getFirst(QianChuRenXingMing);

		String shenfenzhenghaoma = hujiqianchuParams
				.getFirst(ShenFenZhengHaoMa);
		String qianchuliyou = hujiqianchuParams.getFirst(QianChuLiYou);
		String uploadpicture = hujiqianchuParams.getFirst(UploadPicture);
		String picPath = hujiqianchuParams.getFirst(PicPath);
		String cunchudizhi = "F:\\Tomcat 7.0\\webapps\\RestWebService\\upload"
				+ "\\" + time + "-" + qianchurenxingming + "-����֤������" + ".png";
		byte[] c = Base64.decode(uploadpicture);
		System.out.println("Storing posted " + "{Ǩ��������: " + qianchurenxingming
				+ " " + "���֤����: " + shenfenzhenghaoma + "  " + "Ǩ������: \n"
				+ qianchuliyou + "��������Ƭ����:\n" + uploadpicture + "\n��Ƭ��ַ��"
				+ picPath + "\n�Ƿ�ת���ɹ���"
				+ stringToImage(uploadpicture, cunchudizhi) + "\n��������Ƭ����" + c
				+ "}");

		hujiqianchu.setQianChuRenXingMing(QianChuRenXingMing);
		hujiqianchu.setShenFenZhengHaoMa(ShenFenZhengHaoMa);
		hujiqianchu.setQianChuLiYou(QianChuLiYou);
		hujiqianchu.setUploadPicture(UploadPicture);
		hujiqianchu.setpicPath(PicPath);
		System.out.println("hujiqianchu info: "
				+ hujiqianchu.getQianChuRenXingMing() + " "
				+ hujiqianchu.getShenFenZhengHaoMa() + " "
				+ hujiqianchu.getQianChuLiYou()
				+ hujiqianchu.getUploadPicture() + hujiqianchu.getpicPath());
		HuJiService hujiservice = new HuJiService();

		// ��֤����
		boolean huji = hujiservice.hujiservice(qianchurenxingming,
				shenfenzhenghaoma, qianchuliyou, uploadpicture, 0);// ��ָ���쳣
		if (huji) {
			System.out.print("����ҵ����Ϣ�ϴ��ɹ���\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("����ҵ����Ϣ�ϴ��ɹ���\n");
		}

		return hujiqianchu;

	}
}
