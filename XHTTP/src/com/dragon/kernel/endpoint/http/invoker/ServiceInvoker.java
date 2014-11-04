package com.dragon.kernel.endpoint.http.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.dragon.kernel.endpoint.http.transform.MessageContext;

public final class ServiceInvoker implements Invoker
{
	public static final String			HTTP_SERVLET_REQUEST	= "XFireServletController.httpServletRequest";
	public static final String			HTTP_SERVLET_RESPONSE	= "XFireServletController.httpServletResponse";
	private static final ServiceInvoker	INSTANCE				= new ServiceInvoker();

	public static ServiceInvoker getInstance()
	{
		return INSTANCE;
	}

	public Object invoke(MessageContext context) throws IllegalAccessException, InvocationTargetException
	{
		Method method = context.getMethod();

		Object[] args = context.getParameterValues();

		Object proxy = context.getService().getServiceObject();

		Object result = method.invoke(proxy, args);

		context.setResult(result);

		return result;
	}

}