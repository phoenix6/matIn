package org.matin.client;

public class MatINError {

	protected int code;
	protected String message;

	public MatINError() {}
	
	public MatINError(String message)
	{
		this.code = code;
		this.message = message;
	}
	
	public int getCode()
	{
		return code;
	}
	
	public String getMessage()
	{
		return message;
	}
}
