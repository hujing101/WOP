package com.dragon.kernel.endpoint.http.transform.object;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.exc.XHttpException;
import com.dragon.kernel.object.serialize.SerializeUtil;

public class ObjectTool
{
    private static OBJChain chain;
    
    private static OBJHashMap hashmap = new OBJHashMap();
    
    private static OBJArrayList arrayList = new OBJArrayList();
    
    private static OBJArray array = new OBJArray();
    
    private static OBJObject object = new OBJObject();
    
    private static OBJPrimitive primitive = new OBJPrimitive();
    static
    {
        object.addChain(primitive);
        array.addChain(object);
        arrayList.addChain(array);
        hashmap.addChain(arrayList);
        
        setOBJChain(hashmap);
    }
    
    private static void setOBJChain(OBJChain chains)
    {
        chain = chains;
    }
    
    public static OBJChain getOBJChain()
    {
        return chain;
    }
    
    public static void read(MessageContext context) throws XHttpException
    {
        DataInputStream input = new DataInputStream(context.getInputStream());
        Object[] args = null;
        try
        {
            String methodName = input.readUTF();
            int objNum = input.readInt();
            args = new Object[objNum];
            for (int i = 0; i < objNum; i++)
            {
                SerializeUtil pkg = new SerializeUtil();
                args[i] = pkg.readFields(input);
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
                    if (null != args[i]
                            && !args[i].getClass()
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
        }
    }
    
    public static void write(MessageContext context) throws XHttpException
    {
        Object result = context.getResult();
        SerializeUtil pkg = new SerializeUtil();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = null;
        try
        {
            out = new DataOutputStream(baos);
            out.writeUTF(context.getResultCode());
            pkg.write(out, result);
            context.setResponseContent(baos.toByteArray());
        }
        catch (Exception e)
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
        }
        finally
        {
            if (null != out)
            {
                try
                {
                    out.close();
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
