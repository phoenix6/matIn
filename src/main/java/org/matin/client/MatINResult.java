package org.matin.client;

public class MatINResult {

	protected int code;
	protected String message;
	protected Exception serverSideException;
	
	public MatINResult() { }
	
	public MatINResult(String message, int code) 
	{
		this.message = message;
		this.code = code;
	}
	
	public MatINResult(String message, int code, MatINException ex) 
	{
		this.message = message;
		this.code = code;
		this.serverSideException = ex;
	}
	
	
	public String getMessage()
	{
		return message;
	}
	
	public Exception getServerSideException()
	{
		return serverSideException;
	}
}
