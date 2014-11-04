package com.dragon.kernel.endpoint.http.transform;

import java.io.OutputStream;

import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.exc.XHttpException;

public abstract interface Transform
{
	public abstract void consume(MessageContext paramMessageContext) throws XHttpException;

	public abstract void produce(MessageContext paramMessageContext) throws XHttpException;

	public abstract void generateXJDL(OutputStream paramOutputStream, String paramString1, Service paramService, String paramString2)
			throws XHttpException;
}