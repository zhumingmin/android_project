package com.avilyne.rest.resource;

public class ResponseDTO {
	private int status;
	private String message;
	private Object response;

	public ResponseDTO(int status, String message, Object response) {
		super();
		this.status = status;
		this.message = message;
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
}