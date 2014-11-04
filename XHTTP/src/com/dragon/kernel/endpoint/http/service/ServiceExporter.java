package com.dragon.kernel.endpoint.http.service;

import com.dragon.kernel.chain.Chain;
import com.dragon.kernel.endpoint.http.XHttp;

public class ServiceExporter
{
	private Object		service;
	private Class<?>	serviceInterface;
	private Service		serviceInfo;
	private Chain		serviceChain;
	private XHttp		xhttp	= null;

	public void init()
	{
		afterPropertiesSet();
	}

	public void afterPropertiesSet()
	{
		this.serviceInfo = this.xhttp.getServiceFactory().create(this.serviceInterface, this.service);

		this.xhttp.getServiceRegistry().register(this.serviceInfo.getServiceName(), this.serviceInfo);

		this.serviceInfo.setServiceChain(this.serviceChain);
	}

	public void setServiceBean(Object serviceObj)
	{
		this.service = serviceObj;
	}

	public Object getServiceBean()
	{
		return this.service;
	}

	public void setServiceClass(Class<?> serviceIntf)
	{
		this.serviceInterface = serviceIntf;
	}

	public Class<?> getServiceClass()
	{
		return this.serviceInterface;
	}

	public void setXhttp(XHttp xhttp)
	{
		this.xhttp = xhttp;
	}

	public XHttp getXhttp()
	{
		return this.xhttp;
	}

	public void setServiceFilter(Chain chain)
	{
		this.serviceChain = chain;
	}

	public Chain getServiceFilter()
	{
		return this.serviceChain;
	}
}