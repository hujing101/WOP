package com.dragon.kernel.endpoint.http.invoker;

import java.lang.reflect.InvocationTargetException;

import com.dragon.kernel.endpoint.http.transform.MessageContext;

public abstract interface Invoker
{
	public abstract Object invoke(MessageContext paramMessageContext) throws IllegalAccessException, InvocationTargetException;
}