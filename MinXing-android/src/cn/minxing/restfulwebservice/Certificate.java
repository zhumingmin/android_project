package cn.minxing.restfulwebservice;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate {
	/**
	 * 
	 */
	//private String certificate;
	private Long certificateId;
	private String type;
	private String staff;
	private String villagerId;
	private String attachment;
	private String status;
	private String checkDate;
	private String evaluation;
	
	
//	public Certificate(Long certificateId, String type, String staff,
//			String villagerId, String attachment, String status,
//			Timestamp checkDate, String evaluation, String links) {
//		super();
//		this.certificateId = certificateId;
//		this.type = type;
//		this.staff = staff;
//		this.villagerId = villagerId;
//		this.attachment = attachment;
//		this.status = status;
//		this.checkDate = checkDate;
//		this.evaluation = evaluation;
//		
//	}


//	public Certificate() {
//		super();
//		// TODO Auto-generated constructor stub
//	}

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getVillagerId() {
		return villagerId;
	}

	public void setVillagerId(String villagerId) {
		this.villagerId = villagerId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}



	@Override
	public String toString() {
		return "Certificate [certificateId=" + certificateId + ", type=" + type
				+ ", staff=" + staff + ", villagerId=" + villagerId
				+ ", attachment=" + attachment + ", status=" + status
				+ ", checkDate=" + checkDate + ", evaluation=" + evaluation
				+ "]";
	}

}