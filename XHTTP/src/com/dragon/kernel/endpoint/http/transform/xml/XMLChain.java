package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.endpoint.http.transform.AbstractChain;
import com.dragon.kernel.exc.XHttpException;

public abstract class XMLChain extends AbstractChain
{
	public abstract void addChain(XMLChain paramXMLChain);

	public abstract Object doRead(XMLStreamReader paramXMLStreamReader, Type paramType, Class<?> paramClass) throws XHttpException;

	public abstract void doWrite(XMLStreamWriter paramXMLStreamWriter, String paramString, Object paramObject, Type paramType, Class<?> paramClass)
			throws XHttpException;

	public abstract XMLChain getChain();
}