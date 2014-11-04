package com.dragon.kernel.endpoint.http.transform.object;

import java.io.OutputStream;

import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.endpoint.http.transform.Transform;
import com.dragon.kernel.exc.XHttpException;

public class ObjectTransform implements Transform
{

	@Override
	public void consume(MessageContext paramMessageContext) throws XHttpException
	{
		ObjectTool.read(paramMessageContext);
	}

	@Override
	public void generateXJDL(OutputStream paramOutputStream, String paramString1, Service paramService, String paramString2) throws XHttpException
	{

	}

	@Override
	public void produce(MessageContext paramMessageContext) throws XHttpException
	{
		ObjectTool.write(paramMessageContext);
	}

}
