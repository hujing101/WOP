package com.dragon.kernel.endpoint.http.transform.object.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.exc.XHttpException;

public class CommonObjectTool
{
    
    public static void read(MessageContext context) throws XHttpException
    {
        DataInputStream input = new DataInputStream(context.getInputStream());
        ObjectInputStream ois = null;
        Object[] args = null;
        try
        {
            String methodName = input.readUTF();
            int objNum = input.readInt();
            int length = 0;
            byte[] b = null;
            args = new Object[objNum];
            for (int i = 0; i < objNum; i++)
            {
                length = input.readInt();
                b = new byte[length];
                input.read(b);
                ois = new ObjectInputStream(new ByteArrayInputStream(b));
                args[i] = ois.readObject();
                ois.close();
            }
            context.setParameterValues(args);
            List<Method> methods = context.getService().getMethod(methodName);
            for (Method method : methods)
            {
                Class<?>[] classes = method.getParameterTypes();
                if (classes.length != args.length)
                {
                    continue;
                }
                for (int i = 0; i < classes.length; i++)
                {
                    if (!args[i].getClass()
                            .getName()
                            .equals(classes[i].getName()))
                    {
                        break;
                    }
                }
                context.setMethod(method);
            }
            if (null == context.getMethod())
            {
                throw new NoSuchMethodException();
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new XHttpException(ErrorCode.ERROR_CLASS_NOT_FOUND, e);
        }
        catch (NoSuchMethodException e)
        {
            throw new XHttpException(ErrorCode.ERROR_NO_SUCH_METHOD, e);
        }
        catch (Exception e)
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
        }
        finally
        {
            if (null != input)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (null != ois)
            {
                try
                {
                    ois.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void write(MessageContext context) throws XHttpException
    {
        Object result = context.getResult();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream(baos);
            oos.writeUTF(context.getResultCode());
            oos.writeObject(result);
            context.setResponseContent(baos.toByteArray());
        }
        catch (Exception e)
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
        }
        finally
        {
            if (null != oos)
            {
                try
                {
                    oos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (null != baos)
            {
                try
                {
                    baos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
