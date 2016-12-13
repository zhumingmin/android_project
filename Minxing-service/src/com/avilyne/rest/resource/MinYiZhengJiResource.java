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

import com.avilyne.rest.model.MinYiZhengJi;

import com.avilyne.rest.model.PiaoShuNumber;

import com.avilyne.service.GongGaoService;
import com.avilyne.service.MinYiZhengJiService;

@Path("/vote")
public class MinYiZhengJiResource {
	private final static String PiaoShu = "piaoShu";
	private final static String CanXuanRen = "canXuanRen";

	private PiaoShuNumber piaoshunumber = new PiaoShuNumber();
	MinYiZhengJiService minyizhengjiservice = new MinYiZhengJiService();
	GongGaoService gonggaoservice = new GongGaoService();

	ArrayList<String> canxuanrenlist = minyizhengjiservice.canxuanren();
	ArrayList<String> canxuanzhiwulist = minyizhengjiservice.canxuanzhiwu();
	ArrayList<String> gerenshijilist = minyizhengjiservice.gerenshiji();
	ArrayList<String> piaoshulist = minyizhengjiservice.getpiaoshu();

	String gonggao = gonggaoservice.toupiaogonggao();
	String shijian = gonggaoservice.toupiaotime();

	private MinYiZhengJi minyizhengji = new MinYiZhengJi(1, canxuanrenlist,
			canxuanzhiwulist, gerenshijilist, piaoshulist, gonggao, shijian);

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public PiaoShuNumber postPiaoShuNumber(
			MultivaluedMap<String, String> piaoshunumberParams) {
		String piaoshu = piaoshunumberParams.getFirst(PiaoShu);
		String canxuanren = piaoshunumberParams.getFirst(CanXuanRen);
		piaoshunumber.setPiaoShu(piaoshu);
		piaoshunumber.setCanXuanRen(canxuanren);
		// ��֤����
		boolean piaoshuchange = minyizhengjiservice.setpiaoshu(piaoshu,
				canxuanren);// ��ָ���쳣
		if (piaoshuchange) {
			System.out.print("���ݸ��³ɹ���\n");

			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("���ݸ���ʧ�ܣ�\n");
		}
		return piaoshunumber;

	}

	// �ӷ����������Ϣ
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";
	}

	@GET
	@Path("minyi")
	@Produces(MediaType.APPLICATION_JSON)
	public MinYiZhengJi getMinYiZhengJi() {

		return minyizhengji;
	}

}
