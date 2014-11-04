package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.constants.XHttpConstants;
import com.dragon.kernel.exc.XHttpException;

public class OBJArrayList extends OBJChain
{
    private OBJChain nextChain;
    
    public void addChain(OBJChain chain)
    {
        this.nextChain = chain;
    }
    
    @Override
    public Object doRead(DataInput in, Type paramType, Class<?> clz)
            throws XHttpException
    {
        
        List<Object> list = null;
        
        if (isListOrMap(clz, "java.util.List", "java.util.ArrayList"))
        {
            Type[] types = new Type[1];
            
            if ((paramType instanceof ParameterizedType))
            {
                types = ((ParameterizedType) paramType).getActualTypeArguments();
            }
            else if ((paramType instanceof WildcardType))
            {
                System.err.println("aaa");
            }
            try
            {
                int len = in.readInt();
                list = new ArrayList<Object>();
                for (int i = 0; i < len; i++)
                {
                    if (XHttpConstants.NULL_PARAM == in.readByte())
                    {
                        list.add(null);
                    }
                    else if ((types[0] instanceof ParameterizedType))
                    {
                        list.add(ObjectTool.getOBJChain()
                                .doRead(in,
                                        types[0],
                                        Class.forName(types[0].toString()
                                                .split("<")[0])));
                    }
                    else
                    {
                        list.add(ObjectTool.getOBJChain().doRead(in,
                                types[0],
                                (Class<?>) types[0]));
                    }
                }
                return list;
            }
            catch (ClassNotFoundException e)
            {
                throw new XHttpException(ErrorCode.ERROR_CLASS_NOT_FOUND, e);
            }
            catch (Exception e)
            {
                throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
            }
        }
        if (this.nextChain != null)
        {
            return this.nextChain.doRead(in, paramType, clz);
        }
        throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM);
        
    }
    
    @Override
    public void doWrite(DataOutput out, Object value) throws XHttpException
    {
        if (isListOrMap(value.getClass(),
                "java.util.List",
                "java.util.ArrayList"))
        {
            List list = null;
            try
            {
                list = (List) value;
                int len = list.size();
                out.writeInt(len);
                for (int i = 0; i < len; i++)
                {
                    if (null == list.get(i))
                    {
                        out.writeByte(XHttpConstants.NULL_PARAM);
                        continue;
                    }
                    out.writeByte(XHttpConstants.NOT_NULL_PARAM);
                    ObjectTool.getOBJChain().doWrite(out, list.get(i));
                }
                
            }
            catch (Exception e)
            {
                throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
            }
        }
        else if (this.nextChain != null)
        {
            this.nextChain.doWrite(out, value);
        }
        else
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM);
        }
    }
    
    @Override
    public OBJChain getChain()
    {
        return this.nextChain;
    }
    
}
