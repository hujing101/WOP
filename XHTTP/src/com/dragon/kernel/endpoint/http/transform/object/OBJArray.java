package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.constants.XHttpConstants;
import com.dragon.kernel.exc.XHttpException;

public class OBJArray extends OBJChain
{
    private OBJChain nextChain;
    
    public void addChain(OBJChain chain)
    {
        this.nextChain = chain;
    }
    
    @Override
    public OBJChain getChain()
    {
        return this.nextChain;
    }
    
    @Override
    public Object doRead(DataInput in, Type paramType, Class<?> clz)
            throws XHttpException
    {
        if (clz.isArray())
        {
            try
            {
                Object array = null;
                int len = in.readInt();
                array = Array.newInstance(clz.getComponentType(), len);
                Object value;
                for (int i = 0; i < len; i++)
                {
                    value = null;
                    if (XHttpConstants.NOT_NULL_PARAM == in.readByte())
                    {
                        value = ObjectTool.getOBJChain().doRead(in,
                                clz.getComponentType(),
                                clz.getComponentType());
                    }
                    Array.set(array, i, value);
                }
                return array;
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
        if (value.getClass().isArray())
        {
            try
            {
                int len = Array.getLength(value);
                out.writeInt(len);
                for (int i = 0; i < len; i++)
                {
                    Object tempValue = Array.get(value, i);
                    if (null == tempValue)
                    {
                        out.writeByte(XHttpConstants.NULL_PARAM);
                        continue;
                    }
                    out.writeByte(XHttpConstants.NOT_NULL_PARAM);
                    ObjectTool.getOBJChain().doWrite(out, tempValue);
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
}
