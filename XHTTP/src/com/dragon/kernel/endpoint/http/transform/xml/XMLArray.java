package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.exc.XHttpException;

public class XMLArray extends XMLChain
{
	private XMLChain	nextChain;

	public void addChain(XMLChain chain)
	{
		this.nextChain = chain;
	}

	public Object doRead(XMLStreamReader fr, Type paramType, Class<?> clz) throws XHttpException
	{
		if (clz.isArray())
		{
			Object array = null;

			boolean isCompleted = true;

			String localName = null;

			int length = 0;

			int event = fr.getEventType();
			try
			{
				while (true)
				{
					if (event == 1)
					{
						if (array == null)
						{
							localName = fr.getLocalName();

							length = Integer.parseInt(fr.getAttributeValue(0));

							array = Array.newInstance(clz.getComponentType(), length);

							isCompleted = false;

							event = fr.next();
						}
					}

					if (!isCompleted)
					{
						for (int i = 0; i < length; i++)
						{
							Array.set(array, i, XMLTool.getXMLChain().doRead(fr, clz.getComponentType(), clz.getComponentType()));
						}

						isCompleted = true;
					}

					if ((event == 2) && (localName != null) && (localName.equals(fr.getLocalName())))
						break;
					if (!fr.hasNext())
					{
						break;
					}

					event = fr.next();
				}
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArrayList.doRead()", e);
			}

			return array;
		}

		if (this.nextChain != null)
		{
			return this.nextChain.doRead(fr, paramType, clz);
		}

		throw new XHttpException("Null nextChain Exeption");
	}

	public void doWrite(XMLStreamWriter writer, String tagname, Object value, Type paramType, Class<?> clz) throws XHttpException
	{
		if (clz.isArray())
		{
			try
			{
				writer.writeStartElement(tagname == null ? "array" : tagname);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArray.doWrite()", e);
			}

			if (value == null)
			{
				value = DEFAULTVALUE;
			}

			boolean isArray = false;

			int len = 0;

			if (value.getClass().isArray())
			{
				isArray = true;

				len = Array.getLength(value);
			}
			else
			{
				len = 2;
			}

			try
			{
				writer.writeAttribute("length", String.valueOf(len));

				for (int i = 0; i < len; i++)
				{
					Object tempValue = !isArray ? value : Array.get(value, i);

					XMLTool.getXMLChain().doWrite(
							writer,
							isObject(clz.getComponentType(), "java.lang") ? null : "item",
							tempValue,
							clz.getComponentType(),
							clz.getComponentType());
				}

				writer.writeEndElement();
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArray.doWrite()", e);
			}

		}
		else if (this.nextChain != null)
		{
			this.nextChain.doWrite(writer, tagname, value, paramType, clz);
		}
		else
		{
			throw new XHttpException("Null nextChain Exeption");
		}
	}

	public XMLChain getChain()
	{
		return this.nextChain;
	}
}