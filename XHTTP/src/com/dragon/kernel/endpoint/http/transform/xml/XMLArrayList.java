package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.exc.XHttpException;

public class XMLArrayList extends XMLChain
{
	private XMLChain	nextChain;

	public void addChain(XMLChain chain)
	{
		this.nextChain = chain;
	}

	public Object doRead(XMLStreamReader fr, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isListOrMap(clz, "java.util.List", "java.util.ArrayList"))
		{
			Type[] types = new Type[1];

			if ((paramType instanceof ParameterizedType))
			{
				types = ((ParameterizedType) paramType).getActualTypeArguments();
			}

			List<Object> list = null;

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
						if (list == null)
						{
							localName = fr.getLocalName();

							list = new ArrayList<Object>();

							size = Integer.parseInt(fr.getAttributeValue(null, "size"));

							isCompleted = false;

							event = fr.next();
						}
					}

					if (!isCompleted)
					{
						for (int i = 0; i < size; i++)
						{
							if (types[0] != null)
							{
								if ((types[0] instanceof ParameterizedType))
								{
									list.add(XMLTool.getXMLChain().doRead(fr, types[0], Class.forName(types[0].toString().split("<")[0])));
								}
								else
								{
									list.add(XMLTool.getXMLChain().doRead(fr, types[0], (Class<?>) types[0]));
								}
							}
							else
							{
								list.add(XMLTool.getXMLChain().doRead(fr, this.defaultClass, this.defaultClass));
							}
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
			catch (ClassNotFoundException e)
			{
				throw new XHttpException("XMLArrayList.doRead()", e);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArrayList.doRead()", e);
			}

			return list;
		}

		if (this.nextChain != null)
		{
			return this.nextChain.doRead(fr, paramType, clz);
		}

		throw new XHttpException("Null nextChain Exeption");
	}

	@SuppressWarnings("unchecked")
	public void doWrite(XMLStreamWriter writer, String tagname, Object value, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isListOrMap(clz, "java.util.List", "java.util.ArrayList"))
		{
			Type[] types = new Type[1];

			if ((paramType instanceof ParameterizedType))
			{
				types = ((ParameterizedType) paramType).getActualTypeArguments();
			}

			try
			{
				writer.writeStartElement(tagname == null ? "list" : tagname);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArrayList.doWrite()", e);
			}

			if (value == null)
			{
				value = DEFAULTVALUE;
			}

			List list = null;

			if ((value != null) && (isListOrMap(value.getClass(), "java.util.List", "java.util.ArrayList")))
			{
				list = (ArrayList) value;

				this.length = list.size();
			}

			try
			{
				writer.writeAttribute("size", String.valueOf(this.length));

				for (int i = 0; i < this.length; i++)
				{
					Object tempValue = list == null ? value : list.get(i);

					if (types[0] != null)
					{
						if ((types[0] instanceof ParameterizedType))
						{
							XMLTool.getXMLChain().doWrite(writer, null, tempValue, types[0], Class.forName(types[0].toString().split("<")[0]));
						}
						else
						{
							XMLTool.getXMLChain().doWrite(writer, "item", tempValue, types[0], (Class<?>) types[0]);
						}
					}
					else
					{
						XMLTool.getXMLChain().doWrite(writer, "item", tempValue, this.defaultClass, this.defaultClass);
					}
				}

				writer.writeEndElement();
			}
			catch (ClassNotFoundException e)
			{
				throw new XHttpException("XMLArrayList.doWrite()", e);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLArrayList.doWrite()", e);
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