package com.dragon.kernel.endpoint.http.transform.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.endpoint.http.type.TypeFactory;
import com.dragon.kernel.endpoint.http.type.WrapType;
import com.dragon.kernel.exc.XHttpException;

public final class XMLTool
{
	private static XMLChain		chain;
	private static XMLHashMap	hashmap		= new XMLHashMap();
	private static XMLArrayList	arrayList	= new XMLArrayList();
	private static XMLArray		array		= new XMLArray();
	private static XMLObject	object		= new XMLObject();
	private static XMLPrimitive	primitive	= new XMLPrimitive();

	static
	{
		object.addChain(primitive);
		array.addChain(object);
		arrayList.addChain(array);
		hashmap.addChain(arrayList);

		setXMLChain(hashmap);
	}

	private static void setXMLChain(XMLChain chains)
	{
		chain = chains;
	}

	protected static XMLChain getXMLChain()
	{
		return chain;
	}

	@SuppressWarnings("unchecked")
	public static void readXML(MessageContext context) throws XHttpException
	{
		XMLInputFactory factory = XMLInputFactory.newInstance();

		InputStream input = context.getInputStream();

		XMLStreamReader fr = null;
		try
		{
			fr = factory.createXMLStreamReader(input);

			List<Method> methods = getMethods(context, fr);

			for (Method method : methods)
			{
				context.setMethod(method);
				Class[] clz = context.getParameterTypes();
				Type[] paramTypeList = context.getGenericParameterTypes();

				int i = 0;

				Object[] args = new Object[paramTypeList.length];
				boolean isQuit = false;
				int event = 0;
				try
				{
					for (Type paramType : paramTypeList)
					{
						args[i] = getXMLChain().doRead(fr, paramType, clz[i]);

						i++;
					}

					if ((methods.size() > 1) && (fr.nextTag() == 1))
					{
						throw new NoSuchMethodException();
					}

					context.setParameterValues(args);
				}
				catch (Exception e)
				{
					fr = factory.createXMLStreamReader(context.getInputStream());

					isQuit = false;

					event = fr.getEventType();
				}
				do
				{
					if (event == 1)
					{
						event = fr.next();

						isQuit = true;
					}

					if (isQuit)
					{
						break;
					}

					event = fr.next();
				} while (fr.hasNext());
			}

		}
		catch (XMLStreamException localXMLStreamException)
		{
			if (fr != null)
			{
				try
				{
					fr.close();
				}
				catch (XMLStreamException localXMLStreamException1)
				{
				}

			}

			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException localIOException)
				{
				}
			}
		}
		finally
		{
			if (fr != null)
			{
				try
				{
					fr.close();
				}
				catch (XMLStreamException localXMLStreamException2)
				{
				}

			}

			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException localIOException1)
				{
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static List<Method> getMethods(MessageContext context, XMLStreamReader fr) throws XMLStreamException
	{
		List methods = null;

		boolean isQuit = false;

		int event = fr.getEventType();

		while (fr.hasNext())
		{
			if (event == 1)
			{
				Service service = context.getService();
				methods = service.getMethod(fr.getLocalName());

				fr.next();

				isQuit = true;
			}

			if (isQuit)
			{
				break;
			}

			event = fr.next();
		}

		return methods != null ? methods : new ArrayList();
	}

	@SuppressWarnings("unchecked")
	public static void writeXML(MessageContext context) throws XHttpException
	{
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLStreamWriter writer = null;
		PrintWriter pw = null;
		try
		{
			writer = outputFactory.createXMLStreamWriter(baos);

			writer.writeStartDocument("UTF-8", "1.0");
			writer.writeStartElement("result");
			writer.writeAttribute("resultCode", context.getResultCode());

			if ("0".equals(context.getResultCode()))
			{
				Method method = context.getMethod();

				Type returnType = method.getGenericReturnType();
				Class returnClass = method.getReturnType();

				Object dto = context.getResult();
				Object value = null;

				if ((returnClass.isPrimitive()) && (!"void".equals(returnClass.getSimpleName())) && (dto != null))
				{
					WrapType type = TypeFactory.getInstance().getType(returnClass.getName());

					value = type.readObject(dto.toString());
				}
				else
				{
					value = dto;
				}

				if ((dto != null) && (value != null))
				{
					getXMLChain().doWrite(writer, null, value, returnType, returnClass);
				}
			}
			writer.writeEndElement();

			writer.flush();

			context.setResponseContent(baos.toByteArray());
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLTool.writeXML()", e);
		}
		finally
		{
			if (pw != null)
			{
				pw.close();
			}

			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (XMLStreamException localXMLStreamException1)
				{
				}

			}

			if (baos != null)
			{
				try
				{
					baos.close();
				}
				catch (IOException localIOException)
				{
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void generateRMDL(OutputStream output, Service service, String methodName) throws XHttpException
	{
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

		XMLStreamWriter writer = null;
		try
		{
			int j = 1;
			List<Method> methods = service.getMethod(methodName);

			for (Method method : methods)
			{
				writer = outputFactory.createXMLStreamWriter(output);

				if (j <= methods.size())
				{
					output.write(("重载方法" + j + "请求消息体规范:\n").getBytes());
					j++;
				}

				int i = 0;

				Type[] paramTypeList = method.getGenericParameterTypes();
				Class[] clz = method.getParameterTypes();

				writer.writeStartElement(method.getName());

				for (Type paramType : paramTypeList)
				{
					getXMLChain().doWrite(writer, null, "0", paramType, clz[i]);
					i++;
				}

				writer.writeEndElement();
				writer.flush();
				output.write("\n".getBytes());
				output.write("\n".getBytes());
			}
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLTool.generateRMDL()", e);
		}
		catch (IOException e)
		{
			throw new XHttpException("XMLTool.generateRMDL()", e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (XMLStreamException localXMLStreamException1)
				{
				}

			}

			if (output != null)
			{
				try
				{
					output.close();
				}
				catch (IOException localIOException1)
				{
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void generateRVDL(OutputStream output, Service service, String methodName) throws XHttpException
	{
		XMLStreamWriter writer = null;
		try
		{
			int i = 1;
			List<Method> methods = service.getMethod(methodName);

			for (Method method : methods)
			{
				writer = XMLOutputFactory.newInstance().createXMLStreamWriter(output);

				Type returnType = method.getGenericReturnType();
				Class returnClass = method.getReturnType();

				if (i <= methods.size())
				{
					output.write(("重载方法" + i + "返回值规范:\n").getBytes());
					i++;
				}

				writer.writeStartElement("result");
				writer.writeAttribute("resultCode", "0");

				if (!"void".equals(returnClass.getSimpleName()))
				{
					getXMLChain().doWrite(writer, null, "0", returnType, returnClass);
				}

				writer.writeEndElement();
				writer.flush();
				output.write("\n".getBytes());
				output.write("\n".getBytes());
			}
		}
		catch (XMLStreamException e)
		{
			throw new XHttpException("XMLTool.generateRVDL()", e);
		}
		catch (IOException e)
		{
			throw new XHttpException("XMLTool.generateRVDL()", e);
		}
		finally
		{
			if (writer != null)
			{
				try
				{
					writer.close();
				}
				catch (XMLStreamException localXMLStreamException1)
				{
				}

			}

			if (output != null)
			{
				try
				{
					output.close();
				}
				catch (IOException localIOException1)
				{
				}
			}
		}
	}
}