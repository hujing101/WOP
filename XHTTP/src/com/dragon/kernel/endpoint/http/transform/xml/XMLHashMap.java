package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.exc.XHttpException;

public class XMLHashMap extends XMLChain
{
	private XMLChain	nextChain;

	public void addChain(XMLChain chain)
	{
		this.nextChain = chain;
	}

	@SuppressWarnings("unchecked")
	public Object doRead(XMLStreamReader fr, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isListOrMap(clz, "java.util.Map", "java.util.HashMap"))
		{
			Type[] types = new Type[2];

			if ((paramType instanceof ParameterizedType))
			{
				types = ((ParameterizedType) paramType).getActualTypeArguments();
			}

			Object[] keyValue = new Object[2];

			Map map = null;

			String localName = null;

			boolean isCompleted = true;

			int size = 0;

			int event = fr.getEventType();
			try
			{
				while (true)
				{
					if (event == 1)
					{
						if (map == null)
						{
							localName = fr.getLocalName();

							map = new HashMap();

							size = Integer.parseInt(fr.getAttributeValue(null, "size"));

							isCompleted = false;

							event = fr.next();
						}
					}

					if (!isCompleted)
					{
						for (int i = 0; i < size; i++)
						{
							for (int j = 0; j < keyValue.length; j++)
							{
								if (types[j] != null)
								{
									if ((types[j] instanceof ParameterizedType))
									{
										keyValue[j] = XMLTool.getXMLChain().doRead(fr, types[j], Class.forName(types[j].toString().split("<")[0]));
									}
									else
									{
										keyValue[j] = XMLTool.getXMLChain().doRead(fr, types[j], (Class) types[j]);
									}
								}
								else
								{
									keyValue[j] = XMLTool.getXMLChain().doRead(fr, this.defaultClass, this.defaultClass);
								}

							}

							fr.next();

							fr.next();

							map.put(keyValue[0], keyValue[1]);
						}

						isCompleted = true;
					}

					if (((event == 2) && (localName != null) && (localName.equals(fr.getLocalName()))) || (!fr.hasNext()))
					{
						break;
					}

					event = fr.next();
				}
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLHashMap.doRead()", e);
			}
			catch (ClassNotFoundException e)
			{
				throw new XHttpException("XMLHashMap.doRead()", e);
			}

			return map;
		}

		if (this.nextChain != null)
		{
			return this.nextChain.doRead(fr, paramType, clz);
		}

		throw new XHttpException("Null nextChain Exception");
	}

	@SuppressWarnings("unchecked")
	public void doWrite(XMLStreamWriter writer, String tagname, Object value, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isListOrMap(clz, "java.util.Map", "java.util.HashMap"))
		{
			Type[] types = new Type[2];

			if ((paramType instanceof ParameterizedType))
			{
				types = ((ParameterizedType) paramType).getActualTypeArguments();
			}

			try
			{
				writer.writeStartElement(tagname == null ? "map" : tagname);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLHashMap.doWrite()", e);
			}

			if (value == null)
			{
				value = DEFAULTVALUE;
			}

			Iterator[] values = (Iterator[]) null;

			if (isListOrMap(value.getClass(), "java.util.Map", "java.util.HashMap"))
			{
				Map map = (HashMap) value;

				values = new Iterator[]{ map.keySet().iterator(), map.values().iterator() };

				this.length = (map.isEmpty() ? this.length : map.size());
			}

			try
			{
				writer.writeAttribute("size", String.valueOf(this.length));

				for (int i = 0; i < this.length; i++)
				{
					writer.writeStartElement("item");

					for (int j = 0; j < types.length; j++)
					{
						Object tempValue = values == null ? value : values[j].next();

						if (types[j] != null)
						{
							if ((types[j] instanceof ParameterizedType))
							{
								XMLTool.getXMLChain().doWrite(writer, null, tempValue, types[j], Class.forName(types[j].toString().split("<")[0]));
							}
							else
							{
								XMLTool.getXMLChain().doWrite(writer, j == 0 ? "key" : "value", tempValue, types[j], (Class) types[j]);
							}
						}
						else
						{
							XMLTool.getXMLChain().doWrite(writer, j == 0 ? "key" : "value", tempValue, this.defaultClass, this.defaultClass);
						}
					}
					writer.writeEndElement();
				}

				writer.writeEndElement();
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLHashMap.doWrite()", e);
			}
			catch (ClassNotFoundException e)
			{
				throw new XHttpException("XMLHashMap.doWrite()", e);
			}

		}
		else if (this.nextChain != null)
		{
			this.nextChain.doWrite(writer, tagname, value, paramType, clz);
		}
		else
		{
			throw new XHttpException("Null nextChain Exception");
		}
	}

	public XMLChain getChain()
	{
		return this.nextChain;
	}
}