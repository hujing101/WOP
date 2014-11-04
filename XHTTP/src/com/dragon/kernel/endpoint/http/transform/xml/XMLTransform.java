package com.dragon.kernel.endpoint.http.transform.xml;

import java.io.OutputStream;

import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.endpoint.http.transform.Transform;
import com.dragon.kernel.exc.XHttpException;

public class XMLTransform implements Transform
{
	@SuppressWarnings("unused")
	private static final String	REQ_XML	= "REQ_XML";
	@SuppressWarnings("unused")
	private static final String	RES_XML	= "RES_XML";

	public void consume(MessageContext context) throws XHttpException
	{
		XMLTool.readXML(context);
	}

	public void produce(MessageContext context) throws XHttpException
	{
		XMLTool.writeXML(context);
	}

	public void generateXJDL(OutputStream output, String queryString, Service service, String methodName) throws XHttpException
	{
		if (queryString.equalsIgnoreCase("REQ_XML"))
		{
			XMLTool.generateRMDL(output, service, methodName);
		}
		else if (queryString.equalsIgnoreCase("RES_XML"))
		{
			XMLTool.generateRVDL(output, service, methodName);
		}
	}
}