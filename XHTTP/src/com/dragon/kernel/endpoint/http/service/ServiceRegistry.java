package com.dragon.kernel.endpoint.http.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry
{
	Map<String, Service>	name2service	= new HashMap<String, Service>();

	public void register(Service service)
	{
		this.name2service.put(service.getServiceName(), service);
	}

	public void register(String serviceName, Service service)
	{
		this.name2service.put(serviceName, service);
	}

	public void unregister(String serviceName)
	{
		this.name2service.remove(serviceName);
	}

	public void unregister()
	{
		this.name2service.clear();
	}

	public Service findService(String serviceName)
	{
		return (Service) this.name2service.get(serviceName);
	}

	public Collection<Service> getServices()
	{
		return this.name2service.values();
	}

	public boolean hasService(String serviceName)
	{
		return this.name2service.containsKey(serviceName);
	}
}