package com.dragon.kernel.endpoint.http.transform;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.dragon.kernel.endpoint.http.service.Service;

public class MessageContext
{
	private byte[]		responseContent;
	private String		serviceName;
	private Service		service;
	private Method		method		= null;

	private Object[]	args		= null;

	private String		resultCode	= "0";
	private Object		result;
	private byte[]		binput;

	public MessageContext(Service service)
	{
		this.service = service;

		if (service != null)
		{
			this.serviceName = service.getServiceName();
		}
	}

	public MessageContext(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public InputStream getInputStream()
	{
		return new ByteArrayInputStream(this.binput);
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public Service getService()
	{
		return this.service;
	}

	public void setMethod(Method method)
	{
		this.method = method;
	}

	public Method getMethod()
	{
		return this.method;
	}

	public Object[] getParameterValues()
	{
		return (Object[]) this.args.clone();
	}

	public void setParameterValues(Object[] objs)
	{
		this.args = ((Object[]) objs.clone());
	}

	public Class<?>[] getParameterTypes()
	{
		return this.method.getParameterTypes();
	}

	public Type[] getGenericParameterTypes()
	{
		return this.method.getGenericParameterTypes();
	}

	public void setResultCode(String resultCode)
	{
		this.resultCode = resultCode;
	}

	public String getResultCode()
	{
		return this.resultCode;
	}

	public Object getResult()
	{
		return this.result;
	}

	public void setResult(Object result)
	{
		this.result = result;
	}

	public String getServiceName()
	{
		return this.serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public byte[] getResponseContent()
	{
		return this.responseContent;
	}

	public void setResponseContent(byte[] responseContent)
	{
		this.responseContent = responseContent;
	}

	public byte[] getBinput()
	{
		return this.binput;
	}

	public void setBinput(byte[] binput)
	{
		this.binput = binput;
	}
}