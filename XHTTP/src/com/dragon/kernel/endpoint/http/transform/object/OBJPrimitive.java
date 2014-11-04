package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Type;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.endpoint.http.type.TypeFactory;
import com.dragon.kernel.endpoint.http.type.WrapType;
import com.dragon.kernel.exc.XHttpException;

public class OBJPrimitive extends OBJChain
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
        Object value = null;
        WrapType type = TypeFactory.getInstance().getType(clz.getName());
        try
        {
            value = type.doRead(in);
        }
        catch (Exception e)
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
        }
        return value;
    }
    
    @Override
    public void doWrite(DataOutput out, Object value) throws XHttpException
    {
        WrapType type = TypeFactory.getInstance().getType(value.getClass()
                .getName());
        try
        {
            type.doWrite(out, value);
        }
        catch (Exception e)
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
        }
    }
    
    @Override
    public OBJChain getChain()
    {
        return this.nextChain;
    }
    
}
