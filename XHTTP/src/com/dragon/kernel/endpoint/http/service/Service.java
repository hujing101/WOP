package com.dragon.kernel.endpoint.http.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dragon.kernel.chain.Chain;

public class Service
{
	private String						serviceName;
	private Class<?>					intf;
	private Object						serviceObject;
	private Chain						serviceChain;
	private Map<String, List<Method>>	name2method	= new HashMap<String, List<Method>>();

	public Service(Class<?> intf, Object serviceObject)
	{
		this.intf = intf;

		this.serviceObject = serviceObject;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public String getServiceName()
	{
		return this.serviceName;
	}

	public Class<?> getServiceClass()
	{
		return this.intf;
	}

	public Object getServiceObject()
	{
		return this.serviceObject;
	}

	public Method[] getMethods()
	{
		return this.intf.getMethods();
	}

	public List<Method> getMethod(String methodName)
	{
		if (this.name2method.get(methodName) == null)
		{
			this.name2method.put(methodName, new ArrayList<Method>());
		}

		return this.name2method.get(methodName);
	}

	public Map<String, List<Method>> getMap()
	{
		return this.name2method;
	}

	public void setServiceChain(Chain serviceChain)
	{
		this.serviceChain = serviceChain;
	}

	public Chain getServiceChain()
	{
		return this.serviceChain;
	}
}