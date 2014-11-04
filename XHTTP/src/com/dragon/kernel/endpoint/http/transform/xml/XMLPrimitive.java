package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.endpoint.http.type.TypeFactory;
import com.dragon.kernel.endpoint.http.type.WrapType;
import com.dragon.kernel.exc.XHttpException;

public class XMLPrimitive extends XMLChain
{
	public void addChain(XMLChain nextchain)
	{
	}

	public Object doRead(XMLStreamReader fr, Type paramType, Class<?> clz) throws XHttpException
	{
		Object value = null;

		String localName = null;

		WrapType type = TypeFactory.getInstance().getType(clz.getName());

		int event = fr.getEventType();
		try
		{
			while (true)
			{
				if (event == 1)
				{
					localName = fr.getLocalName();
				}

				if ((event == 4) && (!fr.isWhiteSpace()))
				{
					value = type.readObject(fr.getText().trim());
				}

				if (((event == 2) && (localName != null) && (localName.equals(fr.getLocalName()))) || (!fr.hasNext()))
				{
					break;
				}

				event = fr.next();
			}
		}
		catch (NumberFormatException e)
		{
			throw new XHttpException("XMLPrimitive.doRead()", e);
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLPrimitive.doRead()", e);
		}

		return value != null ? value : type.readObject(null);
	}

	public void doWrite(XMLStreamWriter writer, String tagname, Object value, Type paramType, Class<?> clz) throws XHttpException
	{
		try
		{
			writer.writeStartElement(tagname == null ? clz.getSimpleName() : tagname);
			writer.writeCharacters((value == null) || ("".equals(value)) ? "0" : value.toString());
			writer.writeEndElement();
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLPrimitive.doWrite()", e);
		}
	}

	public XMLChain getChain()
	{
		return null;
	}
}