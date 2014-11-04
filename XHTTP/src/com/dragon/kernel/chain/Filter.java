package com.dragon.kernel.chain;

import com.dragon.kernel.exc.XHttpException;

public abstract class Filter
{
	protected Filter	beforeFilter;
	protected Filter	afterFilter;
	protected boolean	pass	= false;

	public abstract void doBefore() throws XHttpException;

	public abstract void doAfter(com.dragon.kernel.endpoint.http.transform.MessageContext context) throws XHttpException;

	public void setNextBeforeFilter(Filter filter)
	{
		this.beforeFilter = filter;
	}

	public Filter getNextBeforeFilter()
	{
		return this.beforeFilter;
	}

	public void setNextAfterFilter(Filter filter)
	{
		this.afterFilter = filter;
	}

	public Filter getNextAfterFilter()
	{
		return this.afterFilter;
	}

	public void setPass(boolean pass)
	{
		this.pass = pass;
	}

	public boolean isPass()
	{
		return this.pass;
	}
}