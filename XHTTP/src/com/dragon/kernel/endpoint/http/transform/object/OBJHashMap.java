package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.constants.XHttpConstants;
import com.dragon.kernel.exc.XHttpException;

public class OBJHashMap extends OBJChain
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
        if (isListOrMap(clz, "java.util.Map", "java.util.HashMap"))
        {
            Map map = new HashMap();
            try
            {
                Type[] types = new Type[2];
                
                if ((paramType instanceof ParameterizedType))
                {
                    types = ((ParameterizedType) paramType).getActualTypeArguments();
                }
                Object[] keyValue = new Object[2];
                int size = in.readInt();
                for (int i = 0; i < size; i++)
                {
                    for (int j = 0; j < keyValue.length; j++)
                    {
                        if (XHttpConstants.NULL_PARAM == in.readByte())
                        {
                            keyValue[j] = null;
                        }
                        else if ((types[j] instanceof ParameterizedType))
                        {
                            keyValue[j] = ObjectTool.getOBJChain().doRead(in,
                                    types[j],
                                    Class.forName(types[j].toString()
                                            .split("<")[0]));
                        }
                        else
                        {
                            keyValue[j] = ObjectTool.getOBJChain().doRead(in,
                                    types[j],
                                    (Class) types[j]);
                        }
                    }
                    map.put(keyValue[0], keyValue[1]);
                }
                
            }
            catch (Exception e)
            {
                throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
            }
            return map;
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
        if (value.getClass().getName().equals("java.util.Map")
                || (value.getClass().getName().equals("java.util.HashMap")))
        {
            Map map = null;
            try
            {
                Iterator keys = null;
                map = (HashMap) value;
                keys = map.keySet().iterator();
                int len = map.size();
                out.writeInt(len);
                Object key = null;
                Object val = null;
                for (int i = 0; i < len; i++)
                {
                    key = keys.next();
                    if (null == key)
                    {
                        out.writeByte(XHttpConstants.NULL_PARAM);
                    }
                    else
                    {
                        out.writeByte(XHttpConstants.NOT_NULL_PARAM);
                        ObjectTool.getOBJChain().doWrite(out, key);
                    }
                    val = map.get(key);
                    if (null == val)
                    {
                        out.writeByte(XHttpConstants.NULL_PARAM);
                    }
                    else
                    {
                        out.writeByte(XHttpConstants.NOT_NULL_PARAM);
                        ObjectTool.getOBJChain().doWrite(out, val);
                    }
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
