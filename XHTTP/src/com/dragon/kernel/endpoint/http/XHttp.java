package com.dragon.kernel.endpoint.http;

import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.endpoint.http.service.ServiceFactory;
import com.dragon.kernel.endpoint.http.service.ServiceRegistry;

public class XHttp
{
	private ServiceFactory	factory;
	private ServiceRegistry	registry;

	public XHttp(ServiceRegistry registry)
	{
		this.registry = registry;

		this.factory = new ServiceFactory();
	}

	public XHttp(ServiceRegistry registry, ServiceFactory factory)
	{
		this.factory = factory;
		this.registry = registry;
	}

	public ServiceRegistry getServiceRegistry()
	{
		return this.registry;
	}

	public ServiceFactory getServiceFactory()
	{
		return this.factory;
	}

	public Service findService(String name)
	{
		return this.registry.findService(name);
	}

	public void unregister()
	{
		this.registry.unregister();
	}

	public void setFactory(ServiceFactory factory)
	{
		this.factory = factory;
	}

	public void setRegistry(ServiceRegistry registry)
	{
		this.registry = registry;
	}
}