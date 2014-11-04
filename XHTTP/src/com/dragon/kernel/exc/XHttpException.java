package com.dragon.kernel.exc;

public class XHttpException extends Exception
{
	private static final long	serialVersionUID	= 1L;
	private String				errCode;

	public String getErrCode()
	{
		return this.errCode;
	}

	public XHttpException(String errCode)
	{
		super(errCode);
		this.errCode = errCode;
	}

	public XHttpException(String errCode, Throwable exp)
	{
		super(errCode, exp);
		this.errCode = errCode;
	}
}