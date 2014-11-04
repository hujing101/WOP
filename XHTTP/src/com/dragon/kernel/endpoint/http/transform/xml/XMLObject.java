package com.dragon.kernel.endpoint.http.transform.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.endpoint.http.type.TypeFactory;
import com.dragon.kernel.exc.XHttpException;

public class XMLObject extends XMLChain
{
	private XMLChain	nextChain;

	public void addChain(XMLChain chain)
	{
		this.nextChain = chain;
	}

	public Object doRead(XMLStreamReader fr, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isObject(clz, "java.lang"))
		{
			Object obj = null;

			String objname = null;

			int event = fr.getEventType();
			while (true)
			{
				if (event == 1)
				{
					if ((objname == null) && (clz.getSimpleName().equalsIgnoreCase(fr.getLocalName())))
					{
						objname = fr.getLocalName();
						try
						{
							obj = Class.forName(clz.getName()).newInstance();

							event = fr.next();
						}
						catch (InstantiationException e)
						{
							throw new XHttpException("XMLObject.doRead()", e);
						}
						catch (IllegalAccessException e)
						{
							throw new XHttpException("XMLObject.doRead()", e);
						}
						catch (ClassNotFoundException e)
						{
							throw new XHttpException("XMLObject.doRead()", e);
						}
						catch (XMLStreamException e)
						{
							throw new XHttpException("XMLObject.doRead()", e);
						}

					}

					processObjFields(fr, obj);
				}

				try
				{
					if (((event == 2) && (objname != null) && (objname.equals(fr.getLocalName()))) || (!fr.hasNext()))
					{
						break;
					}

					event = fr.next();
				}
				catch (XMLStreamException e)
				{
					throw new XHttpException("XMLObject.doRead()", e);
				}
			}

			return obj;
		}

		if (this.nextChain != null)
		{
			return this.nextChain.doRead(fr, paramType, clz);
		}

		throw new XHttpException("Null nextChain Exeption");
	}

	private void processObjFields(XMLStreamReader fr, Object obj) throws XHttpException
	{
		try
		{
			Field f = obj.getClass().getDeclaredField(fr.getLocalName());

			String setName = getSetMethodName(fr, f);

			Method setMethod = obj.getClass().getMethod(setName, new Class[]{ f.getType() });

			if (isBasicType(f.getType(), "java.lang"))
			{
				setMethod.invoke(obj, new Object[]{ TypeFactory.getInstance().getType(f.getType().getName()).readObject(fr.getElementText()) });
			}
			else if ((f.getGenericType() instanceof ParameterizedType))
			{
				setMethod.invoke(obj, new Object[]{ XMLTool.getXMLChain().doRead(fr, f.getGenericType(), f.getType()) });
			}
			else
			{
				setMethod.invoke(obj, new Object[]{ XMLTool.getXMLChain().doRead(fr, f.getGenericType(), (Class<?>) f.getGenericType()) });
			}

		}
		catch (NoSuchFieldError e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (SecurityException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (NoSuchFieldException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (NoSuchMethodException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (IllegalArgumentException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (IllegalAccessException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (InvocationTargetException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLObject.doRead()", e);
		}
	}

	private String getSetMethodName(XMLStreamReader fr, Field f)
	{
		String setName = null;

		if (("boolean".equalsIgnoreCase(f.getType().getName())) && (fr.getLocalName().startsWith("is")))
		{
			setName = fr.getLocalName().replace("is", "set");
		}
		else
		{
			setName = "set" + fr.getLocalName().substring(0, 1).toUpperCase(Locale.getDefault()) + fr.getLocalName().substring(1);
		}
		return setName;
	}

	public void doWrite(XMLStreamWriter writer, String tagname, Object value, Type paramType, Class<?> clz) throws XHttpException
	{
		if (isObject(clz, "java.lang"))
		{
			try
			{
				writer.writeStartElement(tagname == null ? getName(clz.getSimpleName()) : tagname);
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLObject.doWrite()", e);
			}

			if (value == null)
			{
				value = DEFAULTVALUE;
			}

			Field[] fields = clz.getDeclaredFields();

			for (int i = 0; i < fields.length; i++)
			{
				Field f = fields[i];

				if ((Modifier.isFinal(f.getModifiers())) || (Modifier.isStatic(f.getModifiers())))
				{
					continue;
				}

				String getName = null;

				if ("boolean".equalsIgnoreCase(f.getType().getName()))
				{
					getName = f.getName();
					if (!getName.startsWith("is"))
					{
						getName = "is" + f.getName().substring(0, 1).toUpperCase(Locale.getDefault()) + f.getName().substring(1);
					}
				}
				else
				{
					getName = "get" + f.getName().substring(0, 1).toUpperCase(Locale.getDefault()) + f.getName().substring(1);
				}

				Method getMethod = null;
				try
				{
					getMethod = clz.getDeclaredMethod(getName, new Class[0]);
				}
				catch (Exception e)
				{
					continue;
				}

				Object temp = null;

				if (isObject(value.getClass(), "java.lang"))
				{
					try
					{
						temp = getMethod.invoke(value, new Object[0]);
					}
					catch (IllegalArgumentException e)
					{
						throw new XHttpException("XMLObject.doWrite()", e);
					}
					catch (IllegalAccessException e)
					{
						throw new XHttpException("XMLObject.doWrite()", e);
					}
					catch (InvocationTargetException e)
					{
						throw new XHttpException("XMLObject.doWrite()", e);
					}
				}
				else if ("boolean".equalsIgnoreCase(f.getType().getName()))
				{
					temp = Boolean.valueOf(true);
				}
				else
				{
					temp = value;
				}

				if (temp == null)
				{
					continue;
				}

				if (isBasicType(f.getType(), "java.lang"))
				{
					try
					{
						writer.writeStartElement(f.getName());
						writer.writeCharacters(temp.toString());
						writer.writeEndElement();
					}
					catch (XMLStreamException e)
					{
						throw new XHttpException("XMLObject.doWrite()", e);
					}

				}
				else if ((f.getGenericType() instanceof ParameterizedType))
				{
					XMLTool.getXMLChain().doWrite(writer, f.getName(), temp, f.getGenericType(), f.getType());
				}
				else
				{
					XMLTool.getXMLChain().doWrite(writer, f.getName(), temp, f.getGenericType(), (Class<?>) f.getGenericType());
				}

			}

			try
			{
				writer.writeEndElement();
			}
			catch (XMLStreamException e)
			{
				throw new XHttpException("XMLObject.doWrite()", e);
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